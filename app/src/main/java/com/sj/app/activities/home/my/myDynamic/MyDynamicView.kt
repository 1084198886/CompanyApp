package com.sj.app.activities.home.my.myDynamic

import android.app.Activity
import com.supwisdom.orderlib.bean.MyDynamic

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 我的动态
 */
interface MyDynamicView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun loadSuccess(dataList: ArrayList<MyDynamic>?)
    fun showToast(msg: String)
    fun closeProgressDialog()
}