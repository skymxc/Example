package com.github.skymxc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.*
import com.github.skymxc.databinding.ActivityMainBinding
import com.github.skymxc.entity.Banner
import com.github.skymxc.entity.User
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.banner = Banner(0, "Description", ObservableField("imagePath"), 9, ObservableInt(1), "Title", 1, "url")
        var map = mapOf("key" to "第一个", "key2" to "第二个")
        var list = listOf("list 里的值 ", "list 的第二个值")
        var sparse = SparseArray<String>()
        sparse.put(0, "sparse 里的第一个值")
        sparse.put(1, "sparse 里的第二个值")

        binding.map = map
        binding.index = 0
        binding.key = "key"
        binding.list = list
        binding.sparse = sparse;
        binding.eventsHandler = this
        binding.eventsHandler2 = EventsHandler()

        //可观察对象
        var user = User()
        user.age = 23
        user.firstName = "userFirstName"
        user.lastName = "userLastName"
        binding.user = user

        //可观察集合
        var observableList = ObservableArrayList<Any>().apply {
            add(999)
        }
        binding.observableList = observableList

        var observableMap = ObservableArrayMap<String, Any>().apply {
            put("firstName", "名字啦")
            put("age", 199)
        }
        binding.observableMap = observableMap



    }

    fun onClick(view: View) {
        Log.e(this.localClassName, "更改 index")
        binding.index = 1
        binding.banner?.url = "更改了 url"
    }

    fun onTouch(view: View, event: MotionEvent): Boolean {
        Log.e(this.localClassName, "被触摸了----${view}")
        return true
    }

    fun printMap(map: Map<String, String>) {
        Log.e(this.localClassName, "map --> ${map}")
        val buffer = StringBuffer()
        map.forEach {
            Log.e(this.localClassName, " ${it.key} : ${it.value}")
            buffer.append(it.key)
            buffer.append(":")
            buffer.append(it.value)
            buffer.append("\n")
        }
        binding.string = buffer.toString()
    }

    fun showToast(view: View) {
        view.isVisible
        view.visibility = View.INVISIBLE
        Toast.makeText(this, "被点击了", Toast.LENGTH_SHORT).show()
    }

    fun toSecondActivity() {
        var intent = Intent(this, SecondActivity::class.java);
        startActivity(intent)
    }
}
