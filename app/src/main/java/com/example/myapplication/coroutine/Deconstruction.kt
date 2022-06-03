package com.example.myapplication.coroutine

class Deconstruction(
    val data1: String = "data1",
    val data2: String = "data2"
) {
    // 操作符重载
    operator fun component1() = data1
    operator fun component2(): String {
        return data2
    }
}

// 因为数据类自动声明 componentN() 函数
data class DataClass(val d1: String = "d1", val d2: String = "d2")

fun main(args: Array<String>) {
    println("Hello Kotlin")
    val deconstruction = Deconstruction()
    val (data1, data2) = deconstruction
    println("data1: $data1, data2: $data2")

    val dataClass = DataClass()
    val (_, d2) = dataClass
    println("d2: $d2")

    // 解构可用于循环Map集合
    var map: Map<Int, String> = mapOf<Int, String>(1 to "111", 2 to "222")
    for ((k, v) in map) {
        println("$k ---- $v")
    }
}
