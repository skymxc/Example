package com.github.skymxc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.github.skymxc.adapter.BaseRecyclerViewAdapter
import com.github.skymxc.adapter.UserRecyclerAdapter
import com.github.skymxc.databinding.ActivitySecondBinding
import com.github.skymxc.databinding.ActivitySecondBindingImpl
import com.github.skymxc.entity.User
import kotlinx.android.synthetic.main.activity_main.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //不好使
//        ActivitySecondBinding.inflate(layoutInflater)

        //创建绑定类
        var binding = DataBindingUtil.setContentView<ActivitySecondBinding>(this, R.layout.activity_second)
        var user = User()
        user.firstName = "first"
        user.lastName = "last"
//        binding.user = user
        binding.setVariable(BR.user,user)

        var listUser = listOf(User("三","张"),User("四","李"),User("五","王"))

        var adapter =UserRecyclerAdapter(this)
        adapter.setList(listUser)

       adapter.eventsProxy = EventsHandler(binding)
        binding.bindingSecondRecycler.adapter = adapter

        binding.eventHandler = EventsHandler()

    }

    class EventsHandler(val binding: ActivitySecondBinding) :BaseRecyclerViewAdapter.RecyclerItemEventsProxy<User>{

        override fun onClick(item: User?, view: View?) {
            binding.user = item
        }

        override fun onLongClick(item: User?, view: View?): Boolean {
            return false
        }

    }

}
