package com.supwisdom.orderlib.db

import android.content.Context
import com.supwisdom.orderlib.Phone
import com.supwisdom.orderlib.entity.*

/**
 * @desc  数据库操作方法
 */
internal class WorkPhone constructor(context: Context) {
    private var context: Context? = null
    private var sdContext: SDContext? = null
    private var orderConfigParaDao: ConfigParaDao

    private var orderConfigParaRecord: ConfigParaRecord? = null
    private var orderDynamicParaDao: DynamicParaDao
    private var orderDynamicParaRecord: DynamicParaRecord? = null
    private var orderSysParaDao: SysParaDao
    private var cityParaDao: CityParaDao? = null

    /**
     * 控制参数<name,bean>
     */
    private val orderControlParaMap = HashMap<String, SysParaRecord>()

    init {
        this.context = context
        this.sdContext = SDContext(context)
        /**
         * 存储在APK中
         */
        orderConfigParaDao = ConfigParaDao(context)
        orderDynamicParaDao = DynamicParaDao(context)
        orderSysParaDao = SysParaDao(context)
        cityParaDao = CityParaDao(Phone.context!!)
        /**
         * 存储在SD卡
         */
    }

    fun getConfigPara(): ConfigParaRecord? {
        if (orderConfigParaRecord == null) {
            try {
                orderConfigParaDao.getLock().lock()
                orderConfigParaRecord = orderConfigParaDao.get()
            } finally {
                orderConfigParaDao.getLock().unlock()
            }
        }
        return orderConfigParaRecord
    }

    fun replaceConfigPara(record: ConfigParaRecord): Boolean {
        try {
            orderConfigParaDao.getLock().lock()
            if (orderConfigParaDao.replace(record)) {
                orderConfigParaRecord = record
                return true
            }
        } finally {
            orderConfigParaDao.getLock().unlock()
        }
        return false
    }

    fun clearConfigPara(): Boolean {
        try {
            orderConfigParaDao.getLock().lock()
            return orderConfigParaDao.clear()
        } finally {
            orderConfigParaDao.getLock().unlock()
            orderConfigParaRecord = null
        }
    }


    fun getDynamicPara(): DynamicParaRecord? {
        if (orderDynamicParaRecord == null) {
            try {
                orderDynamicParaDao.getLock().lock()
                orderDynamicParaRecord = orderDynamicParaDao.get()
            } finally {
                orderDynamicParaDao.getLock().unlock()
            }
        }
        return orderDynamicParaRecord
    }

    fun clearDynamicPara(): Boolean {
        try {
            orderDynamicParaDao.getLock().lock()
            return orderDynamicParaDao.clear()
        } finally {
            orderDynamicParaDao.getLock().unlock()
            orderDynamicParaRecord = null
        }
    }

    fun replaceDynamicPara(record: DynamicParaRecord): Boolean {
        try {
            orderDynamicParaDao.getLock().lock()
            if (orderDynamicParaDao.replace(record)) {
                orderDynamicParaRecord = record
                return true
            }
        } finally {
            orderDynamicParaDao.getLock().unlock()
        }
        return false
    }

    fun getControlPara(paraName: String): SysParaRecord? {
        if (orderControlParaMap.containsKey(paraName)) {
            return orderControlParaMap[paraName]
        }
        try {
            orderSysParaDao.getLock().lock()
            val record = orderSysParaDao.get(paraName)
            if (record != null) {
                orderControlParaMap[paraName] = record
            }
            return record
        } finally {
            orderSysParaDao.getLock().unlock()
        }
    }

    fun replaceControlPara(record: SysParaRecord): Boolean {
        try {
            orderSysParaDao.getLock().lock()
            if (orderSysParaDao.replace(record)) {
                orderControlParaMap[record.paraName!!] = record
                return true
            }
        } finally {
            orderSysParaDao.getLock().unlock()
        }
        return false
    }

    fun getAllCity(): List<CityParaRecord>? {
        cityParaDao!!.getLock().lock()
        try {
            return cityParaDao!!.getAll()
        } finally {
            cityParaDao!!.getLock().unlock()
        }
    }

    fun insertCity(cityList: List<CityParaRecord>): Boolean {
        cityParaDao!!.getLock().lock()
        try {
            return cityParaDao!!.replace(cityList)
        } finally {
            cityParaDao!!.getLock().unlock()
        }
    }
}