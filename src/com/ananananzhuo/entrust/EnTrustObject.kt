package com.ananananzhuo.entrust

import kotlin.reflect.KProperty

class EnTrustObject {
    var field: Resource by ResourceDelete()
}
class ResourceDelete {
    var resource: Resource = Resource("初始的对象")
    operator fun getValue(enTrustObject: EnTrustObject, property: KProperty<*>): Resource {
        println("调用了getValue方法")
        return resource
    }

    operator fun setValue(enTrustObject: EnTrustObject, property: KProperty<*>, resource: Resource) {
        this.resource = resource
        println("调用了setValue方法")
    }

}
class Resource(val value:String) {
    fun print() {
        println("$value 安安安安卓打印资源")
    }
}

fun main() {
    EnTrustObject().apply {
        field.print()
        field= Resource("被改变的对象")
        field.print()
        field.print()
    }
}