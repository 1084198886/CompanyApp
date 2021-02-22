package com.sj.app.activities.home.my.myDynamic

import com.sj.app.activities.SPApplication
import com.sj.app.activities.home.my.publishDynamic.PublishDynamicView
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 发布动态
 */
class PublishDynamicPresenter(private val publishDynamicView: PublishDynamicView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun publishDynamic() {
        publishDynamicView.showProgressDialog("发布中...")
        ThreadPool.getShortPool().execute {
            try {
                phoneTerminal.publishDynamic()
                publishDynamicView.getActivity().runOnUiThread {
                    publishDynamicView.publishSuccess("发布成功")
                }
            } catch (ex: TerminalInitError) {
                publishDynamicView.getActivity().runOnUiThread {
                    publishDynamicView.showToast(ex.message!!)
                }
            } finally {
                publishDynamicView.getActivity().runOnUiThread {
                    publishDynamicView.closeProgressDialog()
                }
            }
        }
    }

}