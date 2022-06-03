package com.example.myapplication

import android.app.Application
import android.content.Context
import com.example.myapplication.patch.HotFixManager

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        HotFixManager.printClassLoaderInfo(this)
        HotFixManager.printDexPathListInfo(this)
        HotFixManager.downloadHotFixDexFile(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        HotFixManager.preformHotFix(this)
    }
}
