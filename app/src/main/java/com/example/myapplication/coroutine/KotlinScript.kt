package com.example.myapplication.coroutine

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.system.measureTimeMillis

class KotlinScript() : CoroutineScope {
    lateinit var latedata: String
    override val coroutineContext =
        Dispatchers.IO + CoroutineName("AsyncStorageScope") + SupervisorJob()
    fun print() {
        if (!::latedata.isInitialized) {
            println("! isInitialized!")
        } else {
            println("isInitialized!")
        }
        launch {
            print(Thread.currentThread().name)
        }
    }
}

fun demo1() = runBlocking { // 开始执行主协程，主协程
    launch { // 在后台启动一个新的协程并继续
        delay(1000L)
        println("World!")
    }
    println("Hello,") // 主协程在这里会立即执行
    delay(2000L) // 延迟 2 秒来保证 JVM 存活
}
suspend fun demo3() = runBlocking {
    val job = launch { // 启动一个新协程并保持对这个作业的引用
        delay(1000L)
        println("xianyu： " + "before delay!: " + Thread.currentThread().name)
    }
    println("xianyu： " + "after delay !")
    job.join() // 等待直到子协程执行结束
}

fun demoFor3() = runBlocking {
    launch(EmptyCoroutineContext, CoroutineStart.DEFAULT) { // 启动一个新协程并保持对这个作业的引用
        demo3()
        println("xianyu： " + "demo3 end !" + Thread.currentThread().name)
    }
}

fun demoFlow() = runBlocking {
    flow {
        for (i in 1..10)
            emit(i)
    }.collect {
        println("接收到的消息$it")
    }
}

fun demoChannel() = runBlocking {
    val channel = Channel<Int>()
    launch {
        // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
        for (x in 1..5) {
            delay(1000)
            channel.send(x * x)
        }
    }
    // here we print five received integers:
    repeat(5) {
        delay(3000)
        println(channel.receive())
    }
}

/**
 * runBlocking：顶层函数，它和 coroutineScope 不一样，它会阻塞当前线程来等待，所以这个方法在业务中并不适用 。
GlobalScope：全局协程作用域，可以在整个应用的声明周期中操作，且不能取消，所以仍不适用于业务开发。
 */

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // 假设我们在这里做了一些有用的事
    return 13
}
suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // 假设我们在这里也做了一些有用的事
    return 29
}
suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}
fun demo7() = runBlocking {
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")
}

/**
 *  一个线程预加载，一个线程预加载+执行
 *  用一个变量判断当前状态是加载中还未加载
 */

/**
 *  协程的线程切换
 *  launch(newSingleThreadContext("MyOwnThread")) { // 将使它获得一个新的线程
println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
}
 */
class PreLoad {
    // loadState: 0正在加载 1加载结束
    @Volatile var loadState: Int = 0
    @Volatile var canShow: Int = 0

    suspend fun preload() {
        loadState = 0
        println("loading")

        delay(2000)
        loadState = 1
        println("loaded")
        if (canShow == 1) {
            realShow()
        }
    }

    fun show() {
        canShow = 1
        if (loadState == 1) {
            println("call show")
            realShow()
        }
    }
    fun realShow() {
        println("call realShow")
    }
}
fun demo8() = runBlocking {
    println("runBlocking: I'm working in thread ${Thread.currentThread().name}")
    launch(newSingleThreadContext("MyOwnThread")) { // 将使它获得一个新的线程
        delay(100)
        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
    }
    println("runBlocking: I'm working in thread ${Thread.currentThread().name}, end")
}

/**
 * 场景分析：
 * 1.预加载没结束，show方法开始调用，a.show设置标志位，load之后判断标志位继续执行show。b.show线程不阻塞，等待消息唤醒来执行
 * 2.预加载结束，show方法开始调用，好处理：直接顺序执行show
 */
fun demo9() = runBlocking {
    val p = PreLoad()
    launch {
        System.out.println("111")

        p.preload()
    }
    System.out.println("222")

    launch(newSingleThreadContext("MyOwnThread")) { // 将使它获得一个新的线程
        delay(1000)
        p.show()
    }
}

fun main(args: Array<String>) {
    System.out.println("sss")
    demo9()
}
