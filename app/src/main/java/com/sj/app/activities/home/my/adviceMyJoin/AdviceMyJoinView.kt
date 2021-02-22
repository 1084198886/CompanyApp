package com.sj.app.activities.home.my.adviceMyJoin

import android.app.Activity
import com.supwisdom.orderlib.bean.MyStart

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 我报名的征求
 */
interface AdviceMyJoinView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun loadSuccess(
        refresh: Boolean,
        dataList: ArrayList<MyStart>?
    )
    fun showToast(msg: String)
    fun closeProgressDialog()
}