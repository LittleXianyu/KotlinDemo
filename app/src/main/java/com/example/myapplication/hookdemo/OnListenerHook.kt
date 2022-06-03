package com.example.myapplication.hookdemo

import android.view.View
import android.widget.Button
import java.lang.reflect.Field
import java.lang.reflect.Proxy

object OnListenerHook {
    /**
     * public void setOnClickListener(@Nullable OnClickListener l) {
     if (!isClickable()) {
     setClickable(true);
     }
     getListenerInfo().mOnClickListener = l;
     }
     先反射拿到getListenerInfo（）=>mListenerInfo
     然后直接设置        public OnClickListener mOnClickListener;
     */
    fun doHook(btn: Button) {
        var viewClass = Class.forName("android.view.View")
        var viewClass1 = View::class.java
        // 第一步反射拿到mListenerInfo对象

        val field: Field = viewClass.getDeclaredField("mListenerInfo")
        field.isAccessible = true
        val mListenerInfo = field.get(btn)
// 通过方法反射
//        val getListenerInfoMethod = viewClass.superclass!!.superclass!!.getDeclaredMethod("getListenerInfo")
//        getListenerInfoMethod.setAccessible(true)
//        val mListenerInfo = getListenerInfoMethod.invoke(view)
        // 第二步反射拿到mListenerInfo中的mOnClickListener

        val fieldListener: Field = mListenerInfo.javaClass.getDeclaredField("mOnClickListener")
        fieldListener.isAccessible = true
        val oldListener = fieldListener.get(mListenerInfo)

        // 生成动态代理对象
        val onListenerHandler = OnListenerHandler(oldListener)
        val newListenerProxy = Proxy.newProxyInstance(
            oldListener.javaClass.getClassLoader(),
            oldListener.javaClass.getInterfaces(),
            onListenerHandler
        )

        // 反射方式：动态代理对象替代原来的对象
        fieldListener.set(mListenerInfo, newListenerProxy)
    }
}
