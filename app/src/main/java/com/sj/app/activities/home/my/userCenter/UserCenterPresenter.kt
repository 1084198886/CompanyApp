package com.sj.app.activities.home.my.userCenter

import android.graphics.Bitmap
import android.text.TextUtils
import com.sj.app.activities.SPApplication
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.GsonUtil
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc
 */
class UserCenterPresenter(private val perfectInfoView: UserCenterView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun getUserInfo() {
        perfectInfoView.showProgressDialog("获取中...")
        ThreadPool.getShortPool().execute {
            try {
                val dyRecord = phoneTerminal.getDynamicPara()
                perfectInfoView.getActivity().runOnUiThread {
//                    perfectInfoView.showUserInfo(dyRecord!!)
                }
            } catch (ex: TerminalInitError) {
                perfectInfoView.getActivity().runOnUiThread {
                    perfectInfoView.showToast(ex.message!!)
                }
            } finally {
                perfectInfoView.getActivity().runOnUiThread {
                    perfectInfoView.closeProgressDialog()
                }
            }
        }
    }

    fun getTagList(tags: String?): List<String>? {
        if (!TextUtils.isEmpty(tags)) {
            try {
                return GsonUtil.GsonToList(tags, String::class.java)
            } catch (ex: Exception) {
            }
        }
        return null
    }
}