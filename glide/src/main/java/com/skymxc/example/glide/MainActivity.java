package com.skymxc.example.glide;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.skymxc.example.glide.module.GlideApp;
import com.skymxc.example.glide.module.GlideRequest;
import com.skymxc.example.glide.module.GlideRequests;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TOP_URL = "https://ws1.sinaimg.cn/large/0065oQSqly1fubd0blrbuj30ia0qp0yi.jpg";
    ImageView mIVTop;
    RadioGroup mRGOption;
    RadioGroup mRGTransition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIVTop = findViewById(R.id.iv_top);
        mRGOption = findViewById(R.id.rg_options);
        mRGTransition = findViewById(R.id.rg_transitions);
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
                //clean 后 变成了 placeholder ???
                GlideApp.with(this).clear(mIVTop);
                break;
            case R.id.btn_apply_options:
                //应用选项示例 直接 使用 RequestOptions 提供的 静态 API ；其中提供了很多常用的API
                GlideRequest<Drawable> glideRequest = GlideApp.with(this)
                        .load(TOP_URL);

                glideRequest.placeholder(R.drawable.placeholder);
                glideRequest.error(R.drawable.error);
                if (mRGOption.getCheckedRadioButtonId()==R.id.rb_option_circle) {
                    glideRequest.circleCrop();
//                    glideRequest.apply(RequestOptions.circleCropTransform());
//                    glideRequest.circleCrop();
                }else if (mRGOption.getCheckedRadioButtonId()==R.id.rb_center_crop){
                    glideRequest.centerCrop();
//                    glideRequest.apply(RequestOptions.centerCropTransform());
//                    glideRequest.centerCrop();
                }

                //加载一个普通的图片 如何判断使用 bitmap 还是 Drawable
                int trasitionId = mRGTransition.getCheckedRadioButtonId();
                if (trasitionId==R.id.rb_no_transition){
                    //无效果，直接跳入
                    glideRequest.dontAnimate();
                }else if (trasitionId==R.id.rb_transition_crossfade){
                    //交叉淡入 毫秒
                    glideRequest.transition(DrawableTransitionOptions.withCrossFade(1000));
                }else if (trasitionId==R.id.rb_transition_fade){
                    //淡入
                }

                glideRequest.into(mIVTop);
                break;
        }
    }
}
