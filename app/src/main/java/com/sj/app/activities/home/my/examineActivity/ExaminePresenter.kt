package com.sj.app.activities.home.my.examineActivity

import com.sj.app.activities.SPApplication
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc 审批报名活动
 */
class ExaminePresenter(private val examineView: ExamineView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun getExamineActivities(isRefresh: Boolean) {
        examineView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                val dataList = phoneTerminal.getExamineActivities()
                examineView.getActivity().runOnUiThread {
                    examineView.loadSuccess(dataList,isRefresh)
                }
            } catch (ex: TerminalInitError) {
                examineView.getActivity().runOnUiThread {
                    examineView.showToast(ex.message!!)
                }
            } finally {
                examineView.getActivity().runOnUiThread {
                    examineView.closeProgressDialog()
                }
            }
        }
    }

}