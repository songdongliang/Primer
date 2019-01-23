package com.sdl.primer.ui.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log


/**
 * create by songdongliang on 2018/10/9
 */
class SampleHeaderBehavior: CoordinatorLayout.Behavior<ImageView> {

    /**
     * 界面整体向上滑动，到达列表可滑动的临界点
     */
    private var upReach: Boolean = false
    /**
     * 列表向上滑动后，再向下滑动，达到界面整体可滑动的临界点
     */
    private var downReach: Boolean = false
    /**
     * 列表上一个全部可见的item位置
     */
    private var lastPosition: Int = -1

    constructor()

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: ImageView, ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downReach = false
                upReach = false
            }
        }
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    /**
     * 表示是否监听此次RecylerView的滑动事件，这里我们只监听其垂直方向的滑动事件
     */
    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: ImageView, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    /**
     * 处理监听到的滑动事件，实现整体滑动和列表单独滑动（header是否完全隐藏是滑动的临界点）
     */
    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: ImageView, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        if (target is RecyclerView) {
            // 列表第一个全部可见的Item的位置
            val pos = (target.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
            if (pos == 0 && pos < lastPosition) {
                downReach = true
            }
            Log.i("SampleHeaderBehavior", dy.toString())
            // 整体可以滑动，否则recyclerView消费滑动事件
            if (canScroll(child, dy) && pos == 0) {
                var finalY: Float = child.translationY - dy
                if (finalY < -child.height) {
                    finalY = -child.height.toFloat()
                    upReach = true
                } else if (finalY > 0) {
                    finalY = 0f
                }
                child.translationY = finalY
                // 让CoordinatorLayout消费滑动事件
                consumed[1] = dy

            }
            lastPosition = pos
        }
    }

    private fun canScroll(child: ImageView, dy: Int): Boolean {
        if (dy > 0 && child.translationY == -child.height.toFloat() && !upReach) {
            return false
        }
        if (downReach) {
            return false
        }
        return true
    }

}