package com.github.skymxc.lifecycler

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(),BlankFragment.OnFragmentInteractionListener {

    lateinit var fragment: BlankFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        lifecycle.addObserver(MyObserver())
        fragment = BlankFragment.newInstance("","")
    }

    override fun onFragmentInteraction(uri: Uri) {

    }

    fun onClick(view:View){
        when(view.id){
            R.id.lifecycle_main_btn_add ->{
                supportFragmentManager.beginTransaction()
                        .replace(R.id.lifecycle_main_fl_container,fragment)
                        .commit()
            }
            R.id.lifecycle_main_btn_remove ->{
                supportFragmentManager.beginTransaction()
                        .remove(fragment)
                        .commit()
            }
        }

    }
}
