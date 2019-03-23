package com.sdl.primer.motion

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.constraint.motion.MotionLayout
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.sdl.primer.R
import kotlinx.android.synthetic.main.motion_01_basic.*

/**
 * create by songdongliang on 2019/3/22
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DemoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = intent.getIntExtra("layout_file_id", R.layout.motion_01_basic)
        setContentView(layout)

//        if (layout == R.layout.motion_11_coordinatorlayout) {
//            val icon = findViewById<ImageView>(R.id.icon)
//            icon?.clipToOutline = true
//        }

        val debugMode = if (intent.getBooleanExtra("showPaths", false)) {
            MotionLayout.DEBUG_SHOW_PATH
        } else {
            MotionLayout.DEBUG_SHOW_NONE
        }

        motionLayout.setDebugMode(debugMode)
    }
}