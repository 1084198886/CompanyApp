package com.sj.app.activities.home.my.realnameAuth

import android.app.Activity

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc
 */
interface RealNameAuthView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun showToast(msg: String)
    fun authSuccess(msg: String)
    fun closeProgressDialog()
}