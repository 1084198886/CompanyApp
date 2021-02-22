package com.sj.app.activities.home.advice

import android.app.Activity

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc  TODO
 */
interface AdviceView {
    fun getHomeActivity(): Activity
    fun loadDataSuccess(dataList: List<String>, isPullDownRefresh:Boolean)
    fun showProgressDialog(msg: String)
    fun showToast(msg: String)
    fun closeProgressDialog()
    fun loadFinished()
}