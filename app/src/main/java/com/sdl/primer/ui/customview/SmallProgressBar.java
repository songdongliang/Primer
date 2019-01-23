package com.sdl.primer.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.sdl.primer.R;

/**
 * Created by 11870 on 2017/1/10.
 * 小圆形进度条
 * 1.先画灰色圆环 2.在画进度条，进度条的长短随进度改变而改变
 */

public class SmallProgressBar extends View {

    private Paint mPaint ;
    /**
     * 圆环的背景颜色
     */
    private int mainColor ;
    /**
     * 进度条的颜色
     */
    private int progressColor ;
    /**
     * 是否继续画
     */
    private boolean draw = true;
    /**
     * 当前绘制的进度 0-360
     */
    private int progress ;

    private boolean isNext ;

    private RectF oval ;

    public SmallProgressBar(Context context) {
        this(context,null);
    }

    public SmallProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SmallProgressBar);

        mainColor = a.getColor(R.styleable.SmallProgressBar_mainColor, Color.GRAY);
        progressColor = a.getColor(R.styleable.SmallProgressBar_progressColor,
                ContextCompat.getColor(context,R.color.colorAccent));
        a.recycle();

        mPaint = new Paint();
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        new Thread(){
            @Override
            public void run() {
                while (draw){
                    progress ++ ;
                    if(progress == 360){
                        progress = 0;
                        isNext = !isNext ;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(mainColor);
        int center = getWidth() / 2;
        int radius = center - 5;
        canvas.drawCircle(center,center,radius,mPaint);
        mPaint.setColor(progressColor);
        if(oval == null){
            oval = new RectF(center-radius,center-radius,center+radius,center+radius);
        }
        canvas.drawArc(oval,progress-90,isNext ? progress/4 : (360-progress)/4,false,mPaint);
    }

    /**
     * 设置是否要继续画
     * @param draw
     */
    public void setDraw(boolean draw){
        this.draw = draw ;
    }
}
