package com.sdl.primer.worker.blur

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.FileNotFoundException
import java.lang.IllegalArgumentException
import java.lang.RuntimeException

/**
 * create by songdongliang on 2019/3/16
 */
class BlurWorker(context: Context, workerParameters: WorkerParameters)
    : Worker(context, workerParameters) {

    private val TAG = BlurWorker::class.java.simpleName

    override fun doWork(): Result {

        WorkerUtils.makeStatusNotification("Blurring image", applicationContext)
        WorkerUtils.sleep()

        val resourceUri = inputData.getString(Constants.KEY_IMAGE_URI)
        try {
            if (TextUtils.isEmpty(resourceUri)) {
                Log.e(TAG, "Invalid input uri")
                throw IllegalArgumentException("Invalid input uri")
            }

            val resolver = applicationContext.contentResolver

            // create a bitmap
            val bitmap = BitmapFactory.decodeStream(
                    resolver.openInputStream(Uri.parse(resourceUri)))

            // Blur the bitmap
            val output = WorkerUtils.blurBitmap(bitmap, applicationContext)

            // Write bitmap to a temp file
            val outputUri = WorkerUtils.writeBitmapToFile(applicationContext, output)

            // Return the output for the temp file
            val outputData = Data.Builder()
                    .putString(Constants.KEY_IMAGE_URI, outputUri.toString())
                    .build()

            // If there were no errors, return SUCCESS
            return Result.success(outputData)
        } catch (fileNotFoundException: FileNotFoundException) {
            Log.e(TAG, "Failed to decode input stream", fileNotFoundException)
            throw RuntimeException("Failed to decode input stream", fileNotFoundException)
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error applying blur", throwable)
            return Result.failure()
        }
    }

}