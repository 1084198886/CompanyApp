package com.sj.app.activities.home.my.activityCollect

import android.app.Activity

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 收藏和关注的活动
 */
interface CollectAttentionView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun loadSuccess(refresh: Boolean)
    fun showToast(msg: String)
    fun closeProgressDialog()
}