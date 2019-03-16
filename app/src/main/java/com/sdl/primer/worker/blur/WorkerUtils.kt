package com.sdl.primer.worker.blur

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.support.annotation.WorkerThread
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.sdl.primer.R
import java.io.File
import java.io.FileOutputStream
import java.util.*

/**
 * create by songdongliang on 2019/3/16
 */
object WorkerUtils {

    private const val TAG = "WorkUtils"

    /**
     * Create a Notification that is shown as a heads-up notification if possible.
     *
     * For this codelab, this is used to show a notification so that you know when different steps
     * of the background work chain are starting
     */
    fun makeStatusNotification(message: String, context: Context) {

        // Make a channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name = Constants.VERBOSE_NOTIFICATION_CHANNEL_NAME
            val description = Constants.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(Constants.CHANNEL_ID, name, importance)
            channel.description = description
            // Add the channel
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
            if (notificationManager != null) {
                (notificationManager as NotificationManager).createNotificationChannel(channel)
            }
        }

        // Create the notification
        val builder = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(Constants.NOTIFICATION_TITLE)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(LongArray(0))

        // Show the notification
        NotificationManagerCompat.from(context).notify(Constants.NOTIFICATION_ID, builder.build())
    }

    /**
     * Method for sleeping for a fixed about of time to emulate slower work
     */
    fun sleep() {
        try {
            Thread.sleep(Constants.DELAY_TIME_MILLIS, 0)
        } catch (e: InterruptedException) {
            Log.d(TAG, e.message)
        }
    }

    /**
     * Blurs the given Bitmap image
     */
    @WorkerThread
    fun blurBitmap(bitmap: Bitmap, applicationContext: Context): Bitmap {
        var rsContext: RenderScript? = null

        try {
            // Create the output bitmap
            val output = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
            // Blur the image
            rsContext = RenderScript.create(applicationContext, RenderScript.ContextType.DEBUG)
            val inAlloc = Allocation.createFromBitmap(rsContext, bitmap)
            val outAlloc = Allocation.createTyped(rsContext, inAlloc.type)
            val theIntrinsic = ScriptIntrinsicBlur.create(rsContext, Element.U8_4(rsContext))
            theIntrinsic.setRadius(10f)
            theIntrinsic.setInput(inAlloc)
            theIntrinsic.forEach(outAlloc)
            outAlloc.copyTo(output)
            return output
        } finally {
            rsContext?.finish()
        }
    }

    /**
     * Writes bitmap to a temporary file and returns the Uri for the file
     */
    fun writeBitmapToFile(applicationContext: Context, bitmap: Bitmap): Uri {
        val name: String = String.format("blur-filter-output-%s.png", UUID.randomUUID().toString())
        val outputDir = File(applicationContext.filesDir, Constants.OUTPUT_PATH)
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        val outputFile = File(outputDir, name)
        FileOutputStream(outputFile).use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, it)
        }
        return Uri.fromFile(outputFile)
    }
}