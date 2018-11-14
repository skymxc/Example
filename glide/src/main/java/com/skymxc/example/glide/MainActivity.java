package com.skymxc.example.glide;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.skymxc.example.glide.module.GlideApp;
import com.skymxc.example.glide.module.GlideRequest;
import com.skymxc.example.glide.module.GlideRequests;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TOP_URL = "https://ws1.sinaimg.cn/large/0065oQSqly1fubd0blrbuj30ia0qp0yi.jpg";
    ImageView mIVTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIVTop = findViewById(R.id.iv_top);
        findViewById(R.id.btn_clean_top).setOnClickListener(this);
        findViewById(R.id.btn_load_top).setOnClickListener(this);
        findViewById(R.id.btn_apply_options).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_load_top:
                GlideApp.with(this)
                        .load(TOP_URL)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error)
                        .into(mIVTop);
                break;
            case R.id.btn_clean_top:
                GlideApp.with(this).clear(mIVTop);
                break;
            case R.id.btn_apply_options:
                GlideRequest<Drawable> glideRequest = GlideApp.with(this)
                        .load(TOP_URL);

                glideRequest.placeholder(R.drawable.placeholder);
                glideRequest.error(R.drawable.error);

                glideRequest.apply(RequestOptions.circleCropTransform());

                glideRequest.transition(DrawableTransitionOptions.withCrossFade(2000));
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                glideRequest.into(mIVTop);
                break;
        }
    }
}
