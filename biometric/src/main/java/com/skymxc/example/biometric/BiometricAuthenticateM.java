package com.skymxc.example.biometric;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

import com.skymxc.example.biometric.fingerprint.FingerprintAuthenticationDialogFragment;
import com.skymxc.example.biometric.fingerprint.FingerprintCallback;

import javax.crypto.Cipher;

/**
 * Create By 孟祥超
 * Date: 2019/5/30  11:19
 * Description:
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class BiometricAuthenticateM implements IBiometricAuthenticate, FingerprintCallback {

    final String TAG_DIALOG = "DIALOG";
    FingerprintManagerCompat fingerprintManagerCompat;
    FingerprintAuthenticationDialogFragment dialogFragment;
    BiometricAuthenticateCallback callback;
    FragmentActivity activity;

    public BiometricAuthenticateM(FragmentActivity context) {
        this.activity = context;
        this.fingerprintManagerCompat = FingerprintManagerCompat.from(context);
        dialogFragment = new FingerprintAuthenticationDialogFragment();
        dialogFragment.setCallback(this);
    }

    @Override
    public void authenticate(Cipher cipher, BiometricAuthenticateCallback callback) {
        this.callback = callback;
        dialogFragment.setCryptoObject(new FingerprintManagerCompat.CryptoObject(cipher));
        dialogFragment.show(activity.getSupportFragmentManager(), TAG_DIALOG);
    }


    @Override
    public boolean isSupport() {
        return isHardwareDetected()&&hasEnrolledFingerprints()&&isKeyguardSecure();
    }

    @Override
    public boolean isKeyguardSecure(){
        KeyguardManager keyguardManager = activity.getSystemService(KeyguardManager.class);
        return keyguardManager.isKeyguardSecure();
    }
    public boolean hasEnrolledFingerprints(){
        return null == fingerprintManagerCompat ? false :fingerprintManagerCompat.hasEnrolledFingerprints();
    }
    @Override
    public boolean isHardwareDetected() {
        return null == fingerprintManagerCompat ? false : fingerprintManagerCompat.isHardwareDetected();
    }

    @Override
    public void onAuthenticated(FingerprintManagerCompat.AuthenticationResult result) {
        Cipher cipher = null;
        if (null != result && result.getCryptoObject() != null) {
            cipher = result.getCryptoObject().getCipher();
        }
        if (null != callback) {
            callback.onSucc(cipher);
        }
    }

    @Override
    public void onError() {
        if (null != callback) {
            callback.onError();
        }
    }
}
