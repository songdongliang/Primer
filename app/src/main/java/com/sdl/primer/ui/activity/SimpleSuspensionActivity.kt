package com.sdl.primer.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sdl.primer.R
import com.sdl.primer.ui.adapter.SimpleTextAdapter
import com.sdl.primer.ui.decoration.SimpleSuspensionDecoration
import kotlinx.android.synthetic.main.activity_simple_suspension.*

class SimpleSuspensionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_suspension)

        mRecyclerView.addItemDecoration(SimpleSuspensionDecoration(this))
        mRecyclerView.adapter = SimpleTextAdapter(this)
    }
}
