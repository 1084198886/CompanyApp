package com.sj.app.activities.init.labelSelect

import android.app.Activity

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc
 */
interface LabelSelectView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun showToast(msg: String)
    fun saveLabelSuccess(msg: String)
    fun closeProgressDialog()
}