package com.sdl.primer.motion

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.constraint.ConstraintSet
import android.support.transition.AutoTransition
import android.support.transition.TransitionManager
import android.view.View
import com.sdl.primer.R
import kotlinx.android.synthetic.main.activity_constraint_set_example.*

class ConstraintSetExampleActivity : AppCompatActivity() {

    private val SHOW_BIG_IMAGE = "showBigImage"

    private var mShowBigImage = false

    private val mConstraintSetNormal by lazy {
        ConstraintSet()
    }

    private val mConstraintSetBig by lazy {
        ConstraintSet()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_set_example)

        // Note that this can also be achieved by calling
        // `mConstraintSetNormal.load(this, R.layout.constraintset_example_main);`
        // Since we already have an inflated ConstraintLayout in `mRootLayout`, clone() is
        // faster and considered the best practice.
        mConstraintSetNormal.clone(mRootLayout)
        // Load the constraints from the layout where ImageView is enlarged.
        mConstraintSetBig.load(this, R.layout.constraint_set_example_big)

        if (savedInstanceState != null) {
            val previous = savedInstanceState.getBoolean(SHOW_BIG_IMAGE)
            if (previous != mShowBigImage) {
                mShowBigImage = previous
                applyConfig()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putBoolean(SHOW_BIG_IMAGE, mShowBigImage)
    }

    private fun applyConfig() {
        if (mShowBigImage) {
            mConstraintSetBig.applyTo(mRootLayout)
        } else {
            mConstraintSetNormal.applyTo(mRootLayout)
        }
    }

    fun toggleMode(view: View) {
        val transition = AutoTransition()
        transition.duration = 1000
        TransitionManager.beginDelayedTransition(mRootLayout)
        mShowBigImage = !mShowBigImage
        applyConfig()
    }

}
