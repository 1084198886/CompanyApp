package com.sj.app.activities.home.my.groupMyStart

import com.sj.app.activities.SPApplication
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 我开的团
 */
class GroupMyStartPresenter(private val groupMyStartView: GroupMyStartView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun getGroupMyStart(isRefresh: Boolean) {
        groupMyStartView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                val dataList = phoneTerminal.getGroupMyStart()
                groupMyStartView.getActivity().runOnUiThread {
                    groupMyStartView.loadSuccess(isRefresh, dataList)
                }
            } catch (ex: TerminalInitError) {
                groupMyStartView.getActivity().runOnUiThread {
                    groupMyStartView.showToast(ex.message!!)
                }
            } finally {
                groupMyStartView.getActivity().runOnUiThread {
                    groupMyStartView.closeProgressDialog()
                }
            }
        }
    }

    fun deleteGroupMyStart() {
        groupMyStartView.showProgressDialog("删除中...")
        ThreadPool.getShortPool().execute {
            try {
                phoneTerminal.deleteGroupMyStart()
                groupMyStartView.getActivity().runOnUiThread {
                    groupMyStartView.deleteGroupMyStartSuc("删除成功")
                }
            } catch (ex: TerminalInitError) {
                groupMyStartView.getActivity().runOnUiThread {
                    groupMyStartView.showToast(ex.message!!)
                }
            } finally {
                groupMyStartView.getActivity().runOnUiThread {
                    groupMyStartView.closeProgressDialog()
                }
            }
        }
    }

}