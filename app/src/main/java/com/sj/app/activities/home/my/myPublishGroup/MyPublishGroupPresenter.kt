package com.sj.app.activities.home.my.myPublishGroup

import com.sj.app.activities.SPApplication
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @seea
 * @desc 我发的圈子
 */
class MyPublishGroupPresenter(private val myPublishGroupView: MyPublishGroupView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun getMyPublishGroup(isRefresh: Boolean) {
        myPublishGroupView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                val dataList = phoneTerminal.getMyPublishGroup()
                myPublishGroupView.getActivity().runOnUiThread {
                    myPublishGroupView.loadSuccess(isRefresh)
                }
            } catch (ex: TerminalInitError) {
                myPublishGroupView.getActivity().runOnUiThread {
                    myPublishGroupView.showToast(ex.message!!)
                }
            } finally {
                myPublishGroupView.getActivity().runOnUiThread {
                    myPublishGroupView.closeProgressDialog()
                }
            }
        }
    }

}