package com.github.skymxc.lifecycler

import androidx.lifecycle.*

/**
 * <p>
 *
 * </p>
 *
 * @author 孟祥超
 *
 * date: 2019/12/2  9:30
 */
class MainViewModel : ViewModel() {

    //1. 创建可观察的 LiveData
    val str: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    val age: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(12)
    }


    fun updateStr() {
        //更改 LiveData
        str.value = (Math.random() * 100).toInt().toString()
    }

    //应用 map
    fun getApplyMap() = Transformations.map(age) { "${age.value} 岁" }

    //应用 switchMap
    fun getApplySwitchMap() = Transformations.switchMap(age) { age ->
        when {
            age >= 60 -> {
                MutableLiveData<String>("老年人")
            }
            age >= 18 -> {
                MutableLiveData<String>("成年人")
            }
            else -> {
                MutableLiveData<String>("未成年人")
            }
        }
    }

    fun updateMapSource() {
        //修改这个会使上面那个数据改变从而引起ui的更改。
        age.value = (Math.random() * 100).toInt()
    }

    private val num1 = MutableLiveData(12)
    private val num2 = MutableLiveData(13)
    private val num3 = MutableLiveData(14)
    private val num4 = MutableLiveData(15)
    private val num5 = MediatorLiveData<Int>()

    init {
        var observer = Observer<Int> { value ->
            num5.value = value
        }
        //任何一个数据源的改变都会触发上面那个 观察者。
        num5.addSource(num1, observer)
        num5.addSource(num2, observer)
        num5.addSource(num3, observer)
        num5.addSource(num4, observer)
    }


    private fun formatNum(f: String, source: LiveData<Int>): LiveData<String> {
        return Transformations.map(source) { num -> "$f -> $num" }
    }

    fun getNum1() = formatNum("num1", num1)
    fun getNum2() = formatNum("num2", num2)
    fun getNum3() = formatNum("num3", num3)
    fun getNum4() = formatNum("num4", num4)
    fun getNum5() = formatNum("latest", num5)

    fun updateNum(index: Int) {
        when (index) {
            1 -> {
                num1.value = (Math.random() * 100).toInt()
            }
            2 -> {
                num2.value = (Math.random() * 100).toInt()
            }
            3 -> {
                num3.value = (Math.random() * 100).toInt()
            }
            4 -> {
                num4.value = (Math.random() * 100).toInt()
            }
        }
    }

}