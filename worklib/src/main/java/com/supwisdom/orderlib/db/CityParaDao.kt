package com.supwisdom.orderlib.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.supwisdom.orderlib.entity.CityParaRecord
import java.util.concurrent.locks.Lock

/**
 * @desc  城市参数
 */
internal class CityParaDao constructor(context: Context) {
    private val INDEX = 1
    private val dbHelper = ParaDBHelper.getInstance(context)
    private val TABLE = ParaDBHelper.TABLE_NAME_CITY

    fun getLock(): Lock {
        return dbHelper.getLock()
    }

    fun replace(records: List<CityParaRecord>): Boolean {
        val db = dbHelper.writableDatabase
        try {
            db.beginTransaction()
            records.forEach {
                if (db.replace(TABLE, null, getContentValues(it)) <= 0) {
                    return false
                }
            }
            db.setTransactionSuccessful()
            return true
        } finally {
            db.endTransaction()
        }
    }

    fun getAll(): List<CityParaRecord>? {
        val db = dbHelper.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.query(TABLE, null, null, null, null, null, null)
            if (cursor != null) {
                val list = arrayListOf<CityParaRecord>()
                while (cursor.moveToNext()) {
                    list.add(getRecord(cursor))
                }
                return list
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

    private fun getContentValues(record: CityParaRecord): ContentValues {
        val values = ContentValues()
        values.put(BeanPropEnum.City.code.toString(), record.code)
        values.put(BeanPropEnum.City.cityname.toString(), record.cityname)
        values.put(BeanPropEnum.City.province.toString(), record.province)
        return values
    }

    private fun getRecord(cursor: Cursor): CityParaRecord {
        val record = CityParaRecord()
        record.code = cursor.getString(cursor.getColumnIndex(BeanPropEnum.City.code.toString()))
        record.cityname = cursor.getString(cursor.getColumnIndex(BeanPropEnum.City.cityname.toString()))
        record.province = cursor.getString(cursor.getColumnIndex(BeanPropEnum.City.province.toString()))
        return record
    }
}