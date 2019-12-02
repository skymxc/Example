package com.github.skymxc.lifecycler

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.github.skymxc.lifecycler.user.UserActivity

class MainActivity : AppCompatActivity(), BlankFragment.OnFragmentInteractionListener {

    lateinit var fragment: BlankFragment
    lateinit var model: MainViewModel

    lateinit var mTvStr: TextView
    lateinit var mTvStr1: TextView
    lateinit var mTvStr2: TextView

    lateinit var mTvNum1: TextView
    lateinit var mTvNum2: TextView
    lateinit var mTvNum3: TextView
    lateinit var mTvNum4: TextView
    lateinit var mTvNum5: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        lifecycle.addObserver(MyObserver())
        fragment = BlankFragment.newInstance("", "")

        mTvStr = findViewById(R.id.lifecycle_main_tv_str)
        mTvStr1 = findViewById(R.id.lifecycle_main_tv_str_1)
        mTvStr2 = findViewById(R.id.lifecycle_main_tv_str_2)
        mTvNum1 = findViewById(R.id.lifecycle_main_tv_num_1)
        mTvNum2 = findViewById(R.id.lifecycle_main_tv_num_2)
        mTvNum3 = findViewById(R.id.lifecycle_main_tv_num_3)
        mTvNum4 = findViewById(R.id.lifecycle_main_tv_num_4)
        mTvNum5 = findViewById(R.id.lifecycle_main_tv_num_5)

        // 得到 Model
        model = ViewModelProviders.of(this).get(MainViewModel::class.java)

        //2. 创建观察者
        val strObserver = Observer<String> { str ->
            //更改 UI
            mTvStr.setText(str)
        }

        //3. 订阅 LiveData
        model.str.observe(this, strObserver)


        val ageObserver = Observer<String> { str ->
            mTvStr1.setText(str)
        }
        val ageObserver1 = Observer<String> { str -> mTvStr2.text = str }
        model.getApplyMap().observe(this, ageObserver)
        model.getApplySwitchMap().observe(this, ageObserver1)

        model.getNum1().observe(this, Observer { str -> mTvNum1.text = str })
        model.getNum2().observe(this, Observer { str -> mTvNum2.text = str })
        model.getNum3().observe(this, Observer { str -> mTvNum3.text = str })
        model.getNum4().observe(this, Observer { str -> mTvNum4.text = str })
        model.getNum5().observe(this, Observer { str -> mTvNum5.text = str })
    }

    override fun onFragmentInteraction(uri: Uri) {

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.lifecycle_main_btn_add -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.lifecycle_main_fl_container, fragment)
                        .commit()
            }
            R.id.lifecycle_main_btn_remove -> {
                supportFragmentManager.beginTransaction()
                        .remove(fragment)
                        .commit()
            }
            R.id.lifecycle_main_btn_update_str -> {
                model.updateStr()
            }
            R.id.lifecycle_main_btn_apply_map -> {
                model.updateMapSource()
            }
            R.id.lifecycle_main_btn_to_user -> {
                var intent = Intent(this, UserActivity::class.java)
                startActivity(intent)
            }
            R.id.lifecycle_main_btn_update_num_1 -> {
                model.updateNum(1)
            }
            R.id.lifecycle_main_btn_update_num_2 -> {
                model.updateNum(2)
            }
            R.id.lifecycle_main_btn_update_num_3 -> {
                model.updateNum(3)
            }
            R.id.lifecycle_main_btn_update_num_4 -> {
                model.updateNum(4)
            }
        }

    }
}
