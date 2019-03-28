package com.sdl.primer.motion

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * create by songdongliang on 2019/3/27
 */
class ViewPagerAdapter(fm: FragmentManager?): FragmentPagerAdapter(fm) {

    private val fragmentList = arrayListOf<Fragment>()
    private val fragmentTitleList = arrayListOf<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    private fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

    fun addPage(s: String, layout: Int) {
        val page = Page()
        val arg = Bundle()
        arg.putInt("layout", layout)
        page.arguments = arg
        addFragment(page, s)
    }
}