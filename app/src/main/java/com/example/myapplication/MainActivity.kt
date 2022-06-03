package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.hookdemo.EvilInstrumentation
import com.example.myapplication.hookdemo.OnListenerHook
import com.example.myapplication.patch.PatchTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val mainActivityScope = CoroutineScope(SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PatchTest.out()
        EvilInstrumentation.replaceInstrumentation(this)
        val button = findViewById<Button>(R.id.to_1)

        button.setOnClickListener {
//            val intent = Intent()
//            intent.setClass(this, ViewModuleActivity::class.java)
//            startActivity(intent)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.data = Uri.parse("http://www.baidu.com")
            startActivity(intent)
        }
        // hook点击事件
        OnListenerHook.doHook(button)

        val latestNews: Flow<String> = flow {
            emit("hh")
        }
        mainActivityScope.launch {
            latestNews.map { value -> value + "hh" }.collect { value -> System.out.println(value) }
        }
//        DownloadNotifyUtil.createNotificationChannel(this)
//        Log.d("xianyu", "notification permission:" + DownloadNotifyUtil.isNotificationEnabled(this))
//        val builder = DownloadNotifyUtil.notifyDownloadStart(this@MainActivity)
//        var oldProgress = 0
//        DownloadUtil.download(
//            this,
//            "https://shop-download.oss-cn-beijing.aliyuncs.com/release-builds/18.1.0/shihuimiao-v18.1.0_S000.apk ", "test1",
//            object : DownloadUtil.OnDownloadListener {
//                override fun onDownloadSuccess(fileName: String) {
//                    Log.d("xianyu", "onDownloadSuccess")
//                    DownloadNotifyUtil.notifyDownloadSuccess(this@MainActivity, fileName)
//                }
//                override fun onDownloading(progress: Int) {
//                    if (oldProgress != progress) {
//                        oldProgress = progress
//                        DownloadNotifyUtil.notifyDownloadProgress(this@MainActivity, progress)
//                        Log.d("xianyu", "progress" + progress)
//                    }
//                }
//                override fun onDownloadFailed() {
//                    DownloadNotifyUtil.notifyDownloadFailed(this@MainActivity)
//                    Log.d("xianyu", "onDownloadFailed")
//                }
//            }
//        )
    }
}
