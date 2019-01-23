package com.sdl.primer.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.sdl.primer.R;

/**
 * Created by 11870 on 2017/1/6.
 */

public class ReverseColorProgress extends View {

    /**
     * 第一圈的颜色
     */
    private int mFirstColor ;
    /**
     * 第二圈的颜色
     */
    private int mSecondColor ;
    /**
     * 速度
     */
    private int mSpeed ;
    /**
     * 圆环的宽度
     */
    private int mCircleWidth ;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 判断是否要交互颜色
     */
    private boolean isNext ;
    /**
     * 当前进度
     */
    private int mProgress ;

    public ReverseColorProgress(Context context) {
        super(context);
    }

    public ReverseColorProgress(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ReverseColorProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.ReverseColorProgress,defStyleAttr,0);
        int n = a.getIndexCount();
        for(int i = 0;i < n;i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.ReverseColorProgress_circleWidth:
                    mCircleWidth = a.getDimensionPixelOffset(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,20,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.ReverseColorProgress_firstColor:
                    mFirstColor = a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.ReverseColorProgress_secondColor:
                    mSecondColor = a.getColor(attr,Color.RED);
                    break;
                case R.styleable.ReverseColorProgress_speed:
                    mSpeed = a.getInteger(attr,20);
                    break;
            }
        }
        a.recycle();

        mPaint = new Paint();
        new Thread(){
            @Override
            public void run() {
                while (true){
                    mProgress++ ;
                    if(mProgress == 360){
                        isNext = !isNext ;
                        mProgress = 0 ;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 2;
        int radius = center-mCircleWidth/2;
        mPaint.setStrokeWidth(mCircleWidth);
        //设置空心
        mPaint.setStyle(Paint.Style.STROKE);
        //消除锯齿
        mPaint.setAntiAlias(true);
        RectF oval = new RectF(center-radius,center-radius,center+radius,center+radius);
        if(!isNext){
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(center,center,radius,mPaint);
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval,-90,mProgress,false,mPaint);
        } else {
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(center,center,radius,mPaint);
            mPaint.setColor(mFirstColor);
            canvas.drawArc(oval,-90,mProgress,false,mPaint);
        }
    }
}
