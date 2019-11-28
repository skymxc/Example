package com.github.skymxc.entity

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

/**
 * <p>
 * 首页 banner
 * </p>
 *
 * @author 孟祥超
 *
 * date: 2019/11/16  9:24
 * 数据类必须满足的条件：
 * 1. 主构造函数必须有一个主参数
 * 2. 主构造函数的所有参数需要标记为 val 或 var
 * 3. 数据类不能是抽象，开放，密封或者内部的
 * 4. 数据类可以实现接口的，如（序列化接口），同时也是可以继承自其他类的。
 *
 * imagePath,order 是后来改为可观察字段的。
 */

data class Banner  constructor(var id: Int,
                                     var desc: String,
                                     val imagePath: ObservableField<String>,
                                     var isVisible: Int,
                                     var order: ObservableInt,
                                     var title: String,
                                     var type: Int,
                                     var url: String?) {


    constructor( id:Int, desc:String) : this(id,desc, ObservableField(""),1,ObservableInt(1),"",0,null)
}