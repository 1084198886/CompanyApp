package com.sj.app.activities.home.my.adviceMyCreate

import android.app.Activity
import com.supwisdom.orderlib.bean.MyStart

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 我创建的征求
 */
interface AdviceMyCreateView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun loadSuccess(
        refresh: Boolean,
        dataList: ArrayList<MyStart>?
    )
    fun showToast(msg: String)
    fun closeProgressDialog()
    fun deleteGroupMyStartSuc(msg: String)
}