package com.sj.app.activities.home.my.feedBack

import android.app.Activity

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc
 */
interface FeedBackView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun showToast(msg: String)
    fun feedBackSuccess(msg: String)
    fun closeProgressDialog()
}