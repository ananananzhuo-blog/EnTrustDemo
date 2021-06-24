package com.ananananzhuo.entrust.localentrust

class LocalEnTrust {

    fun print(func: () -> Foo) {
        val field by lazy(func)
        print(field.getData())
    }
}
class Foo {
    fun getData():Int{
        return 23
    }
}

fun main() {
    LocalEnTrust().print {
        Foo()
    }
}