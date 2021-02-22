package com.sj.app.activities.home.my.applyForGroup

import com.sj.app.activities.SPApplication
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @seea
 * @desc 入群申请
 */
class ApplyForGroupPresenter(private val applyForGroupView: ApplyForGroupView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun getApplyForGroup(isRefresh: Boolean) {
        applyForGroupView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                val dataList = phoneTerminal.getApplyForGroup()
                applyForGroupView.getActivity().runOnUiThread {
                    applyForGroupView.loadSuccess(isRefresh, dataList)
                }
            } catch (ex: TerminalInitError) {
                applyForGroupView.getActivity().runOnUiThread {
                    applyForGroupView.showToast(ex.message!!)
                }
            } finally {
                applyForGroupView.getActivity().runOnUiThread {
                    applyForGroupView.closeProgressDialog()
                }
            }
        }
    }

}