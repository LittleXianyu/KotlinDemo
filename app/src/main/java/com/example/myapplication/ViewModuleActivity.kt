package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.FragmentActivity

class ViewModuleActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_module_activity_layout)

//        Log.d("xianyu","android.os.Process.is64Bit"+android.os.Process.is64Bit())
    }
}
