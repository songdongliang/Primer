package com.sdl.primer.worker.blur

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.sdl.primer.R
import kotlinx.android.synthetic.main.activity_blur.*

class BlurActivity : AppCompatActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(BlurViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blur)

        // Image uri should be stored in the ViewModel; put it there then display
        val imageUriExtra = intent.getStringExtra(Constants.KEY_IMAGE_URI)
        mViewModel.setImageUri(imageUriExtra)
        if (mViewModel.mImageUri != null) {
            Glide.with(this).load(mViewModel.mImageUri).into(image_view)
        }

        // Setup blur image file button
        go_button.setOnClickListener {
            mViewModel.applyBlur(getBlurLevel())
        }

        // Setup view output image file button
        see_file_button.setOnClickListener {
            val currentUri = mViewModel.mOutputUri
            currentUri?.let {
                val actionView = Intent(Intent.ACTION_VIEW, currentUri)
                if (actionView.resolveActivity(packageManager) != null) {
                    startActivity(actionView)
                }
            }
        }

        // Hookup the Cancel button
        cancel_button.setOnClickListener {
            mViewModel.cancelWork()
        }

        // Show work status
        mViewModel.getOutputWorkInfo().observe(this, Observer { listOfWorkInfo ->

            // If there are no matching work info, do nothing
            if (listOfWorkInfo == null || listOfWorkInfo.isEmpty()) {
                return@Observer
            }
            // We only care about the one output status.
            // Every continuation has only one worker tagged TAG_OUTPUT
            val workInfo = listOfWorkInfo[0]
            if (!workInfo.state.isFinished) {
                showWorkInProgress()
            } else {
                showWorkFinish()

                val outputImageUri = workInfo.outputData.getString(Constants.KEY_IMAGE_URI)
                if (!TextUtils.isEmpty(outputImageUri)) {
                    mViewModel.setOutputUri(outputImageUri!!)
                    see_file_button.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun showWorkFinish() {
        progress_bar.visibility = View.GONE
        cancel_button.visibility = View.GONE
        see_file_button.visibility = View.VISIBLE
    }

    private fun showWorkInProgress() {
        progress_bar.visibility = View.VISIBLE
        cancel_button.visibility = View.VISIBLE
        go_button.visibility = View.GONE
        see_file_button.visibility = View.GONE
    }

    private fun getBlurLevel(): Int {
        return when (radio_blur_group.checkedRadioButtonId) {
            R.id.radio_blur_lv_1 -> 1
            R.id.radio_blur_lv_2 -> 2
            R.id.radio_blur_lv_3 -> 3
            else -> 1
        }
    }

}
