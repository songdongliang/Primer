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

//    var outputData: Data? = null

    private val mWorkManager by lazy {
        WorkManager.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker)

        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val request = OneTimeWorkRequestBuilder<MainWorker>()
                .setConstraints(constraints)
                .setInputData(data)
                .addTag(REQUEST_TAG)
                .build()

        var continuation = mWorkManager.beginWith(request)

        for (i in 0 until 6) {
            val builder = OneTimeWorkRequestBuilder<MainWorker>()
            builder.addTag(REQUEST_TAG)
            continuation = continuation.then(builder.build())
        }
        continuation.enqueue()


        mWorkManager.getWorkInfosByTagLiveData(REQUEST_TAG)
                .observe(this, android.arch.lifecycle.Observer { workInfos ->
                    workInfos?.apply {
                        for (workInfo in workInfos) {
                            if (workInfo.state.isFinished) {
                                mTextWork.text = workInfo.outputData.getLong("name", -1).toString()
                            }
                        }
                    }
                })
        mTextWork.text = "hello word"
    }

    companion object {
        const val REQUEST_TAG = "xxx"
    }
}
