package com.sj.timlib

import android.annotation.SuppressLint
import android.content.Context
import com.sj.timlib.utils.ConfigHelper
import com.sj.timlib.utils.GenerateTestUserSig
import com.sj.timlib.utils.PublicDef
import com.supwisdom.commonlib.utils.GsonUtil
import com.supwisdom.commonlib.utils.LogUtil
import com.tencent.imsdk.*
import com.tencent.imsdk.session.SessionWrapper
import com.tencent.qcloud.tim.uikit.TUIKit
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack
import com.tencent.imsdk.TIMTextElem



/**
 * @desc  对外接口
 */
class TIMTerminal private constructor(private val context: Context) {
    private val TAG = "TIMTerminal"
    private val tim = TIM.getInstance()

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: TIMTerminal? = null

        fun getInstance(context: Context): TIMTerminal {
            if (INSTANCE == null) {
                synchronized(TIMTerminal::class) {
                    if (INSTANCE == null) {
                        TIM.context = context
                        INSTANCE = TIMTerminal(context)
                    }
                }
            }
            return INSTANCE!!
        }
    }

    fun isMainProcess(): Boolean {
        return SessionWrapper.isMainProcess(context)
    }

    fun initUIKit() {
        TUIKit.init(context, PublicDef.SdkAppID, ConfigHelper().configs)
    }

    /**
     * 应用切到前台
     */
    fun doForeground() {
        LogUtil.i(TAG, "应用切到前台")

        TIMManager.getInstance().doForeground(object : TIMCallBack {
            override fun onError(code: Int, desc: String) {
                LogUtil.e(TAG, "doForeground err = $code, desc = $desc")
            }

            override fun onSuccess() {
                LogUtil.i(TAG, "doForeground success")
            }
        })
    }

    /**
     * 应用切到后台
     */
    fun doBackground() {
        LogUtil.i(TAG, "应用切到后台")
        var unReadCount = 0
        val conversationList = TIMManager.getInstance().conversationList
        for (timConversation in conversationList) {
            unReadCount += timConversation.unreadMessageNum.toInt()
        }
        val param = TIMBackgroundParam()
        param.c2cUnread = unReadCount
        TIMManager.getInstance().doBackground(param, object : TIMCallBack {
            override fun onError(code: Int, desc: String) {
                LogUtil.e(TAG, "doBackground err = $code, desc = $desc")
            }

            override fun onSuccess() {
                LogUtil.i(TAG, "doBackground success")
            }
        })
    }

    fun timLogin(account: String) {
        val userSig = GenerateTestUserSig.genTestUserSig(account)
        LogUtil.i(TAG, "timLogin start : $account,userSig:$userSig")

        TUIKit.login(account, userSig, object : IUIKitCallBack {
            override fun onSuccess(data: Any?) {
                LogUtil.i(TAG, "timLogin 登录成功,data:${GsonUtil.GsonString(data)}")

                val loginUser = TIMManager.getInstance().loginUser
                LogUtil.e(TAG, "loginUser $loginUser")

                val tIMConversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, "18737165713")
                val message = TIMMessage()
                //添加文本内容
                val elem = TIMTextElem()
                elem.text = "a new msg"
                message.addElement(elem)

                tIMConversation.sendMessage(message, object : TIMValueCallBack<TIMMessage> {
                    override fun onSuccess(message: TIMMessage?) {
                        LogUtil.i(TAG, "发送消息onSuccess ${GsonUtil.GsonString(message)}")
                    }

                    override fun onError(p0: Int, p1: String?) {
                        LogUtil.i(TAG, "发送消息onError ,p0=$p0,p1:$p1")
                    }

                })
                val conversationList = TIMManager.getInstance().conversationList
                LogUtil.e(TAG, "conversationList ${GsonUtil.GsonString(conversationList)}")
            }

            override fun onError(module: String?, errCode: Int, errMsg: String?) {
                LogUtil.i(TAG, "timLogin module:$module, errorCode = $errCode, errorInfo = $errMsg")
            }
        })
    }
}