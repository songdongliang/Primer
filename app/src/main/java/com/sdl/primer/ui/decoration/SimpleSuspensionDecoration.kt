package com.sdl.primer.ui.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.TypedValue
import android.view.View

/**
 * create by songdongliang on 2019/2/26
 */
class SimpleSuspensionDecoration(mContext: Context): RecyclerView.ItemDecoration() {

    private var mTitleHeight: Float = 0f

    private var mTitleTextSize: Float = 0f

    private val textRect: Rect by lazy {
        Rect()
    }

    private val mTextPaint: Paint by lazy {
        Paint()
    }
    private val mGrayPaint: Paint by lazy {
        Paint()
    }

    private val mPaint: Paint by lazy {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.RED
        paint
    }

    var callback: TitleDecorationCallback? = null

    init {
        mTitleHeight = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                30f,
                mContext.resources.displayMetrics)
        mTitleTextSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                16f,
                mContext.resources.displayMetrics)

        mTextPaint.textSize = mTitleTextSize
        mTextPaint.isAntiAlias = true
        mTextPaint.color = Color.WHITE

        mGrayPaint.isAntiAlias = true
        mGrayPaint.color = Color.DKGRAY
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = (view.layoutParams as RecyclerView.LayoutParams)
                .viewLayoutPosition
        if (position == 0 || isFirst(position)) {
            outRect.top = mTitleHeight.toInt()
        } else {
            outRect.top = 1
        }
    }

    /**
     * 这个用于给getItemOffsets隔开的距离绘制图形
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val params = view.layoutParams as RecyclerView.LayoutParams
            val position = params.viewLayoutPosition

            if (position == 0 || isFirst(position)) {
                val top = view.top - mTitleHeight
                val bottom = view.top
                c.drawRect(left.toFloat(), top, right.toFloat(), bottom.toFloat(), mPaint)

                val groupName = callback?.getGroupName(position) ?: "无组名"
                mTextPaint.getTextBounds(groupName, 0, groupName.length, textRect)
                val x = view.paddingLeft
                val y = top + (mTitleHeight - textRect.height()) / 2 + textRect.height()
                c.drawText(groupName, x.toFloat(), y, mTextPaint)
            } else {
                val top = view.top - 1
                val bottom = view.top
                c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mGrayPaint)
            }
        }
    }

    /**
     * 在item被绘制之后调用，将指定的内容绘制到item view内容之上
     * 这个方法可以将内容覆盖在item上，可用于制作悬停效果，角标等（这里只实现悬停效果）
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val position = ((parent.layoutManager) as LinearLayoutManager)
                .findFirstVisibleItemPosition()
        if (position <= -1 || position >= parent.adapter?.itemCount ?: 0 - 1) {
            // 越界检查
            return
        }

        val firstVisibleView = parent
                .findViewHolderForAdapterPosition(position)?.itemView
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        var top = parent.paddingTop
        var bottom = top + mTitleHeight.toInt()

        // 如果当前屏幕上显示的item是下一组的第一个, 并且第一个被title覆盖, 则开始移动上个title
        // 原理就是不断改变title所在矩形的top和bottom的值
        Log.i("decoration", "firstViewTop---> ${firstVisibleView?.top}")
        if (isFirst(position + 1) && firstVisibleView?.bottom ?: 0 < mTitleHeight) {
//            if (mTitleHeight <= firstVisibleView?.height ?: 0) {
//                val d = (firstVisibleView?.height ?: 0) - mTitleHeight.toInt()
//                top = (firstVisibleView?.top ?: 0) + d
//                Log.i("decoration", "top---> $top")
//            } else {
//                val d = mTitleHeight - (firstVisibleView?.height ?: 0)
//                top = (firstVisibleView?.top ?: 0) - d.toInt() ;// 这里有bug,mTitleHeight过高时 滑动有问题
//            }
            top = (firstVisibleView?.bottom ?: 0) - mTitleHeight.toInt()
            bottom = firstVisibleView?.bottom ?: 0
        }
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)

        val groupName = callback?.getGroupName(position) ?: "无组名"
        mTextPaint.getTextBounds(groupName, 0, groupName.length, textRect)
        val x = left + (firstVisibleView?.paddingLeft ?: 0)
        val y = top + (mTitleHeight - textRect.height()) / 2 + textRect.height()
        Log.i("decoration", "yyyy---> $y")
        c.drawText(groupName, x.toFloat(), y, mTextPaint)
    }

    private fun isFirst(position: Int): Boolean {
        return if (position == 0) {
            true
        } else {
            val preGroupId = callback?.getGroupId(position - 1)
            val groupId = callback?.getGroupId(position)
            preGroupId != groupId
        }
    }

    interface TitleDecorationCallback {

        fun getGroupId(position: Int): Long

        fun getGroupName(position: Int): String
    }
}