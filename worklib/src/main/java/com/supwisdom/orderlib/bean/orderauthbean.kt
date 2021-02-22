package com.supwisdom.orderlib.bean

/**
 * @desc  签到数据
 */
internal data class OrderAuthReqBean(val phyid: String, val devicetime: String)

/**
 * @param imgurl 图片服务器地址
 * @param  heartgap 心跳间隔
 */
internal class OrderAuthRetBean constructor(retcode: Int, retmsg: String) : BaseResp(retcode, retmsg) {
    //    var rtntime: String? = null
    var deviceid: Int = 0
    var token: String? = null
    var devicename: String? = null
    var shopid: Int = 0
    var shopname: String? = null
    var windowid: String? = null
    var windowname: String? = null
    //    var phyid: String? = null
    var version: Int = 0
    var imgurl: String? = null
    var heartgap: Int = 0
    //    var systemdate: String? = null
    var paraverno: Int = 0
    var paragroupid: Int = 0
}