package com.sdl.primer.livedata3

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ObservableBoolean
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sdl.primer.R
import com.sdl.primer.databinding.ActivityDemoBinding

/**
 * https://github.com/r17171709/android_demo/blob/master/DataBindingDemo2
 *
 * https://www.jianshu.com/p/94a54a7e5030
 */
class DemoActivity : AppCompatActivity() {

    private var vm: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewDataBinding = DataBindingUtil
                .setContentView<ActivityDemoBinding>(this, R.layout.activity_demo)
        val autoRefresh = AutoRefresh(ObservableBoolean(false))
        viewDataBinding.rf = autoRefresh

        vm = ViewModelProviders.of(this, MainViewModelFactory(autoRefresh))
                .get(MainViewModel::class.java)
        vm?.response?.observe(this, Observer<ArrayList<Data1>> {
            Toast.makeText(this, "得到结果", Toast.LENGTH_LONG).show()
            vm?.notifyDataSetChanged(it!!)
        })

        viewDataBinding.adapter = vm?.adapter

        vm?.id?.value = "1"
    }
}
