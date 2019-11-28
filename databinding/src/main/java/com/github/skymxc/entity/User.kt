package com.github.skymxc.entity

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.github.skymxc.BR

/**
 * <p>
 * 可观察对象，如果全部字段都是可观察的继承 BaseObservable 即可。
 * </p>
 *
 * @author 孟祥超
 *
 * date: 2019/11/25  20:21
 */

class User(firstName: String,
           lastName: String,
           age: Int) : BaseObservable() {

    constructor() : this("", "", 0)

    constructor(firstName: String, lastName: String) : this(firstName, lastName, 0)

    @get:Bindable
    var firstName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }
    @get:Bindable
    var lastName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
        }
    @get:Bindable
    var age: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.age)
        }

    init {
        this.firstName = firstName
        this.lastName = lastName
        this.age = age
    }
}