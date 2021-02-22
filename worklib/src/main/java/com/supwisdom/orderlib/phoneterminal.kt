package com.supwisdom.orderlib

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.supwisdom.commonlib.bean.LocateCity
import com.supwisdom.commonlib.execption.*
import com.supwisdom.commonlib.utils.LogUtil
import com.supwisdom.orderlib.bean.ApplyForGroup
import com.supwisdom.orderlib.bean.ExamineData
import com.supwisdom.orderlib.bean.MyDynamic
import com.supwisdom.orderlib.bean.MyStart
import com.supwisdom.orderlib.entity.*
import com.supwisdom.orderlib.http.PhoneSession
import com.supwisdom.orderlib.service.AppAuthApi
import com.supwisdom.orderlib.service.AppParaApi

/**
 * @desc  对外接口
 */
class PhoneTerminal private constructor(private val context: Context) {
    private val TAG = "PhoneTerminal"
    private val pos = Phone.getInstance()
    private val authApi = AppAuthApi()
    private val paraApi = AppParaApi()
    var locatedCity: LocateCity? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: PhoneTerminal? = null

        fun getInstance(context: Context): PhoneTerminal {
            if (INSTANCE == null) {
                synchronized(PhoneTerminal::class) {
                    if (INSTANCE == null) {
                        Phone.context = context
                        INSTANCE = PhoneTerminal(context)
                    }
                }
            }
            return INSTANCE!!
        }
    }

    init {
        initAMapLocation()
    }

    /**
     * @throws TerminalConfigError
     * @desc 加载通讯参数
     */
    fun loadConfig() {
        val cfgRecord = pos.getConfigPara() ?: throw TerminalConfigError("未注册")
        PhoneSession.instance.setSession(cfgRecord)
        if (!cfgRecord.isInitOK) {
            throw TerminalConfigError("未注册")
        }
        val dyRecord = pos.getDynamicPara() ?: throw TerminalConfigError("未注册")
        PhoneSession.instance.setSessionToken(dyRecord.token!!)
    }

    private var viewRefreshListener: ViewRefreshListener? = null

    fun setViewRefreshListener(listener: ViewRefreshListener) {
        viewRefreshListener = listener
    }

    abstract class ViewRefreshListener {
        abstract fun onLocationChanged(locatedCity: LocateCity)
        abstract fun onLocateError()
    }

    var mLocationClient: AMapLocationClient? = null
    private fun initAMapLocation() {
        mLocationClient = AMapLocationClient(context)
        val mLocationOption = AMapLocationClientOption()
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving
        mLocationOption.isOnceLocation = true
        mLocationOption.isNeedAddress = true
        mLocationOption.httpTimeOut = 15000
        mLocationOption.isLocationCacheEnable = false

        mLocationClient!!.setLocationOption(mLocationOption)
        mLocationClient!!.setLocationListener { amapLocation ->
            if (amapLocation != null) {
                when (amapLocation.errorCode) {
                    AMapLocation.LOCATION_SUCCESS -> {
                        LogUtil.d(
                            TAG,
                            "locate_success:${amapLocation.province},${amapLocation.city},${amapLocation.cityCode}"
                        )
                        val city = LocateCity(amapLocation.city, amapLocation.province, amapLocation.cityCode)
                        viewRefreshListener?.onLocationChanged(city)
                        stopLocation()
                    }
                    else -> {
                        LogUtil.e(
                            TAG, "location Error, ErrCode" + amapLocation.errorCode + ", errInfo:"
                                    + amapLocation.errorInfo
                        )
                        viewRefreshListener?.onLocateError()
                    }
                }
            }
        }
    }

    fun startLocation() {
        mLocationClient?.startLocation()
    }

    fun stopLocation() {
        mLocationClient?.stopLocation()
    }

    /**
     * 销毁定位客户端，同时销毁本地定位服务
     */
    fun destroyAMapClient() {
        mLocationClient?.onDestroy()
    }

    /**
     * @desc 保存通讯参数
     */
    fun saveConfig(record: ConfigParaRecord) {
        if (!pos.replaceConfigPara(record)) {
            throw TerminalConfigError("保存点餐配置参数失败")
        }
        PhoneSession.instance.setSession(record)
    }

    fun getConfig(): ConfigParaRecord? {
        return pos.getConfigPara()
    }

    fun getDynamicPara(): DynamicParaRecord? {
        return pos.getDynamicPara()
    }

    fun getAllCity(): List<CityParaRecord>? {
        return pos.getAllCity()
    }

    fun getAllCity(cityList: List<CityParaRecord>): Boolean {
        return pos.insertCity(cityList)
    }

    /**
     * 获取验证码
     */
    fun getVerifyNo(account: String) {
        paraApi.getVerifyNo(account)
    }

    fun regist(phone: String, verifyNo: String, pwd: String) {
        authApi.regist(phone, verifyNo, pwd)
    }

    fun login(account: String, password: String) {
        authApi.login(account, password)
    }

    fun modifyPwd(account: String, verifyNo: String, newPwd: String) {
        authApi.modifyPwd(account, verifyNo, newPwd)
    }

    fun bindPhone(phone: String, verifyNo: String) {
        authApi.bindPhone(phone, verifyNo)
    }

    fun realNameAuth() {
        authApi.realNameAuth()
    }

    fun saveCustInfo(petName: String, sex: String, age: String, headerPhoto: Bitmap) {
        paraApi.saveCustInfo(petName, sex, age, headerPhoto)
    }

    fun saveCustLabel(labels: String) {
        paraApi.saveCustLabel(labels)
    }

    fun getUserInfo(userId: String) {
    }

    fun getSettings(userId: String) {
    }

    fun feedBack(advice: String) {
        paraApi.feedBack(advice)
    }

    fun loginOut() {
    }

    fun saveRecvLetterSwitch(state: String): Boolean {
//        paraApi.saveRecvLetterSwitch(state)
        return true
    }

    fun getAdviceData(): List<String>? {
        return paraApi.getAdviceData()
    }

    fun getGroupMyJoin() {
        return paraApi.getGroupMyJoin()
    }

    fun getGroupMyStart(): ArrayList<MyStart>? {
        return paraApi.getGroupMyStart()
    }

    fun getExamineActivities(): List<ExamineData>? {
        return paraApi.getExamineActivities()
    }

    fun getCollectAttentions() {
        return paraApi.getCollectAttentions()
    }

    fun deleteGroupMyStart() {
        paraApi.deleteGroupMyStart()
    }

    fun getActivityDetails() {
        paraApi.getActivityDetails()
    }

    fun getAdviceMyJoin(): ArrayList<MyStart>? {
        return paraApi.getAdviceMyJoin()
    }

    fun getApplyForGroup(): ArrayList<ApplyForGroup>? {
        return paraApi.getApplyForGroup()
    }

    fun getMyDynamic(): ArrayList<MyDynamic>? {
        return paraApi.getMyDynamic()
    }

    fun publishDynamic() {
        paraApi.publishDynamic()
    }

    fun getMyPublishGroup() {
        paraApi.getMyPublishGroup()
    }
}