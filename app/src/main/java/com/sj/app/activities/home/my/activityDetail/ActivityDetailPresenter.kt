package com.sj.app.activities.home.my.activityDetail

import com.sj.app.activities.SPApplication
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 活动详情
 */
class ActivityDetailPresenter(private val activityDetailPresenter: ActivityDetailView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun getActivityDetails() {
        activityDetailPresenter.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                val dataList = phoneTerminal.getActivityDetails()
                activityDetailPresenter.getActivity().runOnUiThread {
                    activityDetailPresenter.loadSuccess()
                }
            } catch (ex: TerminalInitError) {
                activityDetailPresenter.getActivity().runOnUiThread {
                    activityDetailPresenter.showToast(ex.message!!)
                }
            } finally {
                activityDetailPresenter.getActivity().runOnUiThread {
                    activityDetailPresenter.closeProgressDialog()
                }
            }
        }
    }

}