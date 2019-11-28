package com.skymxc.example.biometric.fingerprint;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.skymxc.example.biometric.R;

/**
 * Create By 孟祥超
 * Date: 2019/5/29  10:40
 * Description:
 */
public class FingerprintUIHelper extends FingerprintManagerCompat.AuthenticationCallback {

    static final long ERROR_TIMEOUT_MILLIS = 1600;
    static final long SUCCESS_DELAY_MILLIS = 1300;
    protected ImageView mIvIcon;
    protected TextView mTvError;
    protected CancellationSignal cancellationSignal;
    protected FingerprintManagerCompat fingerprintManagerCompat;

    protected Callback callback;
    boolean mSelfCancelled;

    public FingerprintUIHelper(ImageView mIvIcon, TextView mTvError, FingerprintManagerCompat fingerprintManagerCompat, Callback callback) {
        this.mIvIcon = mIvIcon;
        this.mTvError = mTvError;
        this.fingerprintManagerCompat = fingerprintManagerCompat;
        this.callback = callback;
    }

    /**
     *  是否有指纹感应区(硬件支持) && 是否已经录入了指纹
     * @return
     */
    public boolean isFingerprintAvailable() {
        return fingerprintManagerCompat == null ? false : fingerprintManagerCompat.isHardwareDetected() && fingerprintManagerCompat.hasEnrolledFingerprints();
    }


    /**
     * 开始识别
     * @param cryptoObject
     * @return
     */
    public boolean startListening(FingerprintManagerCompat.CryptoObject cryptoObject) {
        if (!isFingerprintAvailable()) return isFingerprintAvailable();
        cancellationSignal = new CancellationSignal();
        mSelfCancelled = false;
        fingerprintManagerCompat.authenticate(cryptoObject,0,cancellationSignal,this,null);
        mIvIcon.setImageResource(R.mipmap.ic_fp_40px);
        return true;
    }

    /**
     * 停止识别
     */
    public void stopListening(){
        if (null!=cancellationSignal){
            mSelfCancelled =true;
            cancellationSignal.cancel();
            cancellationSignal =null;
        }
    }

    /**
     * 显示错误并再一会后重置 提示
     * @param error
     */
    private void showError(CharSequence error) {
        mIvIcon.setImageResource(R.drawable.ic_fingerprint_error);
        mTvError.setText(error);
        mTvError.setTextColor(
                mTvError.getResources().getColor(R.color.warning_color));
        mTvError.removeCallbacks(mResetErrorTextRunnable);
        mTvError.postDelayed(mResetErrorTextRunnable, ERROR_TIMEOUT_MILLIS);
    }

    /**
     * 重置提示
     */
    private Runnable mResetErrorTextRunnable = new Runnable() {
        @Override
        public void run() {
            mTvError.setTextColor(
                    mTvError.getResources().getColor(R.color.hint_color));
            mTvError.setText(
                    mTvError.getResources().getString(R.string.fingerprint_hint));
            mIvIcon.setImageResource(R.mipmap.ic_fp_40px);
        }
    };

    /**
     * 指纹验证错误
     * 例如 尝试次数过多，请稍后重试。
     * @param errMsgId
     * @param errString
     */
    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        Log.e(FingerprintUIHelper.class.getSimpleName(), "onAuthenticationError: "+errString +";"+errMsgId);
        if (!mSelfCancelled) {
            showError(errString);
            //再 一会后 回调错误
//            mIvIcon.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    callback.onError();
//                }
//            }, ERROR_TIMEOUT_MILLIS);
        }
    }
    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        showError(helpString);
    }

    @Override
    public void onAuthenticationSucceeded(final FingerprintManagerCompat.AuthenticationResult result) {
        mTvError.removeCallbacks(mResetErrorTextRunnable);
        mIvIcon.setImageResource(R.drawable.ic_fingerprint_success);
        mTvError.setTextColor(
                mTvError.getResources().getColor(R.color.success_color));
        mTvError.setText(
                mTvError.getResources().getString(R.string.fingerprint_success));
        mIvIcon.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onAuthenticated(result);
            }
        }, SUCCESS_DELAY_MILLIS);
    }

    /**
     * 认证失败
     * 认证失败是指所有的信息都采集完整，并且没有任何异常，但是这个指纹和之前注册的指纹是不相符的；
     */
    @Override
    public void onAuthenticationFailed() {
        Log.e(FingerprintUIHelper.class.getSimpleName(), "onAuthenticationFailed: 请重试");

        showError(mIvIcon.getResources().getString(
                R.string.fingerprint_not_recognized));
    }

    public interface Callback {

        void onAuthenticated(FingerprintManagerCompat.AuthenticationResult result);

        void onError();
    }
}
