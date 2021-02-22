package com.sj.app.activities.home.advice

import com.sj.app.activities.SPApplication
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc
 */
class AdvicePresenter(private val adviceView: AdviceView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun getAdviceData(pullDownRefresh: Boolean) {
        adviceView.showProgressDialog("获取中")
        ThreadPool.getShortPool().execute {
            try {
                val dataList = phoneTerminal.getAdviceData()
                if (dataList != null) {
                    adviceView.getHomeActivity().runOnUiThread {
                        adviceView.loadDataSuccess(dataList, pullDownRefresh)
                    }
                }
            } catch (ex: TerminalInitError) {
                adviceView.getHomeActivity().runOnUiThread {
                    adviceView.showToast(ex.message!!)
                }
            } finally {
                adviceView.getHomeActivity().runOnUiThread {
                    adviceView.loadFinished()
                }
            }
        }
    }
}