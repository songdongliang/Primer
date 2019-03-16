package com.sdl.primer.worker

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MainWorker(context: Context, workerParams: WorkerParameters)
        : Worker(context, workerParams) {




    override fun doWork(): Result { // running a background thread
        return try {
            Thread.sleep(2000)
            Log.i("songdongliang", inputData.getLong("name", 0).toString())
            val outputData = Data.Builder()
                    .putLong("name", System.currentTimeMillis())
                    .build()
            Result.success(outputData)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

}