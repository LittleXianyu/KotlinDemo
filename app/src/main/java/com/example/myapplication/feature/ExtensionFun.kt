package com.example.myapplication.feature

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

// https://www.kotlincn.net/docs/reference/extensions.html#extension-functions
class ExtensionFun {
    val data = "str"
    fun call1(): String {
        return data
    }
}
fun ExtensionFun.fun2(): String {
    return "fun2"
}

fun main(args: Array<String>) {
    val ext1 = ExtensionFun()
//    println(ext1.fun2())
    for (progress in 1..100)
        if (progress % 2 == (Math.random() * 2).toInt() % 2 && progress < 95) {
//            println(progress)
        }
    val s1 = Date().toString() // Tue Jun 30 11:01:03 CST 2020
    val f1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
    val s2 = f1.format(Date()) // 2020-06-30 11:00:26.401
    println(s2)

}
