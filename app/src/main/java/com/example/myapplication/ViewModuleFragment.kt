package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myapplication.databinding.ViewModuleLayoutBinding
import com.example.myapplication.javabean.User
import com.example.myapplication.viewmodule.ViewModuleForCoroutine

class ViewModuleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**
         * 默认情况下，类名称基于布局文件的名称，它会转换为 Pascal 大小写形式并在末尾添加 Binding 后缀。
         * 以上布局文件名为 activity_main.xml，因此生成的对应类为 ActivityMainBinding
         */
        val binding: ViewModuleLayoutBinding = ViewModuleLayoutBinding.inflate(inflater, container, false)
        val model: ViewModuleForCoroutine by viewModels()
//        binding.user = User("xianyu","12")
        model.getUsers().observe(
            this,
            Observer<User> { users ->
                // update UI
                Log.d("xianyu", "update ui")
                binding.user = users
            }
        )

        return binding.root
    }
}
