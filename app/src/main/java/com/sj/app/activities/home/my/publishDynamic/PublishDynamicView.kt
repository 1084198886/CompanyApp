package com.sj.app.activities.home.my.publishDynamic

import android.app.Activity

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 发布动态
 */
interface PublishDynamicView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun publishSuccess(msg: String)
    fun showToast(msg: String)
    fun closeProgressDialog()
}