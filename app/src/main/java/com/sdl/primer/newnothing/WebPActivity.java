package com.sdl.primer.newnothing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sdl.primer.R;

public class WebPActivity extends AppCompatActivity {

    private SimpleDraweeView mSimpleDraweeView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_p);

//        mSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.simple_drawee_view);
//        mSimpleDraweeView.setImageResource(R.mipmap.webp);
    }


}
