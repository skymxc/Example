package com.github.skymxc.lifecycler.entity

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * <p>
 *
 * </p>
 *
 * @author 孟祥超
 *
 * date: 2019/12/2  14:09
 */
data class User(val firstName: ObservableField<String>,
                val lastName: ObservableField<String>)

data class User2(val firstName: MutableLiveData<String>,
                 val lastName: MutableLiveData<String>)

data class User3(var firstName: String,
                 var lastName:String)