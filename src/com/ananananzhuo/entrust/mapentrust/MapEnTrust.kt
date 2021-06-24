package com.ananananzhuo.entrust.mapentrust

class MapEnTrust( map: Map<String, Any>) {
    val name: String by map
    val age: Int by map
}

fun main() {
    MapEnTrust(mapOf("name" to "李白", "age" to 1500)).apply {
        println(name)
        println(age)
    }
}