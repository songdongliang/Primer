package com.sdl.primer.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.sdl.primer.R
import com.sdl.primer.ui.adapter.SimpleTitleBehaviorAdapter

class BehaviorAdvanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_behavior_advance)

        val recyclerView = findViewById<RecyclerView>(R.id.mRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val mutableList = mutableListOf<String>()
        mutableList.add("苏轼")
        mutableList.add("一片苍茫")
        mutableList.add("风流南宋")
        mutableList.add("风吹草低见牛羊")
        mutableList.add("春风又绿江南岸")
        mutableList.add("兴，百姓苦")
        mutableList.add("故国不堪回首月明中")
        mutableList.add("周瑜")
        recyclerView.adapter = SimpleTitleBehaviorAdapter(mutableList, this)
    }
}
