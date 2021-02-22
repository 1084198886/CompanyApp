package com.sj.app.activities.home.my.settting

import com.sj.app.activities.SPApplication
import com.sj.app.activities.home.my.settting.bean.SettingsBean
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc  设置
 */
class SettingPresenter(private val setttingView: SettingView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun getSettings(userId: String?) {
        setttingView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                phoneTerminal.getSettings(userId!!)
                val settings = SettingsBean()
                setttingView.getActivity().runOnUiThread {
                    setttingView.showSettings(settings)
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

    fun doLoginOut() {
        setttingView.showProgressDialog("退出中...")
        ThreadPool.getShortPool().execute {
            try {
                phoneTerminal.loginOut()
                setttingView.getActivity().runOnUiThread {
                    setttingView.loginOutSuccess()
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

    fun doSaveRecvLetterSwitch(state: String) {
        setttingView.showProgressDialog("加载中...")
        ThreadPool.getShortPool().execute {
            try {
                val result = phoneTerminal.saveRecvLetterSwitch(state)
                setttingView.getActivity().runOnUiThread {
                    setttingView.saveRecvLetterSwitchSuc("保存成功")
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