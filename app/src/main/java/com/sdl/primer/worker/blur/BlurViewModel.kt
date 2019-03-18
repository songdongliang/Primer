package com.sdl.primer.worker.blur

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.net.Uri
import android.text.TextUtils
import androidx.work.*

/**
 * create by songdongliang on 2019/3/18
 */
class BlurViewModel: ViewModel() {

    private val mWorkManager by lazy {
        WorkManager.getInstance()
    }

    var mImageUri: Uri? = null
    var mOutputUri: Uri? = null

    // This transformation makes sure that whenever the current work id changes the WorkInfo
    // the UI is listening to changes
    private val mSaveWorkInfo by lazy {
        mWorkManager.getWorkInfosByTagLiveData(Constants.TAG_OUTPUT)
    }

    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    fun applyBlur(blurLevel: Int) {
        // Add WorkRequest to Cleanup temporary images
        var continuation = mWorkManager.beginUniqueWork(
                Constants.IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(CleanupWorker::class.java))

        // Add WorkRequests to blur the image the number of times requested
        for (i in 0 until blurLevel) {
            val blurBuilder = OneTimeWorkRequest
                    .Builder(BlurWorker::class.java)

            // Input the Uri if this is the first blur operation
            // After the first blur operation the input will be the output of previous
            // blur operations
            if (i == 0) {
                blurBuilder.setInputData(createInputDataForUri())
            }

            continuation = continuation.then(blurBuilder.build())
        }

        // Create charging constraint
        val constraints = Constraints.Builder()
                .setRequiresCharging(true).build()

        val saveWorker = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
                .setConstraints(constraints)
                .addTag(Constants.TAG_OUTPUT)
                .build()

        continuation = continuation.then(saveWorker)

        // Actually start the work
        continuation.enqueue()
    }

    /**
     * Cancel work using the work's unique name
     */
    fun cancelWork() {
        mWorkManager.cancelUniqueWork(Constants.IMAGE_MANIPULATION_WORK_NAME)
    }

    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        mImageUri?.let {
            builder.putString(Constants.KEY_IMAGE_URI, mImageUri.toString())
        }
        return builder.build()
    }

    fun setImageUri(uri: String) {
        mImageUri = uriOrNull(uri)
    }

    fun setOutputUri(outputImageUri: String) {
        mOutputUri = uriOrNull(outputImageUri)
    }

    private fun uriOrNull(uriString: String): Uri? {
        if (!TextUtils.isEmpty(uriString)) {
            return Uri.parse(uriString)
        }
        return null
    }

    fun getOutputWorkInfo(): LiveData<List<WorkInfo>> {
        return mSaveWorkInfo
    }
}