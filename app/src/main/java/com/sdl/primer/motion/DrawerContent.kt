package com.sdl.primer.motion

import android.content.Context
import android.support.constraint.motion.MotionLayout
import android.support.v4.widget.DrawerLayout
import android.util.AttributeSet
import android.view.View

/**
 * create by songdongliang on 2019/3/25
 */
class DrawerContent @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : MotionLayout(context, attrs, defStyleAttr), DrawerLayout.DrawerListener{

    override fun onDrawerStateChanged(p0: Int) {

    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        progress = slideOffset
    }

    override fun onDrawerClosed(p0: View) {

    }

    override fun onDrawerOpened(p0: View) {

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        (parent as? DrawerLayout)?.addDrawerListener(this)
    }
}