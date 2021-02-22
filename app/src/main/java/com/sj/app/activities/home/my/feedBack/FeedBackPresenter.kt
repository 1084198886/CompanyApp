package com.sj.app.activities.home.my.feedBack

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
class FeedBackPresenter(private val feedBackView: FeedBackView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun feedBack(advice: String) {
        feedBackView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                phoneTerminal.feedBack(advice)
                feedBackView.getActivity().runOnUiThread {
                    feedBackView.showToast("提交成功")
                }
            } catch (ex: TerminalInitError) {
                feedBackView.getActivity().runOnUiThread {
                    feedBackView.showToast(ex.message!!)
                }
            } finally {
                feedBackView.getActivity().runOnUiThread {
                    feedBackView.closeProgressDialog()
                }
            }
        }
    }

}