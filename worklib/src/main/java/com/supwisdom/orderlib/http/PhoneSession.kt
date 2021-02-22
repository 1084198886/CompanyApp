package com.supwisdom.orderlib.http

import com.supwisdom.commonlib.okhttp.NetworkHandler
import com.supwisdom.commonlib.okhttp.TransResp
import com.supwisdom.commonlib.utils.LogUtil
import com.supwisdom.orderlib.entity.ConfigParaRecord
import com.supwisdom.orderlib.utils.MD5
import okhttp3.MultipartBody

/**
 * @desc  通讯
 */
internal class PhoneSession {
    private val TAG = "PhoneSession"

    companion object {
        private val session: SessionAttribute by lazy { SessionAttribute() }
        val instance: PhoneSession by lazy { PhoneSession() }
    }

    fun setSession(record: ConfigParaRecord) {
        val sb = StringBuilder()
        session.baseurl = sb.toString()
    }

    fun setSessionToken(devkey: String) {
        session.orderKey = devkey
    }

    private fun sendRequestPost(uri: String, json: String): TransResp? {
        val url = session.baseurl + uri + "?sign=" + getMD5Sign(json)
        LogUtil.i(TAG, "请求url:$url,json:$json")
        val resp = NetworkHandler.getInstance().post(url, json)
        if (resp != null) {
            LogUtil.i(TAG, "响应-retjson:${resp.retjson}")
        } else {
            LogUtil.e(TAG, "响应为空")
        }
        return resp
    }

    /**
     * @param uri
     * @param params  post参数
     * @param multiBuilder  附件
     */
    fun sendRequestPost(uri: String, params: Map<String, String>, multiBuilder: MultipartBody.Builder?): TransResp? {
        val url = session.baseurl + uri
        return NetworkHandler.getInstance().post(url, params, session.token, multiBuilder)
    }

    fun sendPicRequestPost(url: String): TransResp? {
        return NetworkHandler.getInstance().post(url)
    }

    @Throws(Exception::class)
    private fun getMD5Sign(senddata: String): String? {
        val md5Value = session.orderKey + senddata
        return MD5.encodeByMD5(md5Value)
    }
}