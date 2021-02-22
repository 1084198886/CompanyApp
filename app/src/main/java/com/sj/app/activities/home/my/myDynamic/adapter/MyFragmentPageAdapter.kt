package com.sj.app.activities.home.my.myDynamic.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
/**
 * @author gqy
 * @date 2019/8/24
 * @since 1.0.0
 * @see
 * @desc
 * 适合于 Fragment数量不多的情况。当某个页面不可见时，
 * 该页面对应的View可能会被销毁，但是所有的Fragment都会一直存在于内存中
 */
class MyFragmentPageAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val list = arrayListOf<Fragment>()

    fun setList(fragments: List<Fragment>) {
        list.clear()
        list.addAll(fragments)
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }
}
