package com.sj.app.activities.home.advice.startGroup

import com.sj.app.activities.SPApplication
import com.sj.app.activities.home.my.settting.bean.SettingsBean
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc  开团
 */
class StartGroupPresenter(private val setttingView: StartGroupView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun doNetxt() {
        setttingView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
//                phoneTerminal.getSettings(userId!!)
                val settings = SettingsBean()
                setttingView.getActivity().runOnUiThread {
//                    setttingView.showSettings(settings)
                }
            } catch (ex: TerminalInitError) {
                setttingView.getActivity().runOnUiThread {
                    setttingView.showToast(ex.message!!)
                }
            } finally {
                setttingView.getActivity().runOnUiThread {
                    setttingView.closeProgressDialog()
                }
            }
        }
    }

}