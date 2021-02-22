package com.sj.app.activities.home.my.mailList

import com.sj.app.activities.SPApplication
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @seea
 * @desc 通讯录
 */
class MailListPresenter(private val mailListView: MailListView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun getMailList(isRefresh: Boolean) {
        mailListView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                val dataList = phoneTerminal.getMyPublishGroup()
                mailListView.getActivity().runOnUiThread {
                    mailListView.loadSuccess(isRefresh)
                }
            } catch (ex: TerminalInitError) {
                mailListView.getActivity().runOnUiThread {
                    mailListView.showToast(ex.message!!)
                }
            } finally {
                mailListView.getActivity().runOnUiThread {
                    mailListView.closeProgressDialog()
                }
            }
        }
    }

}