package com.sj.app.activities.home.my.activityCollect

import com.sj.app.activities.SPApplication
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 收藏和关注的活动
 */
class CollectAttentionPresenter(private val collectAttentionView: CollectAttentionView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun getCollectAttentions(isRefresh: Boolean) {
        collectAttentionView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                val dataList = phoneTerminal.getCollectAttentions()
                collectAttentionView.getActivity().runOnUiThread {
                    collectAttentionView.loadSuccess(isRefresh)
                }
            } catch (ex: TerminalInitError) {
                collectAttentionView.getActivity().runOnUiThread {
                    collectAttentionView.showToast(ex.message!!)
                }
            } finally {
                collectAttentionView.getActivity().runOnUiThread {
                    collectAttentionView.closeProgressDialog()
                }
            }
        }
    }

}