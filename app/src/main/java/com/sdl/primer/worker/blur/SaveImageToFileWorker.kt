package com.sdl.primer.worker.blur

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * create by songdongliang on 2019/3/16
 */
class SaveImageToFileWorker(context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {

    private val TAG = SaveImageToFileWorker::class.java.simpleName

    private val TITLE = "Blurred Image"

    private val DATE_FORMATTER = SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z", Locale.getDefault())

    override fun doWork(): Result {

        WorkerUtils.makeStatusNotification("Saving image", applicationContext)
        WorkerUtils.sleep()

        val resolver = applicationContext.contentResolver
        try {
            val resourceUri = inputData.getString(Constants.KEY_IMAGE_URI)
            val bitmap = BitmapFactory.decodeStream(
                    resolver.openInputStream(Uri.parse(resourceUri)))
            val imageUrl = MediaStore.Images.Media.insertImage(
                    resolver, bitmap, TITLE, DATE_FORMATTER.format(Date()))
            if (TextUtils.isEmpty(imageUrl)) {
                Log.e(TAG, "Writing to MediaStore failed")
                return Result.failure()
            }
            val outputData = Data.Builder()
                    .putString(Constants.KEY_IMAGE_URI, imageUrl)
                    .build()
            return Result.success(outputData)
        } catch (exception: Exception) {
            Log.e(TAG, "Unable to save image to Gallery", exception)
            return Result.failure()
        }
    }
}