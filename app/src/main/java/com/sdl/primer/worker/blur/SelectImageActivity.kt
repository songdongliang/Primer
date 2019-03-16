package com.sdl.primer.worker.blur

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.sdl.primer.R
import kotlinx.android.synthetic.main.activity_select_image.*

class SelectImageActivity : AppCompatActivity() {

    private val sPermissions = arrayListOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private var mPermissionRequestCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_image)

        if (savedInstanceState != null) {
            mPermissionRequestCount =
                    savedInstanceState.getInt(KEY_PERMISSIONS_REQUEST_COUNT, 0)
        }

        // Make sure the app has correct permissions to run
        requestPermissionsIfNecessary()

        // Create request to get image from filesystem when button clicked
        selectImage.setOnClickListener {
            val chooseIntent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(chooseIntent, REQUEST_CODE_IMAGE)
        }
    }

    /**
     * Save the permission request count on a rotate
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(KEY_PERMISSIONS_REQUEST_COUNT, mPermissionRequestCount)
    }

    /**
     * Request permissions twice - if the user denies twice then show a toast about how to update
     * the permission for storage. Also disable the button if we don't have access to pictures on
     * the device.
     */
    private fun requestPermissionsIfNecessary() {
        if (!checkAllPermissions()) {
            if (mPermissionRequestCount < MAX_NUMBER_REQUEST_PERMISSIONS) {
                mPermissionRequestCount += 1
                ActivityCompat.requestPermissions(
                        this,
                        sPermissions.toArray(arrayOf()),
                        REQUEST_CODE_PERMISSIONS)
            } else {
               Toast.makeText(this, R.string.set_permissions_in_settings,
                       Toast.LENGTH_LONG).show()
               selectImage.isEnabled = false
            }
        }
    }

    private fun checkAllPermissions(): Boolean {
        var hasPermissions = true
        for (permission in sPermissions) {
            hasPermissions = hasPermissions.and(
                    ContextCompat.checkSelfPermission(
                            this, permission) == PackageManager.PERMISSION_GRANTED)
        }
        return hasPermissions
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            requestPermissionsIfNecessary() // no-op if permissions are granted already
        }
    }

    /**
     * Image Selection
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE -> handleImageRequestResult(data)
            }
        }
    }

    private fun handleImageRequestResult(data: Intent?) {
        var imageUri: Uri? = null
        if (data?.clipData != null) {
            imageUri = data.clipData!!.getItemAt(0).uri
        } else if (data?.data != null) {
            imageUri = data.data
        }
        if (imageUri == null) {
            Log.e(TAG, "Invalid input image Uri.")
            return
        }

        val filterIntent = Intent(this, BlurActivity::class.java)
        filterIntent.putExtra(Constants.KEY_IMAGE_URI, imageUri.toString())
        startActivity(filterIntent)
    }

    companion object {

        const val TAG = "SelectImageActivity"

        const val REQUEST_CODE_IMAGE = 100

        const val REQUEST_CODE_PERMISSIONS = 101

        const val KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT"

        const val MAX_NUMBER_REQUEST_PERMISSIONS = 2

    }
}
