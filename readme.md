关注公众号学习更多知识

![](https://files.mdnice.com/user/15648/404c2ab2-9a89-40cf-ba1c-02df017a4ae8.jpg)


## kotlin 委托
关于委托的介绍，我会用自己实现的多个例子把最常用的几个实现委托的方式进行讲解，描述会很清楚容易理解。

尤其最后会对android中ViewModel扩展库中的继承Lazy类懒初始化方式进行简单模拟，帮助你更好的理解

### 使用委托实现继承

使用 by 关键字实现委托

1. 代码说明

![](https://files.mdnice.com/user/15648/f3b5f38e-9218-4c89-9891-540cb90d0a68.png)
调用 BrokenTaxi 的 drive 方法的时候，会把方法调用转发给形参传入的 Taxi 的 drive 方法

编译器会优先使用复写的方法，比如我们的BrokenTaxi复写了startupEngin方法，那么便不会去调用Taxi的startupEngine方法

2. 输出结果

![](https://files.mdnice.com/user/15648/544f6082-62e5-4bf4-a154-34e2cb4ffe35.png)

因为BrokenTaxi表示坏掉的出租车，所以无法启动发动机，需要重写 startupEngine，当用户意图启动发动机的时候便告诉他无法启动

### 标准委托

#### 延迟属性

首次访问的时候初始化

lazy 会接受一个 lambda 并返回一个 Lazy <T> 实例的函数，返回的实例可以作为实现延迟属性的委托，第一次使用属性的时候会把 lambda 表达式的返回值传给属性

![](https://files.mdnice.com/user/15648/106d3560-fad9-4c3f-969b-16882c71f66b.png)

默认情况下，延迟属性的初始化是在同步锁中进行的，所有线程都可以观察到该值。

如果你确定初始化将总是发生在与属性使用位于相同的线程， 那么可以使用 LazyThreadSafetyMode.NONE 模式：它不会有任何线程安全的保证以及相关的开销，实现方式代码如下：

```
 val laze2 :String by lazy(LazyThreadSafetyMode.NONE){
        "安安安安卓"
    }
```

#### 可观察属性

监听器会收到此属性变更的通知
  
  可观察属性有两种实现的方式：observable、vetoable
  
  其中observable的方式可以在属性发生改变的时候收到回调；
  
  vetoable的方式也可以收到回调，但是使用vetoable的时候我们可以根据条件进行拦截，来决定是否要更新属性的值；

##### 使用 observable 方式创建

![](https://files.mdnice.com/user/15648/6fcf885c-d1ff-49c2-a8b1-6f1e94ea6c0e.png)
当我们更改 field 的值的时候，兰布达表达式会被回调

##### 使用 vetoable 方式创建

vetoable 方式创建可观察属性也很重要，我们可以在兰布达表达式里进行拦截，然后决定是否应该更新属性的值
![](https://files.mdnice.com/user/15648/904c6ed9-5631-4927-b6ca-b686f929bca6.png)

#### 将属性存储在映射中

我们可以使用 map 映射来委托属性，map 的 key 就是被委托属性的名称，map 的 value 就是被委托属性的值

![](https://files.mdnice.com/user/15648/9552512c-8c85-4a97-b115-62c1a97ef0a4.png)

### 局部委托属性

![](https://files.mdnice.com/user/15648/78b05ed0-f000-4fa8-8d68-39741499aa32.png)

### 属性委托要求

属性委托本应最先讲的，因为我最开始了解委托的时候就对这里理解的很慢，所以让你先看看前面的东西可能更容易接受这里的属性委托。

属性委托必须提供一个 getValue 方法，如果是 var 的属性还要提供 setValue 方法

1. 属性委托的基本原理讲解

![](https://files.mdnice.com/user/15648/5cd320c2-a8b9-4e6b-a423-b86d57959fcf.png)

2. 属性的更改和委托类中的方法调用

关于属性委托，如果被委托属性 field 被设置为 var，那么委托类 ResourceDelete 就需要同时实现 getValue 和 setValue 方法。

那么不禁有以下疑问：

1、是否每次我们使用 field 属性引用都会调用 getValue 方法 （答案：yes）
2、是否每次我们更改 field 属性引用都会调用 setValue 方法 (答案：yes)

代码太多，截图已经放不下了，上代码吧

运行完代码会放出上面两条结论的验证

```

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
```

![](https://files.mdnice.com/user/15648/295b0223-f431-4914-8330-1560663d6a95.png)

### 使用 Lazy 类实现委托

#### Lazy 的描述

kotlin 为我们提供了一个 Lazy类帮助我们实现更复杂的懒加载，Lazy 的描述我已经翻译后截图，如下：

![](https://files.mdnice.com/user/15648/0ab0afe6-51cf-4600-bb45-23b23088d56d.png)

也就是说 Lazy 可以帮我们做这样一件事，我们要获取的属性的值使用 value 存放，并且提供了 isInitialized 方法判断属性是否已经被初始化。

如果已经被初始化，那么当我们用以下代码获取属性的时候值便不会被重新设置。

```
private val engine: Engine by engine()
```

#### 使用 Lazy 实现懒加载

我们做了这样的一个实现：

创建一个 Car 汽车类，汽车类中持有一个引擎 Engine 类，引擎 engine 便是使用懒加载的方式初始化，看代码：

```


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
```

代码执行结果：

![](https://files.mdnice.com/user/15648/d2715a24-d119-4b47-bdba-761936c68f70.png)

关于 Lazy 方式实现的懒加载，如果想对更复杂的应用场景进行学习，推荐你去阅读 android ViewModel 扩展库中对 ViewModel 的初始化方式源码，会收货颇丰

  最后我给自己带个盐：
  
> 欢迎关注我的公众号 **“安安安安卓”** 学习更多知识