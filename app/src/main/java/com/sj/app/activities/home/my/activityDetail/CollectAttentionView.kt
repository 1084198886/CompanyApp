package com.sj.app.activities.home.my.activityDetail

import android.app.Activity

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 活动详情
 */
interface ActivityDetailView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun loadSuccess()
    fun showToast(msg: String)
    fun closeProgressDialog()
}