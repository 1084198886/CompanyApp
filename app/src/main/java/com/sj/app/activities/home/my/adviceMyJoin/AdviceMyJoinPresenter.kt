package com.sj.app.activities.home.my.adviceMyJoin

import com.sj.app.activities.SPApplication
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @seea
 * @desc 我报名的征求
 */
class AdviceMyJoinPresenter(private val adviceMyJoinView: AdviceMyJoinView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun getAdviceMyJoin(isRefresh: Boolean) {
        adviceMyJoinView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                val dataList = phoneTerminal.getAdviceMyJoin()
                adviceMyJoinView.getActivity().runOnUiThread {
                    adviceMyJoinView.loadSuccess(isRefresh, dataList)
                }
            } catch (ex: TerminalInitError) {
                adviceMyJoinView.getActivity().runOnUiThread {
                    adviceMyJoinView.showToast(ex.message!!)
                }
            } finally {
                adviceMyJoinView.getActivity().runOnUiThread {
                    adviceMyJoinView.closeProgressDialog()
                }
            }
        }
    }

}