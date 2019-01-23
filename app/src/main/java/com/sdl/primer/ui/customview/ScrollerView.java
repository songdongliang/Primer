package com.sdl.primer.ui.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.sdl.primer.R;

/**
 * Created by 11870 on 2017/2/16.
 */

public class ScrollerView extends View {

    private Scroller mScroller ;

    private Paint mPaint ;

    public ScrollerView(Context context) {
        this(context,null);
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int paddingLeft = getPaddingLeft() ;
        int paddingRight = getPaddingRight() ;
        int paddingTop = getPaddingTop() ;
        int paddingBottom = getPaddingBottom() ;
        int width = getWidth() - paddingLeft - paddingRight ;
        int height = getHeight() - paddingTop - paddingBottom ;
        int radius = Math.min(width,height) / 2;
        canvas.drawCircle(paddingLeft+width/2,paddingTop + height/2,radius,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("sdl","touch");
        scrollBy(5,5);
        mScroller.startScroll(getScrollX(),getScrollY(),20,20);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(200,200);
        } else if(widthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(200,heightSize);
        } else if(heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize,200);
        } else {
            setMeasuredDimension(widthSize,heightSize);
        }

    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
}
