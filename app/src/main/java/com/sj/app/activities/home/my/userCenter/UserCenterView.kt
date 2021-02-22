package com.sj.app.activities.home.my.userCenter

import android.app.Activity
import com.supwisdom.orderlib.entity.DynamicParaRecord

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc  TODO
 */
interface UserCenterView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun showUserInfo(data: DynamicParaRecord)
    fun showToast(msg: String)
    fun closeProgressDialog()
}