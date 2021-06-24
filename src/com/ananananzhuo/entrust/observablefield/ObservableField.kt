package com.ananananzhuo.entrust.observablefield

import kotlin.properties.Delegates

class ObservableField {
    var field: String by Delegates.observable("安安安安卓") { property, oldValue, newValue ->
        println("属性值：${property}  旧值：$oldValue  新值： $newValue")
    }
}

fun main() {
    ObservableField().let {
        println(it.field)
        it.field = "安安安安卓，你变了"
        println(it.field)

    }
}