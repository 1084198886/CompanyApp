package com.supwisdom.orderlib.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * 持久化数据库
 */
internal class PersistDBHelper private constructor(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, VERSION) {
    companion object {
        private const val DB_NAME = "local_persist_db"
        private const val VERSION = 1

        private var mInstance: PersistDBHelper? = null
        fun getInstance(context: Context): PersistDBHelper {
            if (mInstance == null) {
                synchronized(PersistDBHelper::class.java) {
                    if (mInstance == null) {
                        mInstance = PersistDBHelper(context)
                    }
                }
            }
            return mInstance!!
        }
    }


    private val lock = ReentrantLock()
    fun getLock(): Lock {
        return lock
    }

    override fun onCreate(db: SQLiteDatabase) {
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
        }
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}