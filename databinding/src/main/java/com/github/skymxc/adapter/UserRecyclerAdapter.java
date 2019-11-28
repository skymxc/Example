package com.github.skymxc.adapter;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

import com.github.skymxc.BR;
import com.github.skymxc.R;
import com.github.skymxc.databinding.LayoutRecyclerItemUserBinding;
import com.github.skymxc.entity.User;

/**
 * <p>
 *
 * </p>
 *
 * @author 孟祥超
 * <p>
 * date: 2019/11/27  10:51
 */
public class UserRecyclerAdapter extends BaseRecyclerViewAdapter<User> {
    public UserRecyclerAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int loadLayout(int viewType) {
        return R.layout.layout_recycler_item_user;
    }

    @Override
    public void onBindItem(User item, BindingViewHolder holder) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(com.github.skymxc.BR.user,item);
        binding.setVariable(BR.eventHandler,eventsProxy);
    }
}
