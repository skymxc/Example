package com.skymxc.example.biometric.fingerprint;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.skymxc.example.biometric.R;

/**
 * Create By 孟祥超
 * Date: 2019/5/29  9:15
 * Description:
 */
public class FingerprintAuthenticationDialogFragment extends DialogFragment implements FingerprintUIHelper.Callback, View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Activity重建时 不创建新的 Fragment 实例
        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme_Material_Light_Dialog);
    }

    ImageView mIcon;
    TextView mTvError;
    Button mBtnNegative;
    FingerprintUIHelper mFingerprintUIHelper;

    FingerprintManagerCompat.CryptoObject cryptoObject;

    FingerprintCallback callback;

    public void setCryptoObject(FingerprintManagerCompat.CryptoObject cryptoObject) {
        this.cryptoObject = cryptoObject;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_fingerprint, container, false);
        mIcon = inflate.findViewById(R.id.fingerprint_icon);
        mTvError = inflate.findViewById(R.id.fingerprint_status);
        mBtnNegative = inflate.findViewById(R.id.btn_cancel);
        mBtnNegative.setOnClickListener(this);
        mFingerprintUIHelper = new FingerprintUIHelper(mIcon,mTvError, FingerprintManagerCompat.from(getContext()),this);
        return inflate;
    }
    public void setTittle(String title){
    }

    @Override
    public void onResume() {
        super.onResume();
        mFingerprintUIHelper.startListening(cryptoObject);
    }

    @Override
    public void onPause() {
        super.onPause();
        mFingerprintUIHelper.stopListening();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // bind Activity
    }

    @Override
    public void onAuthenticated(FingerprintManagerCompat.AuthenticationResult result) {
        dismiss();
        if (null!=callback)
        callback.onAuthenticated(result);
    }

    @Override
    public void onError() {
        mFingerprintUIHelper.stopListening();
        if (null!=callback)
        callback.onError();
    }

    public void setCallback(FingerprintCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
