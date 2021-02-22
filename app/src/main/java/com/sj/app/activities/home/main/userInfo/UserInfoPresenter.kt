package com.sj.app.activities.home.main.userInfo

import android.text.TextUtils
import com.sj.app.activities.SPApplication
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc  用户信息
 */
class UserInfoPresenter(private val userInfoView: UserInfoView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun getUserInfo(userId: String?) {
        if (TextUtils.isEmpty(userId)) {
            return userInfoView.showToast("获取失败")
        }
        userInfoView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                phoneTerminal.getUserInfo(userId!!)
                userInfoView.getActivity().runOnUiThread {
                    userInfoView.showUserInfo()
                }
            } catch (ex: TerminalInitError) {
                userInfoView.getActivity().runOnUiThread {
                    userInfoView.showToast(ex.message!!)
                }
            } finally {
                userInfoView.getActivity().runOnUiThread {
                    userInfoView.closeProgressDialog()
                }
            }
        }
    }

}