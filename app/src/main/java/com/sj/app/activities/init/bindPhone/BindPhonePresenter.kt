package com.sj.app.activities.init.bindPhone

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
class BindPhonePresenter(private val bindPhoneView: BindPhoneView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun getVerifyNo(account: String) {
        bindPhoneView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                phoneTerminal.getVerifyNo(account)
                bindPhoneView.getActivity().runOnUiThread {
                    bindPhoneView.showToast("获取验证码成功")
                }
            } catch (ex: TerminalInitError) {
                bindPhoneView.getActivity().runOnUiThread {
                    bindPhoneView.showToast(ex.message!!)
                }
            } finally {
                bindPhoneView.getActivity().runOnUiThread {
                    bindPhoneView.closeProgressDialog()
                }
            }
        }
    }

    fun bindPhone(phone: String, verifyNo: String) {
        bindPhoneView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                phoneTerminal.bindPhone(phone, verifyNo)
                bindPhoneView.getActivity().runOnUiThread {
                    bindPhoneView.bindPhoneSuccess("绑定成功")
                }
            } catch (ex: TerminalInitError) {
                bindPhoneView.getActivity().runOnUiThread {
                    bindPhoneView.showToast(ex.message!!)
                }
            } finally {
                bindPhoneView.getActivity().runOnUiThread {
                    bindPhoneView.closeProgressDialog()
                }
            }
        }
    }
}