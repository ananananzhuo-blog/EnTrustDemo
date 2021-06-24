package com.ananananzhuo.entrust.observablefield

import kotlin.properties.Delegates

class VetoableField {
    var vetoField: Int by Delegates.vetoable(100) { _, old, new ->
        new in 0..old
    }
}

fun main() {
    VetoableField().let {
        println("初始值：${it.vetoField}")
        println("设置新值101")
        it.vetoField = 101
        println("查看新值是否设置成功${it.vetoField == 101}")
        println("设置新值50")
        it.vetoField = 50
        println("查看新值是否设置成功${it.vetoField == 50}")

    }
}