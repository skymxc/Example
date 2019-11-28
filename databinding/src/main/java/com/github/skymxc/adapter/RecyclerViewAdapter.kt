package com.github.skymxc.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * <p>
 *
 * </p>
 *
 * @author 孟祥超
 *
 * date: 2019/11/27  12:01
 */
abstract class RecyclerViewAdapter<M, V : ViewDataBinding>(var list: List<M> = listOf(),
                                                           var context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {



    override fun getItemCount(): Int {
        return list.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        var binding: V = DataBindingUtil.inflate(LayoutInflater.from(context), getLayout(viewType), parent, false)
        return RecyclerViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        var item = list[position]
        var binding: V? = DataBindingUtil.getBinding(holder.itemView)
        this.onBindItem(binding, item, holder)
    }

    abstract fun getLayout(viewType: Int): Int

    abstract fun onBindItem(binding: V?, item: M, holder: RecyclerViewHolder)

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
