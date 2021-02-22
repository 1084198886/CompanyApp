package com.supwisdom.orderlib.db

/**
 * @desc  数据库存储元素
 */
internal class BeanPropEnum {

    enum class AppDynamic {
        id,
        userid,
        headphoto,
        signature,
        petname,
        city,
        constellation,
        tags,
        token,
        sex,
        age
    }

    enum class AppSysPara {
        id,
        paraname,
        paravalue
    }

    enum class AppConfig {
        id,
        account,
        serverurl,
        initOK
    }

    enum class AppControl {
        paraname,
        paravalue
    }

    enum class City {
        code,
        cityname,
        province
    }

}