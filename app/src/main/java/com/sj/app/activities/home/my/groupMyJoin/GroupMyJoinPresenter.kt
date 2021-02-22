package com.sj.app.activities.home.my.groupMyJoin

import com.sj.app.activities.SPApplication
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 我加入的团
 */
class GroupMyJoinPresenter(private val groupMyJoinView: GroupMyJoinView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun getGroupMyJoin(isRefresh: Boolean) {
        groupMyJoinView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                val groupList = phoneTerminal.getGroupMyJoin()
                groupMyJoinView.getActivity().runOnUiThread {
                    groupMyJoinView.loadSuccess(isRefresh)
                }
            } catch (ex: TerminalInitError) {
                groupMyJoinView.getActivity().runOnUiThread {
                    groupMyJoinView.showToast(ex.message!!)
                }
            } finally {
                groupMyJoinView.getActivity().runOnUiThread {
                    groupMyJoinView.closeProgressDialog()
                }
            }
        }
    }

}