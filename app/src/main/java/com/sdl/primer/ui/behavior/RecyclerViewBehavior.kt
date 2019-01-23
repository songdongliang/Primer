package com.sdl.primer.ui.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 * create by songdongliang on 2018/10/9
 */
class RecyclerViewBehavior: CoordinatorLayout.Behavior<RecyclerView> {

    constructor()

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    override fun layoutDependsOn(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        return dependency is ImageView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        var y = dependency.height + dependency.translationY
        if (y < 0) {
            y = 0f
        }
        child.y = y
        return true
    }
}