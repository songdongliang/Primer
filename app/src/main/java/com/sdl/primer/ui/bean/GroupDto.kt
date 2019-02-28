package com.sdl.primer.ui.bean

import com.sdl.primer.ui.decoration.SimpleSuspensionDecoration

/**
 * create by songdongliang on 2019/2/28
 */
class GroupDto: SimpleSuspensionDecoration.TitleDecorationCallback {

    var datas = ArrayList<GroupIdName>()

    override fun getGroupId(position: Int): Long {
        return datas[position].id
    }

    override fun getGroupName(position: Int): String {
        return datas[position].name
    }
}