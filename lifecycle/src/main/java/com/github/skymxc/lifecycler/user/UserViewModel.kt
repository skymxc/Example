package com.github.skymxc.lifecycler.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.github.skymxc.lifecycler.entity.User3

/**
 * <p>
 *
 * </p>
 *
 * @author 孟祥超
 *
 * date: 2019/12/2  15:58
 */
class UserViewModel : ViewModel() {

    val user4: MutableLiveData<User3> by lazy {
        MutableLiveData(User3("firstName", lastName = "lastName"))
    }


    fun updateUserName4() {
        user4.value = User3((Math.random() * 100).toInt().toString(),
                (Math.random() * 100).toInt().toString())
    }

    private val name: MutableLiveData<String> by lazy {
        MutableLiveData("name")
    }

    fun getName() = Transformations.map(name) { str -> "name5 -> $str" }

    fun updateName() {
        name.value = (Math.random() * 100).toInt().toString()
    }
}