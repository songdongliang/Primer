package com.sdl.primer.livedata3

import android.arch.lifecycle.*
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.sdl.primer.R
import com.sdl.primer.databinding.ActivityLiveDataBinding

class LiveDataActivity : AppCompatActivity() {

    val state: NetWorkState by lazy {
        NetWorkState(applicationContext)
    }

    val id: MutableLiveData<String> = MutableLiveData()

    private val realResult: MediatorLiveData<Data1> = MediatorLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityLiveDataBinding>(this, R.layout.activity_live_data)

        state.observe(this, Observer {
            Toast.makeText(this, if (it!=null && it) "网络连接成功" else "网络连接失败", Toast.LENGTH_LONG).show()
        })

        val data1 = Transformations.switchMap(id) {
            val temp:MutableLiveData<Data1> = MutableLiveData()
            Thread(Runnable {
                Thread.sleep(4000)
                temp.postValue(Data1(it))
            }).start()
            temp
        }

        data1.observe(this, Observer {
            Log.d(LiveDataActivity::class.java.simpleName, it?.name)
        })

        val data11 = Transformations.map(id) {
            if (it != null) {
                return@map Data1(it)
            }
            Data1("")
        }

        data11.observe(this, Observer {
            Log.d(LiveDataActivity::class.java.simpleName, it?.name)
        })

        id.value = "测试姓名"

        val cacheSource = loadCache()
        realResult.addSource(cacheSource) {
            realResult.removeSource(cacheSource)
            if (TextUtils.isEmpty(it?.name)) {
                val apiResponse = loadFromNetwork()
                realResult.addSource(apiResponse) { netData ->
                    realResult.removeSource(apiResponse)
                    if (TextUtils.isEmpty(netData?.name)) {
                        realResult.value = null
                    } else {
                        realResult.value = netData
                    }
                }
            } else {
                realResult.value = it
            }
        }

        realResult.observe(this, Observer {
            Log.d(LiveDataActivity::class.java.simpleName, "onChanged ${it?.name}")
        })

    }

    private fun loadCache(): LiveData<Data1> {
        val temp = MutableLiveData<Data1>()
        Thread(Runnable {
            Thread.sleep(3000)
            temp.postValue(Data1(""))
        }).start()
        return temp
    }

    private fun loadFromNetwork(): LiveData<Data1> {
        val temp = MutableLiveData<Data1>()
        Thread(Runnable {
            Thread.sleep(3000)
            temp.postValue(Data1("cs"))
        }).start()
        return temp
    }

}
