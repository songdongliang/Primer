package com.sdl.primer.livedata2

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sdl.primer.R
import com.sdl.primer.databinding.ActivityRecyclerView2Binding

/**
 * https://github.com/r17171709/android_demo/blob/master/DataBindingDemo2
 *
 * https://www.jianshu.com/p/94a54a7e5030
 */
class RecyclerViewActivity : AppCompatActivity() {

    val teacher2: ArrayList<Teacher2> by lazy {
        ArrayList<Teacher2>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewDataBinding = DataBindingUtil
                .setContentView<ActivityRecyclerView2Binding>(this, R.layout.activity_recycler_view2)
        viewDataBinding.adapter = RecyclerViewAdapter(teacher2)

        for (i in 0..30) {
            teacher2.add(Teacher2(ObservableField("Hello$"), ObservableField(i)))
        }
        viewDataBinding.adapter?.notifyDataSetChanged()
    }
}
