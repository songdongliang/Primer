package com.sdl.primer.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sdl.primer.R;
import com.sdl.primer.util.StringConfig;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class NetCacheActivity extends AppCompatActivity {

    private String path = "http://imgsrc.baidu.com/";

    private ImageView iv ;

    private NetService netService ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_cache);

        initView();
        initData();
    }

    private void initView() {
        iv = (ImageView) findViewById(R.id.iv) ;
    }

    private void initData() {
        File cacheFile = new File(getCacheDir(), StringConfig.NET_CACHE);
        Cache cache = new Cache(cacheFile,10 * 1024 * 1024);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(path)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        netService = retrofit.create(NetService.class);

        netService.getBytes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                    iv.setImageBitmap(bitmap);
                });
    }

    public void request(View view){
        netService.getBytes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                    iv.setImageBitmap(bitmap);
                });
    }

}
