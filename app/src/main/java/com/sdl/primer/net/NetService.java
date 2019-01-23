package com.sdl.primer.net;

import com.sdl.primer.livedata3.Data1;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by 11870 on 2017/2/28.
 */

public interface NetService {

    @GET("baike/pic/item/7aec54e736d12f2ee289bffe4cc2d5628435689b.jpg")
    @Headers("Cache-Control:public,max-age=60")
    Observable<ResponseBody> getBytes();

    @GET
    Observable<ArrayList<Data1>> getTestPerson(@Url String url);
}
