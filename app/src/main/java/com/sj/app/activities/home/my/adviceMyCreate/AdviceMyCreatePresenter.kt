package com.sj.app.activities.home.my.adviceMyCreate

import com.sj.app.activities.SPApplication
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 我创建的征求
 */
class AdviceMyCreatePresenter(private val adviceMyCreateView: AdviceMyCreateView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun getGroupMyStart(isRefresh: Boolean) {
        adviceMyCreateView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                val dataList = phoneTerminal.getGroupMyStart()
                adviceMyCreateView.getActivity().runOnUiThread {
                    adviceMyCreateView.loadSuccess(isRefresh, dataList)
                }
            } catch (ex: TerminalInitError) {
                adviceMyCreateView.getActivity().runOnUiThread {
                    adviceMyCreateView.showToast(ex.message!!)
                }
            } finally {
                adviceMyCreateView.getActivity().runOnUiThread {
                    adviceMyCreateView.closeProgressDialog()
                }
            }
        }
    }

    fun deleteGroupMyStart() {
        adviceMyCreateView.showProgressDialog("删除中...")
        ThreadPool.getShortPool().execute {
            try {
                phoneTerminal.deleteGroupMyStart()
                adviceMyCreateView.getActivity().runOnUiThread {
                    adviceMyCreateView.deleteGroupMyStartSuc("删除成功")
                }
            } catch (ex: TerminalInitError) {
                adviceMyCreateView.getActivity().runOnUiThread {
                    adviceMyCreateView.showToast(ex.message!!)
                }
            } finally {
                adviceMyCreateView.getActivity().runOnUiThread {
                    adviceMyCreateView.closeProgressDialog()
                }
            }
        }
    }

}