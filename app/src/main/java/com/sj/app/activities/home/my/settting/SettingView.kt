package com.sj.app.activities.home.my.settting

import android.app.Activity
import com.sj.app.activities.home.my.settting.bean.SettingsBean

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc  设置
 */
interface SettingView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun showToast(msg: String)
    fun closeProgressDialog()
    fun showSettings(settings: SettingsBean)
    fun loginOutSuccess()
    fun saveRecvLetterSwitchSuc(msg: String)
}