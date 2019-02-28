package com.sdl.primer.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sdl.primer.R
import com.sdl.primer.ui.adapter.SimpleTextAdapter
import com.sdl.primer.ui.bean.GroupDto
import com.sdl.primer.ui.bean.GroupIdName
import com.sdl.primer.ui.decoration.SimpleSuspensionDecoration
import kotlinx.android.synthetic.main.activity_simple_suspension.*

class SimpleSuspensionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_suspension)

        val groupDto = GroupDto()
        val list = groupDto.datas
        list.add(GroupIdName(1, "hello"))
        list.add(GroupIdName(1, "hello"))
        list.add(GroupIdName(2, "hello"))
        list.add(GroupIdName(2, "hello"))
        list.add(GroupIdName(2, "hello"))
        list.add(GroupIdName(2, "hello"))
        list.add(GroupIdName(2, "hello"))
        list.add(GroupIdName(3, "hello"))
        list.add(GroupIdName(3, "hello"))
        list.add(GroupIdName(4, "hello"))
        list.add(GroupIdName(5, "hello"))
        list.add(GroupIdName(5, "hello"))
        list.add(GroupIdName(5, "hello"))
        list.add(GroupIdName(6, "hello"))
        list.add(GroupIdName(7, "hello"))
        list.add(GroupIdName(7, "hello"))
        list.add(GroupIdName(7, "hello"))
        list.add(GroupIdName(8, "hello"))
        list.add(GroupIdName(8, "hello"))
        list.add(GroupIdName(9, "hello"))
        list.add(GroupIdName(10, "hello"))
        list.add(GroupIdName(10, "hello"))
        val simpleSuspensionDecoration = SimpleSuspensionDecoration(this)
        simpleSuspensionDecoration.callback = groupDto
        mRecyclerView.addItemDecoration(simpleSuspensionDecoration)
        mRecyclerView.adapter = SimpleTextAdapter(this, list)
    }
}
