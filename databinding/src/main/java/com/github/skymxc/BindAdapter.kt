package com.github.skymxc

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.github.skymxc.data.GlideApp
import com.github.skymxc.data.MyGlideAPP

/**
 * <p>
 *
 * </p>
 *
 * @author 孟祥超
 *
 * date: 2019/11/27  16:15
 */

@BindingAdapter("imageUrl", "error", "placeholder")
fun loadImage(view: ImageView, url: String, error: Drawable, placeholder: Drawable) {
    Log.e("loadImage","url->$url")
    GlideApp.with(view.context)
            .load(url)
            .error(error)
            .placeholder(placeholder)
            .into(view)
}

//绑定侦听器需要的是一个抽象类或者接口
//@BindingAdapter("android:onClick")
//fun setOnClick(view:Button,eventsHandler: EventsHandler){
//    view.setOnClickListener {
//        eventsHandler.updateChecked()
//    }
//}