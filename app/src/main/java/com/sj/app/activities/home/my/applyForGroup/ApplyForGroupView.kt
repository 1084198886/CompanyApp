package com.sj.app.activities.home.my.applyForGroup

import android.app.Activity
import com.supwisdom.orderlib.bean.ApplyForGroup
import com.supwisdom.orderlib.bean.MyStart

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 入群申请
 */
interface ApplyForGroupView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun loadSuccess(
        refresh: Boolean,
        dataList: ArrayList<ApplyForGroup>?
    )

    fun showToast(msg: String)
    fun closeProgressDialog()
}