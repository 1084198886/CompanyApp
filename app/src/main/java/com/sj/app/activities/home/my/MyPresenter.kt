package com.sj.app.activities.home.my

import android.text.TextUtils
import com.sj.app.activities.SPApplication
import com.supwisdom.commonlib.execption.TerminalInitError
import com.supwisdom.commonlib.utils.GsonUtil
import com.supwisdom.commonlib.utils.LogUtil
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc
 */
class MyPresenter(private val myView: MyView) {
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    fun getMyInfo() {
        ThreadPool.getShortPool().execute {
            try {
                val dyRecord = phoneTerminal.getDynamicPara()
                if (dyRecord != null) {
                    myView.getMyActivity().runOnUiThread {
                        myView.showMyInfo(dyRecord)
                    }
                }
            } catch (ex: TerminalInitError) {
            }
        }
    }

    fun getTagList(tagList: ArrayList<String>, tags: String?): List<String>? {
        if (!TextUtils.isEmpty(tags)) {
            try {
                val tagBeanList = GsonUtil.GsonToList(tags, String::class.java)
                if (tagBeanList != null) {
                    tagList.addAll(tagBeanList)
                }
            } catch (ex: Exception) {
            }
        }
        return null
    }

}