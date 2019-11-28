package com.skymxc.example.biometric.fingerprint;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

/**
 * Create By 孟祥超
 * Date: 2019/5/29  11:10
 * Description:
 */
public interface FingerprintCallback {
    void onAuthenticated(FingerprintManagerCompat.AuthenticationResult result);
    void onError();
}
