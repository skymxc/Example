package com.skymxc.example.biometric;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.CancellationSignal;
import androidx.annotation.RequiresApi;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import javax.crypto.Cipher;

/**
 * Create By 孟祥超
 * Date: 2019/5/30  11:30
 * Description:
 */
@RequiresApi(api = Build.VERSION_CODES.P)
public class BiometricAuthenticateP implements IBiometricAuthenticate {
    BiometricPrompt.Builder builder;

    Context context;
    public BiometricAuthenticateP(Context context) {
        this.context =context;
        builder= new BiometricPrompt.Builder(context);
        builder.setTitle("生物识别");
        builder.setSubtitle("生物识别子标题");
        builder.setDescription("生物识别-描述");
    }

    @Override
    public void authenticate(Cipher cipher, final BiometricAuthenticateCallback callback) {
        final CancellationSignal cancellationSignal = new CancellationSignal();

        builder.setNegativeButton("账号密码登陆", context.getMainExecutor(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancellationSignal.cancel();
//                if (null!=callback){
//                    callback.onNegativeClicked();
//                }
            }
        });
        BiometricPrompt build = builder.build();
        build.authenticate(new BiometricPrompt.CryptoObject(cipher), cancellationSignal, context.getMainExecutor(), new BiometricPrompt.AuthenticationCallback() {


            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                if (null!=callback){
                    Cipher cipher1 =null;
                    if (result!=null&&result.getCryptoObject()!=null){
                        cipher1 = result.getCryptoObject().getCipher();
                    }
                    callback.onSucc(cipher1);
                }
            }

            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (null!=callback){
                    callback.onError();
                }
            }
        });
    }

    @Override
    public boolean isSupport() {
        return isHardwareDetected()&&isHardwareDetected();
    }

    @Override
    public boolean isKeyguardSecure() {
        KeyguardManager keyguardManager = context.getSystemService(KeyguardManager.class);
        return keyguardManager.isKeyguardSecure();
    }


    @Override
    public boolean isHardwareDetected() {
        return FingerprintManagerCompat.from(context).isHardwareDetected();
    }
}
