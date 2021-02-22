package com.sj.app.activities.init

import android.app.Activity

/**
 * @author gqy
 * @date 2019/8/9 0009.
 * @since 1.0.0
 * @see
 * @desc  TODO
 */
interface InitView {
    fun getActivity(): Activity
    fun loginSuccess()
    fun showProgressDialog(msg: String)
    fun showToast(msg: String)
    fun closeProgressDialog()
}