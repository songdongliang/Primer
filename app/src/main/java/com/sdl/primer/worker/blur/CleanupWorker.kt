package com.sdl.primer.worker.blur

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File

/**
 * create by songdongliang on 2019/3/16
 */
class CleanupWorker(context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {

    private val TAG = CleanupWorker::class.java.simpleName

    override fun doWork(): Result {
        // Makes a notification when the work starts and slows down the work so that it's easier to
        // see each WorkRequest start, even on emulated devices
        WorkerUtils.makeStatusNotification("Cleaning up old temporary files", applicationContext)
        WorkerUtils.sleep()

        try {
            val outputDirectory = File(applicationContext.filesDir, Constants.OUTPUT_PATH)
            if (outputDirectory.exists()) {
                val entries = outputDirectory.listFiles()
                if (entries != null && entries.isNotEmpty()) {
                    for (file in entries) {
                        val name = file.name
                        if (!TextUtils.isEmpty(name) && name.endsWith(".png")) {
                            val deleted = file.delete()
                            Log.i(TAG, String.format("Deleted %s - %s", name, deleted))
                        }
                    }
                }
            }
            return Result.success()
        } catch (exception: Exception) {
            Log.e(TAG, "Error cleaning up", exception)
            return Result.failure()
        }
    }
}