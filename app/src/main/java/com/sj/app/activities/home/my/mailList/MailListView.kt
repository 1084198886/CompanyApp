package com.sj.app.activities.home.my.mailList

import android.app.Activity

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 通讯录
 */
interface MailListView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun loadSuccess(isRefresh:Boolean)
    fun showToast(msg: String)
    fun closeProgressDialog()
}