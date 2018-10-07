package com.skymxc.example.exchange;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FrameLayout flBig;
    FrameLayout flSmall1;
    FrameLayout flSmall2;
    FrameLayout flSmall3;
    ConstraintLayout constraint;
    LinearLayout llControl;

    ConstraintSet mResetConstraint = new ConstraintSet();

    private String mScreentID;
    private ArrayList<FrameLayout> mPlayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flBig = findViewById(R.id.e_fl_big);
        flSmall1 = findViewById(R.id.e_fl_small_1);
        flSmall2 = findViewById(R.id.e_fl_small_2);
        flSmall3 = findViewById(R.id.e_fl_small_3);
        constraint = findViewById(R.id.e_constraint);
        llControl = findViewById(R.id.e_ll_control);
        flBig.setOnClickListener(this);
        flSmall1.setOnClickListener(this);
        flSmall2.setOnClickListener(this);
        flSmall3.setOnClickListener(this);
        findViewById(R.id.e_bt_small_1).setOnClickListener(onClickListener);
        findViewById(R.id.e_bt_small_2).setOnClickListener(onClickListener);
        findViewById(R.id.e_bt_small_3).setOnClickListener(onClickListener);
        findViewById(R.id.e_bt_reset).setOnClickListener(onClickListener);

        mResetConstraint.clone(constraint);

        //default big is mxc;
        flBig.setTag("mxc");
        mScreentID = String.valueOf(flBig.getTag());

        mPlayList.add(flBig);
        mPlayList.add(flSmall1);
        mPlayList.add(flSmall2);
        mPlayList.add(flSmall3);

    }

    @Override
    public void onClick(View v) {
        if (v.getVisibility()!=View.VISIBLE){
            return;
        }


        switch (v.getId()){
            case R.id.e_fl_big:
                toggleView(flBig);
                break;
            case R.id.e_fl_small_1:
                toggleView(flSmall1);
                break;
            case R.id.e_fl_small_2:
                toggleView(flSmall2);
                break;
            case R.id.e_fl_small_3:
                toggleView(flSmall3);
                break;
        }
    }


    /**
     * 将 这个 view 放到 大屏
     * @param frameLayout
     */
    private void toggleView(FrameLayout frameLayout){
        if (!isVisible(frameLayout)) return;
        if (frameLayout.getTag()==null) return;
        if (String.valueOf(frameLayout.getTag()).equals(mScreentID)) return;

        mScreentID = "mxc";
        mResetConstraint.applyTo(constraint);

        if (frameLayout.getId()!=R.id.e_fl_big) {


            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraint);

            int smallHeight = frameLayout.getHeight();
            int smallWidth = frameLayout.getWidth();

            int bigWidth = constraint.getWidth();
            int bigHeight = constraint.getHeight();

            constraintSet.clear(R.id.e_fl_big);
            constraintSet.clear(frameLayout.getId());

            //尺寸
            constraintSet.constrainWidth(frameLayout.getId(), bigWidth);
            constraintSet.constrainHeight(frameLayout.getId(), bigHeight);
            constraintSet.constrainHeight(R.id.e_fl_big, smallHeight);
            constraintSet.constrainWidth(R.id.e_fl_big, smallWidth);

            // 约束
            constraintSet.connect(R.id.e_fl_big, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);

            constraintSet.connect(frameLayout.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
            constraintSet.connect(frameLayout.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
            constraintSet.connect(frameLayout.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
            constraintSet.connect(frameLayout.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

            // 处理 其他布局对这个的约束
            if (frameLayout.getId() == R.id.e_fl_small_1) {

                constraintSet.connect(R.id.e_fl_big, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                constraintSet.connect(R.id.e_fl_small_2, ConstraintSet.BOTTOM, R.id.e_fl_big, ConstraintSet.TOP);

            } else if (frameLayout.getId() == R.id.e_fl_small_2) {
                constraintSet.connect(R.id.e_fl_small_3, ConstraintSet.BOTTOM, R.id.e_fl_big, ConstraintSet.TOP);
                constraintSet.connect(R.id.e_fl_big, ConstraintSet.BOTTOM, R.id.e_fl_small_1, ConstraintSet.TOP);

            }else if (frameLayout.getId()==R.id.e_fl_small_3){
                constraintSet.connect(R.id.e_fl_big,ConstraintSet.BOTTOM,R.id.e_fl_small_2,ConstraintSet.TOP);
            }

            constraintSet.applyTo(constraint);
            mScreentID = String.valueOf(frameLayout.getTag());
        }

        for (FrameLayout frame:mPlayList){
            if (frame.getTag()!=null){
                frame.setVisibility(View.VISIBLE);
                if (!String.valueOf(frame.getTag()).equals(mScreentID)) {
                    frame.bringToFront();
                }
            }
        }
        llControl.bringToFront();


    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.e_bt_small_1:
                    if (isVisible(flSmall1)){
                        flSmall1.setTag(null);
                        flSmall1.setVisibility(View.GONE);
                    }else{
                        flSmall1.setTag("small_1");
                        flSmall1.setVisibility(View.VISIBLE);
                        flSmall1.bringToFront();
                    }
                    break;
                case R.id.e_bt_small_2:
                    if (isVisible(flSmall2)){
                        flSmall2.setTag(null);
                        flSmall2.setVisibility(View.GONE);
                    }else{
                        flSmall2.setTag("small_2");
                        flSmall2.setVisibility(View.VISIBLE);
                        flSmall2.bringToFront();
                    }
                    break;
                case R.id.e_bt_small_3:
                    if (isVisible(flSmall3)){
                        flSmall3.setTag(null);
                        flSmall3.setVisibility(View.GONE);
                    }else{
                        flSmall3.setTag("small_3");
                        flSmall3.setVisibility(View.VISIBLE);
                        flSmall3.bringToFront();
                    }
                    break;
                case R.id.e_bt_reset:
                    mResetConstraint.applyTo(constraint);
                    for (FrameLayout frameLayout:mPlayList){
                        if (frameLayout.getTag()!=null){
                            frameLayout.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
            }
        }
    };

    private boolean isVisible(View view){
        return view.getVisibility()==View.VISIBLE;
    }
}
