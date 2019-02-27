package com.sdl.primer.ui.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * create by songdongliang on 2019/2/26
 */
class SimpleSuspensionDecoration(mContext: Context): RecyclerView.ItemDecoration() {

    private val mPaint: Paint by lazy {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.RED
        paint
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.top = 1
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val top = view.top - 1
            val bottom = view.top

            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        mPaint.color = Color.BLUE
        c.drawRect(0f, 0f, parent.right.toFloat(), 60f, mPaint)
    }

}