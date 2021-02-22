package com.sj.app.activities.home.main.userInfo

import android.app.Activity

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc  TODO
 */
interface UserInfoView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun showToast(msg: String)
    fun closeProgressDialog()
    fun showUserInfo()
}