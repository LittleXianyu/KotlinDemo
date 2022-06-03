package com.example.myapplication.coroutine

class InlineDemo

/**
 * https://blog.csdn.net/u013064109/article/details/78786646/
 */

fun demolet() {
    val result = "hello let".let {
        println(it.length)
        "end code"
    }
    println(result)
}

fun demowith() {
    val result = with("hello with") {
        println("with, $length")
        "end code"
    }
    println(result)
}

// 类似let+with，可以判空
fun demorun() {
    var codestring: String
    codestring = "hello run"
    codestring = codestring.run {
        println("$this, $length")
        ""
    }
    if (codestring == null) {
        println("codestring = null")
    } else {
        println(codestring == null)
    }
//    println(codestring)
}

// run函数是以闭包形式返回最后一行代码的值，而apply函数的返回的是传入对象的本身
fun demoapply() {
    var codestring: String
    codestring = "hello apply"
    var result = codestring.apply {
        println("$this, $length")
        "null"
    }
    println("codestring, $result") // 返回codestring本身
}

// 类似let返回对象本身
fun demoalse() {
    var codestring: String
    codestring = "hello alse"
    val result = codestring.let {
        println("$it, $it.length")
        "end code"
    }
    println(result) // 返回codestring本身
}

fun main(args: Array<String>) {
    println("Hello Kotlin")
    demorun()
}
