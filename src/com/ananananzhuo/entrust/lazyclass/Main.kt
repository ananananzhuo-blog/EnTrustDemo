package com.ananananzhuo.entrust.lazyclass

fun main() {
    Car().apply {
        myEngine()
        myEngine()
        myEngine()
    }
}

/**
 * 声明汽车类
 */
class Car {
    private val engine: Engine by engine()//使用扩展函数懒初始化引擎
    private val engine1: Engine by engine()//使用扩展函数懒初始化引擎
    fun myEngine() {
        engine.showIntroduce()
        engine1.showIntroduce()
    }
}

/**
 * 声明引擎类
 */
class Engine(private val describe: String) {

    fun showIntroduce() {
        println(describe)
    }
}

/**
 * 创建获取引擎的扩展函数，
 */
fun Car.engine(): LazyEngine {
    return LazyEngine()
}

/**
 * 引擎初始化类，实现Lazy类，泛型传引擎Engine类
 */
class LazyEngine : Lazy<Engine> {
    private var cache: Engine? = null

    /**
     * 如果引擎缓存cache不为空，说明已经被初始化，否则不会被初始化
     */
    override fun isInitialized(): Boolean {
        return cache != null
    }

    /**
     * 获取引擎值
     */
    override val value: Engine
        get() {
            return if (cache == null) {//引擎没有的时候创建，有的话便返回cache值
                cache=Engine("旁白(安安安安卓)：长安马自达，双涡轮增压引擎")
                cache!!
            } else {
                cache!!
            }
        }
}