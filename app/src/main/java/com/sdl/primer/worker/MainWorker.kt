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
            val outputData = Data.Builder()
                    .putInt("name", inputData.getInt("name", 0))
                    .build()
            Result.success(outputData)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

}