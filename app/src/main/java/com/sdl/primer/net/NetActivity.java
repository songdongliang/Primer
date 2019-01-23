package com.sdl.primer.net;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sdl.primer.R;

public class NetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
    }

    public void netCache(View view){
        jumpActivity(NetCacheActivity.class);
    }

    public void jumpActivity(Class clazz){
        startActivity(new Intent(this,clazz));
    }
}
