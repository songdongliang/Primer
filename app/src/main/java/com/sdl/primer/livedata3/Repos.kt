package com.sdl.primer.livedata3

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.sdl.primer.net.NetService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

object Repos {

    fun getMainResponse(input: String): LiveData<ArrayList<Data1>> {
        val temp = MutableLiveData<ArrayList<Data1>>()
        val okHttpClient = OkHttpClient.Builder()
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl("http://imgsrc.baidu.com/")
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        val netService = retrofit.create(NetService::class.java)

        netService.getTestPerson("http://www.mocky.io/v2/5b470ea0320000b332301d9f")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    temp.value = it
                }

        return temp
    }
}