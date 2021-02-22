package com.sj.app.activities.home.my.examineActivity

import android.app.Activity
import com.supwisdom.orderlib.bean.ExamineData

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 审批报名活动
 */
interface ExamineView {
    fun getActivity(): Activity
    fun showProgressDialog(msg: String)
    fun showToast(msg: String)
    fun closeProgressDialog()
    fun loadSuccess(dataList:List<ExamineData>?, refresh: Boolean)
}