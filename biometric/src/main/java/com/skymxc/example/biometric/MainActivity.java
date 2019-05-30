package com.skymxc.example.biometric;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.skymxc.example.biometric.fingerprint.FingerprintAuthenticationDialogFragment;
import com.skymxc.example.biometric.fingerprint.FingerprintCallback;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FingerprintCallback {

    final String TAG_FRAGMENT = "FingerprintAuthenticationDialogFragment";
    final String CONTENT = " 这里是加密信息!!!!";
    final int REQUEST_CODE_PERMISSION_USE_FINGERPRINT = 100;
    TextView mTvLog;
    FingerprintAuthenticationDialogFragment authenticationDialogFragment;

    SharedPreferences preferences;
    BiometricAuthenticateManager biometricManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        mTvLog = findViewById(R.id.tv_log);
        findViewById(R.id.btn_fingerprint_simple).setOnClickListener(this);
        findViewById(R.id.btn_fingerprint_encrypt).setOnClickListener(this);
        findViewById(R.id.btn_fingerprint_decrypt).setOnClickListener(this);
        findViewById(R.id.btn_biometric_simple).setOnClickListener(this);
        findViewById(R.id.btn_biometric_encrypt).setOnClickListener(this);
        findViewById(R.id.btn_biometric_decrypt).setOnClickListener(this);
        findViewById(R.id.btn_simple_x).setOnClickListener(this);
        findViewById(R.id.btn_encrypt_x).setOnClickListener(this);
        findViewById(R.id.btn_decrypt_x).setOnClickListener(this);
        authenticationDialogFragment = new FingerprintAuthenticationDialogFragment();
        biometricManager = new BiometricAuthenticateManager(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fingerprint_simple:
                simpleFingerprint();
                break;
            case R.id.btn_fingerprint_encrypt:
                encryptFingerprint();
                break;
            case R.id.btn_fingerprint_decrypt:
                decryptFingerprint();
                break;
            case R.id.btn_biometric_simple:
                simpleBiometric();
                break;
            case R.id.btn_biometric_encrypt:
                encryptBiometric();
                break;
            case R.id.btn_biometric_decrypt:
                decryptBiometric();
                break;
            case R.id.btn_simple_x:
                simplex();
                break;
            case R.id.btn_encrypt_x:
                encryptx();
                break;
            case R.id.btn_decrypt_x:
                decryptx();
                break;
        }
    }

    protected void decryptx() {
        //keystore
        if (null == mKeyStore) {
            try {
                mKeyStore = KeyStore.getInstance(KEYSTORE_TYPE);
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
        }
        //密钥生成器
        if (null == mKeyGenerator) {
            try {
                mKeyGenerator = KeyGenerator.getInstance(ALGORITHM, KEYSTORE_TYPE);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
        }

        //cipher
        try {
            if (null == mCipher) {
                mCipher = Cipher.getInstance(ALGORITHM + "/" + BLOCK_MODE + "/" + ENCRYPTION_PADDING);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        //生成 KEY
        createKey(KEY_NAME);
        initDecryptCipher();
        biometricManager.authenticate(mCipher, new BiometricAuthenticateCallback() {
            @Override
            public void onSucc(Cipher cipher) {
                log("兼容性解密-");
                decrypteData(cipher);
            }

            @Override
            public void onError() {
                log("兼容性解密失败");
            }
        });
    }

    protected void encryptx() {
        //keystore
        if (null == mKeyStore) {
            try {
                mKeyStore = KeyStore.getInstance(KEYSTORE_TYPE);
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
        }
        //密钥生成器
        if (null == mKeyGenerator) {
            try {
                mKeyGenerator = KeyGenerator.getInstance(ALGORITHM, KEYSTORE_TYPE);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
        }

        //cipher
        try {
            if (null == mCipher) {
                mCipher = Cipher.getInstance(ALGORITHM + "/" + BLOCK_MODE + "/" + ENCRYPTION_PADDING);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        //生成 KEY
        createKey(KEY_NAME);

        //初始化
        initEncryptCipher();
        biometricManager.authenticate(mCipher, new BiometricAuthenticateCallback() {
            @Override
            public void onSucc(Cipher cipher) {
                log("兼容性加密");
                cryptData(cipher);
            }

            @Override
            public void onError() {
                log("兼容性加密失败");
            }
        });
    }

    protected void simplex() {
        biometricManager.authenticate(null, new BiometricAuthenticateCallback() {
            @Override
            public void onSucc(Cipher cipher) {
                log("simplex-识别成功");
            }

            @Override
            public void onError() {
                log("simplex-识别失败");
            }
        });
    }

    protected void decryptBiometric() {
        final CancellationSignal cancellationSignal = new CancellationSignal();
        BiometricPrompt.Builder builder = new BiometricPrompt.Builder(this);
        builder.setTitle("标题");
        builder.setSubtitle("子标题");
        builder.setDescription("描述");
        builder.setNegativeButton("账号密码登陆", getMainExecutor(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancellationSignal.cancel();
            }
        });
        BiometricPrompt build = builder.build();
        // keystore
        if (null == mKeyStore) {
            try {
                mKeyStore = KeyStore.getInstance(KEYSTORE_TYPE);
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
        }
        //密钥生成器
        if (null == mKeyGenerator) {
            try {
                mKeyGenerator = KeyGenerator.getInstance(ALGORITHM, KEYSTORE_TYPE);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
        }
        //是否有 屏幕锁
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            KeyguardManager keyguardManager = getSystemService(KeyguardManager.class);
            if (!keyguardManager.isKeyguardSecure()) {
                log("没有屏幕锁!");
                toast("没有屏幕锁!");
                return;
            }
        }
        //cipher
        if (null == mCipher) {
            try {
                mCipher = Cipher.getInstance(ALGORITHM + "/" + BLOCK_MODE + "/" + ENCRYPTION_PADDING);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }
        }
        //创建 key
        //生成 KEY
        createKey(KEY_NAME);
        initDecryptCipher();
        build.authenticate(new BiometricPrompt.CryptoObject(mCipher), cancellationSignal, getMainExecutor(), new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                cancellationSignal.cancel();
                log("onAuthenticationError--生物识别解密-" + errString);
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
                log("onAuthenticationHelp--生物识别解密--" + helpString);
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                log("onAuthenticationSucceeded---生物识别解密");
                if (result.getCryptoObject() == null) {
                    log(" cryptObject is null ---生物识别解密");
                    return;
                }
                decrypteData(result.getCryptoObject().getCipher());
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                log("onAuthenticationFailed-生物识别解密");
            }
        });
    }

    protected void encryptBiometric() {
        final CancellationSignal cancellationSignal = new CancellationSignal();
        BiometricPrompt.Builder builder = new BiometricPrompt.Builder(this);
        builder.setTitle("标题");
        builder.setSubtitle("子标题");
        builder.setDescription("描述");
        builder.setNegativeButton("账号密码登陆", getMainExecutor(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancellationSignal.cancel();
            }
        });
        BiometricPrompt build = builder.build();
        //keystore
        if (null == mKeyStore) {
            try {
                mKeyStore = KeyStore.getInstance(KEYSTORE_TYPE);
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
        }
        //密钥生成器
        if (null == mKeyGenerator) {
            try {
                mKeyGenerator = KeyGenerator.getInstance(ALGORITHM, KEYSTORE_TYPE);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
        }
//有没有设置锁屏
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            KeyguardManager keyguardManager = getSystemService(KeyguardManager.class);
            if (!keyguardManager.isKeyguardSecure()) {
                log("没有屏幕锁!");
                toast("没有屏幕锁!");
                return;
            }
        }
        //cipher
        try {
            if (null == mCipher) {
                mCipher = Cipher.getInstance(ALGORITHM + "/" + BLOCK_MODE + "/" + ENCRYPTION_PADDING);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        //生成 KEY
        createKey(KEY_NAME);

        //初始化
        initEncryptCipher();
        build.authenticate(new BiometricPrompt.CryptoObject(mCipher), cancellationSignal, getMainExecutor(), new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                toast(errString.toString());
                log("onAuthenticationError-生物识别加密-失败-" + errString);
                cancellationSignal.cancel(); // keystore
                if (null == mKeyStore) {
                    try {
                        mKeyStore = KeyStore.getInstance(KEYSTORE_TYPE);
                    } catch (KeyStoreException e) {
                        e.printStackTrace();
                    }
                }
                //密钥生成器
                if (null == mKeyGenerator) {
                    try {
                        mKeyGenerator = KeyGenerator.getInstance(ALGORITHM, KEYSTORE_TYPE);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (NoSuchProviderException e) {
                        e.printStackTrace();
                    }
                }
                //是否有 屏幕锁
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    KeyguardManager keyguardManager = getSystemService(KeyguardManager.class);
                    if (!keyguardManager.isKeyguardSecure()) {
                        log("没有屏幕锁!");
                        toast("没有屏幕锁!");
                        return;
                    }
                }
                //cipher
                if (null == mCipher) {
                    try {
                        mCipher = Cipher.getInstance(ALGORITHM + "/" + BLOCK_MODE + "/" + ENCRYPTION_PADDING);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    }
                }
                //创建 key
                initDecryptCipher();
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
                log("onAuthenticationHelp-生物识别加密-help->" + helpString);
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                log("onAuthenticationSucceeded -生物识别-加密-");
                BiometricPrompt.CryptoObject cryptoObject = result.getCryptoObject();
                if (null == cryptoObject) {
                    log("onAuthenticationSucceeded--- cryptObject is null!");
                    return;
                }
                Cipher cipher = cryptoObject.getCipher();
                cryptData(cipher);

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                log("onAuthenticationFailed-生物识别-加密-");
            }
        });
    }

    protected void simpleBiometric() {
        final CancellationSignal cancellationSignal = new CancellationSignal();
        BiometricPrompt.Builder builder = new BiometricPrompt.Builder(this);
        builder.setTitle("标题");
        builder.setSubtitle("子标题");
        builder.setDescription("描述");
        builder.setNegativeButton("账号密码登陆", getMainExecutor(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancellationSignal.cancel();
            }
        });
        BiometricPrompt build = builder.build();
        build.authenticate(cancellationSignal, getMainExecutor(), new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                log("简单生物识别-onAuthenticationError>" + errString);
                toast(errString.toString());
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
                log("onAuthenticationHelp->" + helpString);
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                log("简单生物识别 成功");
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                log("onAuthenticationFailed--简单生物识别->");
            }
        });
    }


    KeyStore mKeyStore;
    KeyGenerator mKeyGenerator;
    Cipher mCipher;
    SecretKey mSecretKey;
    String encode;
    // 非对称加密
    final String ALGORITHM = KeyProperties.KEY_ALGORITHM_AES;
    //AndroidKeyStore 主要用于存储 密钥 key
    final String KEYSTORE_TYPE = "AndroidKeyStore";
    final String KEY_NAME = "user";
    final String BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC;
    final String ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7;


    protected void decryptFingerprint() {
        // keystore
        if (null == mKeyStore) {
            try {
                mKeyStore = KeyStore.getInstance(KEYSTORE_TYPE);
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
        }
        //密钥生成器
        if (null == mKeyGenerator) {
            try {
                mKeyGenerator = KeyGenerator.getInstance(ALGORITHM, KEYSTORE_TYPE);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
        }
        //是否有 屏幕锁
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            KeyguardManager keyguardManager = getSystemService(KeyguardManager.class);
            if (!keyguardManager.isKeyguardSecure()) {
                log("没有屏幕锁!");
                toast("没有屏幕锁!");
                return;
            }
        }
        //cipher
        if (null == mCipher) {
            try {
                mCipher = Cipher.getInstance(ALGORITHM + "/" + BLOCK_MODE + "/" + ENCRYPTION_PADDING);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }
        }
        //创建 key
        createKey(KEY_NAME);
        initDecryptCipher();
        authenticationDialogFragment.setCallback(new FingerprintCallback() {
            @Override
            public void onAuthenticated(FingerprintManagerCompat.AuthenticationResult result) {
                if (null == result.getCryptoObject()) {
                    log("解密失败: cryptoObject is null!");
                    return;
                }
                Cipher cipher = result.getCryptoObject().getCipher();
                decrypteData(cipher);
            }

            @Override
            public void onError() {
                log("验证失败->解密");
            }
        });
        authenticationDialogFragment.setCryptoObject(new FingerprintManagerCompat.CryptoObject(mCipher));
        authenticationDialogFragment.show(getSupportFragmentManager(), TAG_FRAGMENT);
    }

    private void decrypteData(Cipher cipher) {
        String encode = getResult();
        try {
//
            byte[] decrypted = cipher.doFinal(Base64.decode(encode, Base64.URL_SAFE));
            String s = new String(decrypted);
            log("解密成功-->" + s);

        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    private void encryptFingerprint() {
        //keystore
        if (null == mKeyStore) {
            try {
                mKeyStore = KeyStore.getInstance(KEYSTORE_TYPE);
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
        }
        //密钥生成器
        if (null == mKeyGenerator) {
            try {
                mKeyGenerator = KeyGenerator.getInstance(ALGORITHM, KEYSTORE_TYPE);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
        }
//有没有设置锁屏
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            KeyguardManager keyguardManager = getSystemService(KeyguardManager.class);
            if (!keyguardManager.isKeyguardSecure()) {
                log("没有屏幕锁!");
                toast("没有屏幕锁!");
                return;
            }
        }
        //cipher
        try {
            if (null == mCipher) {
                mCipher = Cipher.getInstance(ALGORITHM + "/" + BLOCK_MODE + "/" + ENCRYPTION_PADDING);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        //生成 KEY
        createKey(KEY_NAME);

        //初始化
        initEncryptCipher();
        authenticationDialogFragment.setCallback(new FingerprintCallback() {
            @Override
            public void onAuthenticated(FingerprintManagerCompat.AuthenticationResult result) {
                if (result.getCryptoObject() == null) {
                    log("验证成功!cryptoObject is null");
                    return;
                }
                Cipher cipher = result.getCryptoObject().getCipher();
                cryptData(cipher);
            }

            @Override
            public void onError() {
                log("验证错误-加密数据");
            }
        });
        authenticationDialogFragment.setCryptoObject(new FingerprintManagerCompat.CryptoObject(mCipher));
        authenticationDialogFragment.show(getSupportFragmentManager(), TAG_FRAGMENT);
    }

    private void cryptData(Cipher cipher) {
        //加密数据
        try {
            byte[] encrypted = cipher.doFinal(CONTENT.getBytes());
            byte[] iv = cipher.getIV();
            encode = Base64.encodeToString(encrypted, Base64.URL_SAFE);
            String ivs = Base64.encodeToString(iv, Base64.URL_SAFE);
            log("加密后的数据:->" + encode);
            putResult(encode);
            putIV(ivs);
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    protected void initEncryptCipher() {
        try {
            mKeyStore.load(null);
            mCipher.init(Cipher.ENCRYPT_MODE, mSecretKey);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }


    protected void initDecryptCipher() {
        try {
            mKeyStore.load(null);
            byte[] decode = Base64.decode(getIV(), Base64.URL_SAFE);
            mCipher.init(Cipher.DECRYPT_MODE, mSecretKey, new IvParameterSpec(decode));
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    protected void createKey(String keyName) {
        try {
            if (mSecretKey == null) {
                mKeyStore.load(null);
                mSecretKey = (SecretKey) mKeyStore.getKey(KEY_NAME, null);
            }
            if (mSecretKey != null) return;
            KeyGenParameterSpec.Builder builder = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                builder = new KeyGenParameterSpec.Builder(keyName,
                        KeyProperties.PURPOSE_ENCRYPT |
                                KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(BLOCK_MODE)
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(ENCRYPTION_PADDING);
                mKeyGenerator.init(builder.build());
                mKeyGenerator.generateKey();
            }
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    Toast toast;

    protected void toast(String txt) {
        if (toast == null) {
            toast = Toast.makeText(this, txt, Toast.LENGTH_SHORT);
        } else {
            toast.setText(txt);
        }
        toast.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION_USE_FINGERPRINT) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                simpleFingerprint();
                return;
            } else {
                toast("权限被拒绝");
            }
        }
    }

    /**
     * 简单指纹识别
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void simpleFingerprint() {
        //检查权限
        int checkSelfPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT);
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.USE_FINGERPRINT}, REQUEST_CODE_PERMISSION_USE_FINGERPRINT);
            return;
        }
        //是否有硬件支持
        FingerprintManagerCompat managerCompat = FingerprintManagerCompat.from(this);
        boolean hardwareDetected = managerCompat.isHardwareDetected();
        if (!hardwareDetected) {
            toast("当前设备不支持指纹识别");
            return;
        }
        //是否已经录入指纹
        boolean hasEnrolledFingerprints = managerCompat.hasEnrolledFingerprints();
        if (!hasEnrolledFingerprints) {
            toast("目前没有录入指纹");
            return;
        }
        authenticationDialogFragment.show(getSupportFragmentManager(), TAG_FRAGMENT);
    }


    StringBuffer buffer = new StringBuffer();

    protected void log(String txt) {
        buffer.append("\n");
        buffer.append(txt);
        mTvLog.setText(buffer.toString());

    }

    public void putResult(String string) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("encode", string);
        edit.commit();
    }

    public String getResult() {
        return preferences.getString("encode", "");
    }

    public void putIV(String string) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("iv", string);
        edit.commit();
    }

    public String getIV() {
        return preferences.getString("iv", "");
    }

    @Override
    public void onAuthenticated(FingerprintManagerCompat.AuthenticationResult result) {
        log("验证成功");

    }

    @Override
    public void onError() {
        log("验证失败");
    }
}
