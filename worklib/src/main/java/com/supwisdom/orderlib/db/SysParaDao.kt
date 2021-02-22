package com.supwisdom.orderlib.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.supwisdom.orderlib.entity.SysParaRecord
import java.util.concurrent.locks.Lock

/**
 * @desc  系统参数
 */
internal class SysParaDao constructor(context: Context) {
    private val dbHelper = ParaDBHelper.getInstance(context)
    private val TABLE = ParaDBHelper.TABLE_NAME_SYSPARA

    fun getLock(): Lock {
        return dbHelper.getLock()
    }

    fun replace(record: SysParaRecord): Boolean {
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

    fun get(paraName: String): SysParaRecord? {
        val db = dbHelper.readableDatabase
        var cursor: Cursor? = null
        val selection = BeanPropEnum.AppControl.paraname.toString() + "=?"
        val selectionArgs = arrayOf(paraName)
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

    private fun getContentValues(record: SysParaRecord): ContentValues {
        val values = ContentValues()
        values.put(BeanPropEnum.AppControl.paraname.toString(), record.paraName)
        values.put(BeanPropEnum.AppControl.paravalue.toString(), record.paraValue)
        return values
    }

    private fun getRecord(cursor: Cursor): SysParaRecord {
        val record = SysParaRecord()
        record.paraName = cursor.getString(cursor.getColumnIndex(BeanPropEnum.AppControl.paraname.toString()))
        record.paraValue = cursor.getString(cursor.getColumnIndex(BeanPropEnum.AppControl.paravalue.toString()))
        return record
    }
}