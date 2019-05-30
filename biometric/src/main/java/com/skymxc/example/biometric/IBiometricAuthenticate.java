package com.skymxc.example.biometric;

import javax.crypto.Cipher;

/**
 * Create By 孟祥超
 * Date: 2019/5/30  11:10
 * Description: 生物识别
 */
public interface IBiometricAuthenticate {

    void authenticate(Cipher cipher, BiometricAuthenticateCallback callback);


    boolean isSupport();

    boolean isKeyguardSecure();
//
//    boolean hasEnrolledFingerprints();

    boolean isHardwareDetected();
}
