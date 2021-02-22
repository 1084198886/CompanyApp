package com.sj.app.activities.init

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
class InitPresenter(private val initView: InitView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun appLogin(account: String, password: String) {
        initView.showProgressDialog("登录中")
        ThreadPool.getShortPool().execute {
            try {
                phoneTerminal.login(account, password)
                /**
                 * 登录成功保存配置
                 */
                val config = phoneTerminal.getConfig()!!
                config.account = account
                phoneTerminal.saveConfig(config)

                initView.getActivity().runOnUiThread {
                    initView.loginSuccess()
                }
            } catch (ex: TerminalInitError) {
                initView.getActivity().runOnUiThread {
                    initView.showToast(ex.message!!)
                }
            } finally {
                initView.getActivity().runOnUiThread {
                    initView.closeProgressDialog()
                }
            }
        }
    }

    fun appRegist(phone: String, verifyNo: String, pwd: String) {
        initView.showProgressDialog("注册中")
        ThreadPool.getShortPool().execute {
            try {
                phoneTerminal.regist(phone, verifyNo, pwd)
                initView.getActivity().runOnUiThread {
                    initView.showToast("注册成功")
                }
            } catch (ex: TerminalInitError) {
                initView.getActivity().runOnUiThread {
                    initView.showToast(ex.message!!)
                }
            } finally {
                initView.getActivity().runOnUiThread {
                    initView.closeProgressDialog()
                }
            }
        }
    }

    fun getVerifyNo(account: String) {
        initView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                phoneTerminal.getVerifyNo(account)
                initView.getActivity().runOnUiThread {
                    initView.showToast("获取验证码成功")
                }
            } catch (ex: TerminalInitError) {
                initView.getActivity().runOnUiThread {
                    initView.showToast(ex.message!!)
                }
            } finally {
                initView.getActivity().runOnUiThread {
                    initView.closeProgressDialog()
                }
            }
        }
    }

    fun timLogin() {
        val account = phoneTerminal.getConfig()!!.account
        val timTerminal = SPApplication.getInstance().getTIMTerminal()
        timTerminal.timLogin(account!!)
    }
}