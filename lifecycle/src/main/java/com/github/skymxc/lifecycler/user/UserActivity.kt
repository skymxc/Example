package com.github.skymxc.lifecycler.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.skymxc.lifecycler.R
import com.github.skymxc.lifecycler.databinding.ActivityUserBinding
import com.github.skymxc.lifecycler.entity.User
import com.github.skymxc.lifecycler.entity.User2
import com.github.skymxc.lifecycler.entity.User3

class UserActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bind = DataBindingUtil.setContentView<ActivityUserBinding>(this, R.layout.activity_user)
        bind.user = User(ObservableField("firstName"), ObservableField("lastName"))
        bind.eventHandler = EventsProxy()
        bind.user2 = User2(MutableLiveData("firstName"), MutableLiveData("lastName"))

        bind.user3 = User3("firstName", "lastName")
        //要使用 LiveData ,这句是必须的，设置生命周期所有者是当前 Activity，否则的话，属性的改变通知不到。
        bind.lifecycleOwner = this

        //使用 LiveData 可观察对象

        var viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        bind.vm = viewModel

        viewModel.user4.observe(this, Observer { user->
            bind.lifecycleUserTvName4.text = "name4 -> ${user.firstName } · ${user.lastName}"
        })

        viewModel.getName().observe(this, Observer { name->
        bind.lifecycleUserTvName5.text = name})
    }

    class EventsProxy {
        fun updateUserName(user: User) {
            user.firstName.set((Math.random() * 100).toInt().toString())
            user.lastName.set((Math.random() * 100).toInt().toString())
        }

        fun updateUserName2(user: User2) {
            user.firstName.value = (Math.random() * 100).toInt().toString()
            user.lastName.value = (Math.random() * 100).toInt().toString()
        }

        fun updateUserName3(user: User3) {
            user.firstName = (Math.random() * 100).toInt().toString()
            user.lastName = (Math.random() * 100).toInt().toString()
            //这个是肯定不会自动更新的，1.没有使用可观察字，2.不是可观察对象。
        }

    }
}
