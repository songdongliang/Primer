package com.sdl.primer.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.sdl.primer.R;

/**
 * Created by 11870 on 2017/1/10.
 * 音量控制
 */

public class CustomVolumeControlBar extends View {
    /**
     * 第一圈的颜色
     */
    private int mFirstColor ;
    /**
     * 第二圈的颜色
     */
    private int mSecondColor ;
    /**
     * 圈的宽度
     */
    private int mCircleWidth;
    /**
     * 画笔
     */
    private Paint mPaint ;
    /**
     * 当前进度
     */
    private int mCurrentCount = 3 ;
    /**
     * 中间显示的图片
     */
    private Bitmap mImage ;
    /**
     * 每个块块间的间隙
     */
    private int mSplitSize ;
    /**
     * 个数
     */
    private int mCount;
    /**
     * 绘制参考的矩形
     */
    private Rect mRect ;

    public CustomVolumeControlBar(Context context) {
        super(context);
    }

    public CustomVolumeControlBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomVolumeControlBar);
        mFirstColor = a.getColor(R.styleable.CustomVolumeControlBar_firstColor, Color.GRAY);
        mSecondColor = a.getColor(R.styleable.CustomVolumeControlBar_secondColor,
                ContextCompat.getColor(context,R.color.colorAccent));
        mImage = BitmapFactory.decodeResource(getResources(),
                a.getResourceId(R.styleable.CustomVolumeControlBar_bg,R.mipmap.ic_launcher));
        mCircleWidth = a.getDimensionPixelSize(R.styleable.CustomVolumeControlBar_circleWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,20,getResources().getDisplayMetrics()));
        mCount = a.getInt(R.styleable.CustomVolumeControlBar_dotCount,20);
        mSplitSize = a.getInt(R.styleable.CustomVolumeControlBar_splitSize,20);
        a.recycle();
        mPaint = new Paint();
        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
