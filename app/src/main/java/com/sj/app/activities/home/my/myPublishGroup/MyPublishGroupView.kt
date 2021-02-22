package com.sj.app.activities.home.my.myPublishGroup

import android.app.Activity

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 我发的圈子
 */
interface MyPublishGroupView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun loadSuccess(isRefresh:Boolean)
    fun deleteSuccess()
    fun showToast(msg: String)
    fun closeProgressDialog()
}