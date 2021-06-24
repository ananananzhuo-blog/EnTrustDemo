package com.ananananzhuo.entrust.lazyfield

class LazyField{
    val lazy :String by lazy{
        println("调用兰布达表达式获取属性值")
        "安安安安卓"
    }
    val laze2 :String by lazy(LazyThreadSafetyMode.NONE){
        "安安安安卓"
    }
}

fun main() {
    LazyField().let {
        println(it.lazy)
        println(it.lazy)
    }
}