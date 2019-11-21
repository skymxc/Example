package com.skymxc.example.glide;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 孟祥超
 * <p>
 * date: 2019/11/19  10:38
 */
public class PicturePagerAdapter extends FragmentStatePagerAdapter {

    private List<PictureFragment> list;

    public PicturePagerAdapter(List<PictureFragment> list, FragmentManager manager) {
        super(manager);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public PictureFragment getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.e(PicturePagerAdapter.class.getSimpleName(), "instantiateItem: "+position );
        Drawable drawable = list.get(current).getDrawable();
        list.get(position).setPlaceholder(drawable);
        return super.instantiateItem(container, position);
    }

    private int current= 0;


    public void setCurrent(int current) {
        this.current = current;
    }
}
