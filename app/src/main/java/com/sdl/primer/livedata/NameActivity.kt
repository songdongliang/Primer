package com.sdl.primer.livedata

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sdl.primer.R
import kotlinx.android.synthetic.main.activity_name.*

// https://juejin.im/entry/5b345ecd6fb9a00e434676b4
class NameActivity : AppCompatActivity() {

    private val mModel: NameViewModel by lazy {
        // 创建ViewModel实例
        ViewModelProviders.of(this).get(NameViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        // 创建更新UI的观察者
        val nameObservable: Observer<String> = Observer {
            mNameTextView.text = it
        }

        // 观察liveData
        mModel.getCurrentName().observe(this, nameObservable)

    }
}
