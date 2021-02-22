package com.supwisdom.orderlib.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * @desc  参数数据库
 */
internal class ParaDBHelper private constructor(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, VERSION) {
    companion object {
        private const val DB_NAME = "local_para_db"
        private const val VERSION = 1
        /**
         * 本地配置文件
         */
        const val TABLE_NAME_CONFIG = "tb_para_config"
        /**
         * 本地城市
         */
        const val TABLE_NAME_CITY = "tb_para_city"
        /**
         * 动态参数
         */
        const val TABLE_NAME_DYNAMIC = "tb_para_dynamic"
        /**
         * 设备控制参数
         */
        const val TABLE_NAME_SYSPARA = "tb_para_control"

        private var mInstance: ParaDBHelper? = null
        fun getInstance(context: Context): ParaDBHelper {
            if (mInstance == null) {
                synchronized(ParaDBHelper::class.java) {
                    if (mInstance == null) {
                        mInstance = ParaDBHelper(context)
                    }
                }
            }
            return mInstance!!
        }
    }

    private val CREATE_TABLE_NAME_CONFIG = ("create table IF NOT EXISTS "
            + TABLE_NAME_CONFIG + " ( "
            + BeanPropEnum.AppConfig.id + " integer primary key,"
            + BeanPropEnum.AppConfig.account + " varchar(11),"
            + BeanPropEnum.AppConfig.serverurl + " varchar(48),"
            + BeanPropEnum.AppConfig.initOK + " integer )")

    private val CREATE_TABLE_NAME_CITY = ("create table IF NOT EXISTS "
            + TABLE_NAME_CITY + " ( "
            + BeanPropEnum.City.code + " varchar(10) primary key,"
            + BeanPropEnum.City.cityname + " varchar(48),"
            + BeanPropEnum.City.province + " varchar(24) )")

    private val CREATE_TABLE_NAME_DYNAMIC = ("create table IF NOT EXISTS "
            + TABLE_NAME_DYNAMIC + " ( "
            + BeanPropEnum.AppDynamic.id + " integer primary key,"
            + BeanPropEnum.AppDynamic.userid + " varchar(11),"
            + BeanPropEnum.AppDynamic.headphoto + " varchar(254),"
            + BeanPropEnum.AppDynamic.signature + " varchar(56),"
            + BeanPropEnum.AppDynamic.petname + " varchar(32),"
            + BeanPropEnum.AppDynamic.city + " varchar(32),"
            + BeanPropEnum.AppDynamic.age + " integer,"
            + BeanPropEnum.AppDynamic.constellation + " char(24),"
            + BeanPropEnum.AppDynamic.token + " varchar(32),"
            + BeanPropEnum.AppDynamic.sex + " char(1),"
            + BeanPropEnum.AppDynamic.tags + " varchar(254) )")

    private val CREATE_TABLE_NAME_SYSPARA = ("create table IF NOT EXISTS "
            + TABLE_NAME_SYSPARA + "("
            + BeanPropEnum.AppControl.paraname + " varchar(64) primary key,"
            + BeanPropEnum.AppControl.paravalue + " varchar(254) )")

    private val lock = ReentrantLock()
    fun getLock(): Lock {
        return lock
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_NAME_CONFIG)
        db.execSQL(CREATE_TABLE_NAME_CITY)
        db.execSQL(CREATE_TABLE_NAME_DYNAMIC)
        db.execSQL(CREATE_TABLE_NAME_SYSPARA)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
        }
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}