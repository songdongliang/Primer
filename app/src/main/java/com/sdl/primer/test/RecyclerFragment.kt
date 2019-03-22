package com.sdl.primer.test

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sdl.primer.R
import com.sdl.primer.ui.adapter.SimpleTextAdapter
import com.sdl.primer.ui.bean.GroupDto
import com.sdl.primer.ui.bean.GroupIdName
import com.sdl.primer.ui.decoration.SimpleSuspensionDecoration
import kotlinx.android.synthetic.main.fragment_recycler.*

/**
 * create by songdongliang on 2019/3/22
 */
class RecyclerFragment: Fragment {

    constructor()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        val simpleSuspensionDecoration = SimpleSuspensionDecoration(context!!)
        simpleSuspensionDecoration.callback = groupDto
        mRecyclerView.addItemDecoration(simpleSuspensionDecoration)
        mRecyclerView.adapter = SimpleTextAdapter(context!!, list)
    }
}