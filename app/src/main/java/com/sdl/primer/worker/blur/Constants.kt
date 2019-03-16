package com.sdl.primer.worker.blur

/**
 * create by songdongliang on 2019/3/16
 */
class Constants {

    companion object {
        // Name of Notification Channel for verbose notifications of background work
        val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence = "Verbose WorkManager Notifications"
        var VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION = "Shows notifications whenever work starts"
        val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
        val CHANNEL_ID = "VERBOSE_NOTIFICATION"
        val NOTIFICATION_ID = 1

        // The name of the image manipulation work
        internal val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"

        // Other keys
        val OUTPUT_PATH = "blur_filter_outputs"
        val KEY_IMAGE_URI = "KEY_IMAGE_URI"
        internal val TAG_OUTPUT = "OUTPUT"

        val DELAY_TIME_MILLIS: Long = 3000
    }
}