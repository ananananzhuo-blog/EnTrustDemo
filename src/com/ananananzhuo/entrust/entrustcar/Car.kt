package com.ananananzhuo.entrust.entrustcar

interface Car {
    fun drive()
    fun startupEngine()
}

class Taxi : Car {
    override fun drive() {
        println("开出租")
    }

    override fun startupEngine() {
        println("启动发动机，出发")
    }
}

class BrokenTaxi(car: Car) : Car by car {
    override fun startupEngine() {
        println("发动机坏了，开不了")
    }
}


fun main() {
    Taxi().run {
        drive()
        startupEngine()
    }
    println("------")
    BrokenTaxi(Taxi()).run {
        drive()//开车
        startupEngine()//启动发动机
    }
}