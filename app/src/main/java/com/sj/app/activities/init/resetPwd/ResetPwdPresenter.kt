package com.sj.app.activities.init.resetPwd

import com.sj.app.activities.SPApplication
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc  密码修改
 */
class ResetPwdPresenter(private val findPwdView: ResetPwdView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun getVerifyNo(account: String) {
        findPwdView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                phoneTerminal.getVerifyNo(account)
                findPwdView.getActivity().runOnUiThread {
                    findPwdView.showToast("获取验证码成功")
                }
            } catch (ex: TerminalInitError) {
                findPwdView.getActivity().runOnUiThread {
                    findPwdView.showToast(ex.message!!)
                }
            } finally {
                findPwdView.getActivity().runOnUiThread {
                    findPwdView.closeProgressDialog()
                }
            }
        }
    }

    fun resetPwd(account: String, verifyNo: String, newPwd: String) {
        findPwdView.showProgressDialog("加载中...")
        ThreadPool.getShortPool().execute {
            try {
                phoneTerminal.modifyPwd(account, verifyNo, newPwd)
                // TODO  密码重置成功，重置登录状态
                findPwdView.getActivity().runOnUiThread {
                    findPwdView.resetPwdSuccess("修改成功")
                }
            } catch (ex: TerminalInitError) {
                findPwdView.getActivity().runOnUiThread {
                    findPwdView.showToast(ex.message!!)
                }
            } finally {
                findPwdView.getActivity().runOnUiThread {
                    findPwdView.closeProgressDialog()
                }
            }
        }
    }
}