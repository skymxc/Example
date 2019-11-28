package com.skymxc.example.biometric;

import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import javax.crypto.Cipher;

/**
 * Create By 孟祥超
 * Date: 2019/5/30  11:42
 * Description:
 */
public class BiometricAuthenticateManager {
    IBiometricAuthenticate biometricAuthenticate;

    public BiometricAuthenticateManager(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.P){
            biometricAuthenticate = new BiometricAuthenticateP(activity);
        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            biometricAuthenticate = new BiometricAuthenticateM(activity);
        }else{
            //do nothing
        }
    }

    public void authenticate(Cipher cipher,BiometricAuthenticateCallback callback){
        if (null!=biometricAuthenticate){
            biometricAuthenticate.authenticate(cipher,callback);
        }
    }

}
