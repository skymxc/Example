package com.github.skymxc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 孟祥超
 * <p>
 * date: 2019/11/27  10:39
 */
public abstract class BaseRecyclerViewAdapter<M> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BindingViewHolder> {

    protected List<M> mList = new ArrayList<>();
    protected Context mContext;
    protected RecyclerItemEventsProxy eventsProxy;

    public BaseRecyclerViewAdapter(List<M> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public BaseRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<M> list) {
        this.mList = list;
    }

    public RecyclerItemEventsProxy getEventsProxy() {
        return eventsProxy;
    }

    public void setEventsProxy(RecyclerItemEventsProxy eventsProxy) {
        this.eventsProxy = eventsProxy;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), loadLayout(viewType), parent, false);
        return new BindingViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        M m = mList.get(position);
        this.onBindItem( m, holder);
    }

    public abstract @LayoutRes
    int loadLayout(int viewType);

    public abstract void onBindItem(M item, BindingViewHolder holder);

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    public static class BindingViewHolder extends RecyclerView.ViewHolder {

        public BindingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public ViewDataBinding getBinding(){
            return DataBindingUtil.getBinding(itemView);
        }
    }

    public  interface RecyclerItemEventsProxy<M> {
        void onClick(M item, View view);

        boolean onLongClick(M item, View view);
    }
}
