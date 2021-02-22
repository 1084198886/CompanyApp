package com.sj.app.activities.home.my.realnameAuth

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
class RealNameAuthPresenter(private val feedBackView: RealNameAuthView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun realNameAuth() {
        feedBackView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                phoneTerminal.realNameAuth()
                feedBackView.getActivity().runOnUiThread {
                    feedBackView.showToast("认证成功")
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