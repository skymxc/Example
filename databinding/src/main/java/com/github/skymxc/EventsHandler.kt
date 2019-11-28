package com.github.skymxc

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.databinding.*
import com.github.skymxc.entity.Banner
import com.github.skymxc.entity.User
import java.util.*

/**
 * <p>
 *
 * </p>
 *
 * @author 孟祥超
 *
 * date: 2019/11/22  17:46
 */
class EventsHandler : BaseObservable() {

    //为实现双向绑定
    @get:Bindable
    var checked: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.checked)
        }

    private val TAG = "EventsHandler"
    fun printList(list: List<String>) {
        list.forEach {
            Log.e(TAG, "printList -> ${it}")
        }
    }

    fun printMap(view: View, map: Map<String, String>): Boolean {
        if (view is Button)
            view.text = "print map"
        Log.e(TAG, "---------print map ---------")
        map.forEach {
            Log.e(TAG, "${it.key}:${it.value}")
        }
        return true
    }

    /**
     * 更改数据后，查看 UI 是否被自动更改
     */
    fun updateValue(user: User, banner: Banner, map: ObservableMap<String, Any>, list: ObservableList<Any>) {
        banner.imagePath.set("更改后的 imagePath")
        banner.order.set(12)
        banner.url = "更改后的 URL"
        user.firstName = "更改后的 firstName"
        user.lastName = "更改后的 lastName"
        user.age = 90

        map["firstName"] = "更改后的名字啦"
        list[0] = 82
    }

    fun updateChecked() {
            checked = !checked
            Log.e(TAG, "checked->$checked")
    }
}