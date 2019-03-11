package com.sdl.primer.worker

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.*
import com.sdl.primer.R
import kotlinx.android.synthetic.main.activity_worker.*
import java.text.SimpleDateFormat
import java.util.*

class WorkerActivity : AppCompatActivity() {

    private val dateFormat =  SimpleDateFormat("hh:mm:ss", Locale.getDefault())

    private val data = Data.Builder()
            .putString("time", dateFormat.format(Date()))
            .build()

    var outputData: Data? = null

    private val mWorkManager by lazy {
        WorkManager.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker)

        val request = OneTimeWorkRequestBuilder<MainWorker>()
                .setInputData(data)
                .build()

        mWorkManager.enqueue(request)
        mWorkManager.getWorkInfoByIdLiveData(request.id)
                .observe(this, android.arch.lifecycle.Observer { workInfo ->
                    workInfo?.apply {
                        if (state.isFinished) {
                            mTextWork.text = outputData.getString("name").toString()
                        }
                    }
                })
        mTextWork.text = "hello word"
    }

    inner class MainWorker(context: Context, workerParams: WorkerParameters)
        : Worker(context, workerParams) {

        override fun doWork(): Result {
            Log.d("WorkManager", inputData.getString("time"))
            Thread.sleep(2000)
            outputData = Data.Builder()
                    .putString("name", "SouthernBox")
                    .build()
            return Result.success()
        }

    }
}
