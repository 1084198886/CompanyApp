package com.supwisdom.orderlib.entity

/**
 * @desc  系统参数
 */
class SysParaRecord {
    var paraName: String? = null
    var paraValue: String? = null
}

enum class SysPara {
    /**
     * 人脸识别比对得分
     */
    FACE_COMPSCORE,
    /**
     * 人脸取餐是否启用(1--是，0--否)
     */
    FACE_TAKEMEAL_ENABLE
}