package com.example.myapplication.hookdemo

import android.util.Log
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class OnListenerHandler(val listener: Any) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?) {
        Log.d("xianyu", "1:  " + method?.name)
        if (args != null) {
            for (obj in args) {
                Log.d("xianyu", "1:  " + obj)
            }
        }
        /*
        反射api中Method的invoke方法接收可变长参数，
        在java中允许数组赋值给可变长参数Object... args，
        Kotlin中，数组是array，可变长参数类型是vararg，类型不一致，
        Kotlin中数组转为可变长参数，是通过前面加*
            fun a(){
        val array : Array<String> = arrayOf("hello","world")
        //转换
        b(*array)
    }

    fun b(vararg strings: String){
        //可变长参数函数
    }

         */
        method?.invoke(listener, *args!!)
    }
}
