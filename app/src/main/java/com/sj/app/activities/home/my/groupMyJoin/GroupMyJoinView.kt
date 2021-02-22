package com.sj.app.activities.home.my.groupMyJoin

import android.app.Activity

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 我加入的团
 */
interface GroupMyJoinView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun loadSuccess(refresh: Boolean)
    fun showToast(msg: String)
    fun closeProgressDialog()
}