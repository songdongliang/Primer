package com.sdl.primer.livedata2

import android.view.View
import android.widget.Toast

/**
 * create by songdongliang on 2019/1/4
 */
class MyHandlers {

    fun onClick(view: View) {
        Toast.makeText(view.context, "MyHandlers onClick", Toast.LENGTH_SHORT).show()
    }

    fun onClick3(view: View, str: String) {
        Toast.makeText(view.context, str, Toast.LENGTH_SHORT).show()
    }
}