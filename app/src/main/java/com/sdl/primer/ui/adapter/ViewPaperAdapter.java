package com.sdl.primer.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

public class ViewPaperAdapter extends PagerAdapter {
    private Context mContext;
    private List<String> mImageUrls;

    public ViewPaperAdapter(Context context, List<String> imageUrls) {
        this.mContext = context;
        this.mImageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ImageView image=new ImageView(mContext);
//        image.setImageResource(mImageIds.get(position));

        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setPadding(200, 80, 200, 80);
        ((ViewPager) container).addView(image);
        return image;
    }

}