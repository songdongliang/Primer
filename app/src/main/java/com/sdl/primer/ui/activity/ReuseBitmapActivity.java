package com.sdl.primer.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.sdl.primer.R;

public class ReuseBitmapActivity extends AppCompatActivity {

    private ImageView iv_1 ;
    private ImageView iv_2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reuse_bitmap);

        initView();
        loadData();
    }

    private void loadData() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true ;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.gu_tian_le,options) ;
        iv_1.setImageBitmap(bitmap);

        options.inBitmap = bitmap ;
        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.mipmap.gu_tian_le,options);
        iv_2.setImageBitmap(bm);
    }

    private void initView() {
        iv_1 = (ImageView) findViewById(R.id.iv_1);
        iv_2 = (ImageView) findViewById(R.id.iv_2);
    }

}
