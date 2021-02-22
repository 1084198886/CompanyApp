package com.supwisdom.orderlib.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.supwisdom.orderlib.entity.ConfigParaRecord
import java.util.concurrent.locks.Lock

/**
 * @desc  通讯参数
 */
internal class ConfigParaDao constructor(context: Context) {
    private val INDEX = 1
    private val dbHelper = ParaDBHelper.getInstance(context)
    private val TABLE = ParaDBHelper.TABLE_NAME_CONFIG

    fun getLock(): Lock {
        return dbHelper.getLock()
    }

    fun replace(record: ConfigParaRecord): Boolean {
        val db = dbHelper.writableDatabase
        try {
            db.beginTransaction()
            val values = getContentValues(record)
            if (db.replace(TABLE, null, values) > 0) {
                db.setTransactionSuccessful()
                return true
            }
        } finally {
            db.endTransaction()
        }
        return false
    }

    fun get(): ConfigParaRecord? {
        val db = dbHelper.readableDatabase
        var cursor: Cursor? = null
        val selection = BeanPropEnum.AppConfig.id.toString() + "=?"
        val selectionArgs = arrayOf(INDEX.toString())
        try {
            cursor = db.query(TABLE, null, selection, selectionArgs, null, null, null)
            if (cursor != null && cursor.moveToNext()) {
                return getRecord(cursor)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    fun clear(): Boolean {
        val db = dbHelper.writableDatabase
        try {
            db.beginTransaction()
            if (db.delete(TABLE, null, null) >= 0) {
                db.setTransactionSuccessful()
                return true
            }
        } finally {
            db.endTransaction()
        }
        return false
    }

    private fun getContentValues(record: ConfigParaRecord): ContentValues {
        val values = ContentValues()
        values.put(BeanPropEnum.AppConfig.id.toString(), INDEX)
        values.put(BeanPropEnum.AppConfig.account.toString(), record.account)
        values.put(BeanPropEnum.AppConfig.serverurl.toString(), record.serverurl)
        if (record.isInitOK) {
            values.put(BeanPropEnum.AppConfig.initOK.toString(), 1)
        } else {
            values.put(BeanPropEnum.AppConfig.initOK.toString(), 0)
        }
        return values
    }

    private fun getRecord(cursor: Cursor): ConfigParaRecord {
        val record = ConfigParaRecord()
        record.account = cursor.getString(cursor.getColumnIndex(BeanPropEnum.AppConfig.account.toString()))
        record.serverurl = cursor.getString(cursor.getColumnIndex(BeanPropEnum.AppConfig.serverurl.toString()))
        val initOK = cursor.getInt(cursor.getColumnIndex(BeanPropEnum.AppConfig.initOK.toString()))
        record.isInitOK = initOK == 1
        return record
    }
}