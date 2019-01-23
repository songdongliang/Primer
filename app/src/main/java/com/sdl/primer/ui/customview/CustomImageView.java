package com.sdl.primer.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.sdl.primer.R;

/**
 * Created by 11870 on 2017/1/6.
 */

public class CustomImageView extends View {

    private static final String TAG = "CustomImageView";

    private static final int IMAGE_SCALE_FITXY = 0;
    private static final int IMAGE_SCALE_CENTER = 1;

    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 位图
     */
    private Bitmap mImage ;
    /**
     * 图片缩放比例
     */
    private int mImageScale ;
    /**
     * 文字
     */
    private String mTitle ;
    /**
     * 文本颜色
     */
    private int mTextColor ;
    /**
     * 文本大小
     */
    private int mTextSize ;

    private Rect mRect ;
    /**
     * 文本所在的矩形
     */
    private Rect mTextBound ;

    private int mWidth;
    private int mHeight ;

    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CustomImageView,defStyleAttr,0);
        int n = a.getIndexCount();
        for(int i = 0;i < n;i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.CustomImageView_image:
                    mImage = BitmapFactory.decodeResource(getResources(),a.getResourceId(attr,0));
                    break;
                case R.styleable.CustomImageView_imageScaleType:
                    mImageScale = a.getInt(attr,0);
                    break;
                case R.styleable.CustomImageView_titleText:
                    mTitle = a.getString(attr);
                    break;
                case R.styleable.CustomImageView_titleTextColor:
                    mTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomImageView_titleTextSize:
                    mTextSize = a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                    break;
            }
        }
        //释放
        a.recycle();
        mRect = new Rect();
        mPaint = new Paint();
        mTextBound = new Rect();
        mPaint.setTextSize(mTextSize);
        //计算绘制字体需要的范围
        mPaint.getTextBounds(mTitle,0,mTitle.length(),mTextBound);

        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw() called with: canvas = [" + canvas + "]");
        //画边框
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.CYAN);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        mRect.left = getPaddingLeft();
        mRect.right = mWidth - getPaddingRight();
        mRect.top = getPaddingTop();
        mRect.bottom = mHeight - getPaddingBottom();

        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        //画文字
        if(mTextBound.width() > mRect.width()){
            TextPaint textPaint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(
                    mTitle,textPaint,mRect.width(),TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg,getPaddingLeft(),mHeight - getPaddingBottom(),mPaint);
        } else {
            //正常情况下，将字体居中
            canvas.drawText(mTitle,(mWidth-mTextBound.width())/2,mHeight-getPaddingBottom(),mPaint);
        }

        mRect.bottom -= mTextBound.height();
        if(mImageScale == IMAGE_SCALE_FITXY){
            canvas.drawBitmap(mImage,null,mRect,mPaint);
        } else {
            mRect.left = mWidth / 2 - mImage.getWidth() / 2;
            mRect.right = mWidth / 2 + mImage.getWidth() / 2;
            mRect.top = (mHeight - mTextBound.height())/2 - mImage.getHeight()/2;
            mRect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight()/2;

            canvas.drawBitmap(mImage,null,mRect,mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure() called with: widthMeasureSpec = [" + widthMeasureSpec + "], heightMeasureSpec = [" + heightMeasureSpec + "]");
        //设置宽度
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if(specMode == MeasureSpec.EXACTLY){ //match actual
            mWidth = specSize ;
        } else if(specMode == MeasureSpec.AT_MOST){ //wrap_content
            //图片决定的宽
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            //字体决定的宽
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();
            int desireWidth = Math.max(desireByImg,desireByTitle);
            mWidth = Math.min(desireWidth,specSize);
        }
        //设置高度
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if(specMode == MeasureSpec.EXACTLY){ //match actual
            mHeight = specSize ;
        } else if(specMode == MeasureSpec.AT_MOST){ //wrap_content
            int desireHeight = getPaddingTop() + getPaddingBottom()
                    + mImage.getHeight() + mTextBound.height();
            mHeight = Math.min(desireHeight,specSize);
        }
        Log.i(TAG,"width: " + mWidth + ", mHeight: " + mHeight);
        setMeasuredDimension(mWidth,mHeight);
    }
}
