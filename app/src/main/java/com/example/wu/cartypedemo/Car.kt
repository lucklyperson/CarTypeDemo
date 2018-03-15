package com.example.wu.cartypedemo

/**
 * Created by wu on 2018/3/15.
 */
class Car {
    var id: String = "" //id
    var name: String = "" //名称
    var initial: String = "" //首字母
    var color: String = ""//颜色
    var type: String = "" //类型
    var logo: String = ""  //车标
    var carlist: List<Car>? = null//子品牌

    override fun toString(): String {
        return "Car(id='$id', name='$name', initial='$initial', color='$color', type='$type',  logo='$logo', carlist=$carlist)"
    }

}

