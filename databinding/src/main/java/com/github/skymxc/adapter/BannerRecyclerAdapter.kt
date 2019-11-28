package com.github.skymxc.adapter

import android.content.Context
import com.github.skymxc.R
import com.github.skymxc.databinding.LayoutBannerBinding
import com.github.skymxc.entity.Banner

/**
 * <p>
 *
 * </p>
 *
 * @author 孟祥超
 *
 * date: 2019/11/27  13:58
 */
class BannerRecyclerAdapter(list:List<Banner>,context: Context):RecyclerViewAdapter<Banner,LayoutBannerBinding>(list,context){
    override fun getLayout(viewType: Int): Int {
        return R.layout.layout_banner
    }

    override fun onBindItem(binding: LayoutBannerBinding?, item: Banner, holder: RecyclerViewHolder) {
        binding?.banner = item
    }

}