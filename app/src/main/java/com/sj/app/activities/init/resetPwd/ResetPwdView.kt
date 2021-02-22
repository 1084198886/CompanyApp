package com.sj.app.activities.init.resetPwd

import android.app.Activity

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc  TODO
 */
interface ResetPwdView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun showToast(msg: String)
    fun closeProgressDialog()
    fun resetPwdSuccess(msg: String)
}