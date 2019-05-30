package com.skymxc.example.biometric;

import javax.crypto.Cipher;

/**
 * Create By 孟祥超
 * Date: 2019/5/30  11:16
 * Description:
 */
public interface BiometricAuthenticateCallback {
    void onSucc(Cipher cipher);
    void onError();
//    void onNegativeClicked();
}
