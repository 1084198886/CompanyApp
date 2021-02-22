package com.sj.app.activities.init.labelSelect

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
class LabelSelectPresenter(private val labelSelectView: LabelSelectView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun saveCustLabel(labels: String) {
        labelSelectView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                phoneTerminal.saveCustLabel(labels)
                labelSelectView.getActivity().runOnUiThread {
                    labelSelectView.saveLabelSuccess("绑定成功")
                }
            } catch (ex: TerminalInitError) {
                labelSelectView.getActivity().runOnUiThread {
                    labelSelectView.showToast(ex.message!!)
                }
            } finally {
                labelSelectView.getActivity().runOnUiThread {
                    labelSelectView.closeProgressDialog()
                }
            }
        }
    }
}