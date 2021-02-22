package com.sj.app.activities.init.perfectInfo

import android.app.Activity

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc  TODO
 */
interface PerfectInfoView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun saveCustInfoSuccess(msg:String)
    fun showToast(msg: String)
    fun closeProgressDialog()
}