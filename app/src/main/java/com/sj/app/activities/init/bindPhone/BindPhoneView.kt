package com.sj.app.activities.init.bindPhone

import android.app.Activity

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc
 */
interface BindPhoneView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun showToast(msg: String)
    fun bindPhoneSuccess(msg: String)
    fun closeProgressDialog()
}