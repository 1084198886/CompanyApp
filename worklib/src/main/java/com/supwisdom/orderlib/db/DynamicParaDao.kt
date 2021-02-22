package com.supwisdom.orderlib.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.supwisdom.orderlib.entity.DynamicParaRecord
import java.util.concurrent.locks.Lock

/**
 * @desc  动态参数
 */
internal class DynamicParaDao constructor(context: Context) {
    private val INDEX = 1
    private val dbHelper = ParaDBHelper.getInstance(context)
    private val TABLE = ParaDBHelper.TABLE_NAME_DYNAMIC

    fun getLock(): Lock {
        return dbHelper.getLock()
    }

    fun replace(record: DynamicParaRecord): Boolean {
        val db = dbHelper.writableDatabase
        try {
            db.beginTransaction()
            val values = getContentValues(record)
            if (db.replace(TABLE, null, values) > 0) {
                db.setTransactionSuccessful()
                return true
            }
            return false
        } finally {
            db.endTransaction()
        }
    }

    fun get(): DynamicParaRecord? {
        val db = dbHelper.readableDatabase
        var cursor: Cursor? = null
        val selection = BeanPropEnum.AppDynamic.id.toString() + "=?"
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

    private fun getContentValues(record: DynamicParaRecord): ContentValues {
        val values = ContentValues()
        values.put(BeanPropEnum.AppDynamic.id.toString(), INDEX)
        values.put(BeanPropEnum.AppDynamic.userid.toString(), record.userid)
        values.put(BeanPropEnum.AppDynamic.headphoto.toString(), record.headphoto)
        values.put(BeanPropEnum.AppDynamic.petname.toString(), record.petname)
        values.put(BeanPropEnum.AppDynamic.signature.toString(), record.signature)
        values.put(BeanPropEnum.AppDynamic.city.toString(), record.city)
        values.put(BeanPropEnum.AppDynamic.constellation.toString(), record.constellation)
        values.put(BeanPropEnum.AppDynamic.tags.toString(), record.tags)
        values.put(BeanPropEnum.AppDynamic.age.toString(), record.age)
        values.put(BeanPropEnum.AppDynamic.sex.toString(), record.sex)
        values.put(BeanPropEnum.AppDynamic.token.toString(), record.token)
        return values
    }

    private fun getRecord(cursor: Cursor): DynamicParaRecord {
        val record = DynamicParaRecord()
        record.userid = cursor.getString(cursor.getColumnIndex(BeanPropEnum.AppDynamic.userid.toString()))
        record.headphoto= cursor.getString(cursor.getColumnIndex(BeanPropEnum.AppDynamic.headphoto.toString()))
        record.signature= cursor.getString(cursor.getColumnIndex(BeanPropEnum.AppDynamic.signature.toString()))
        record.petname = cursor.getString(cursor.getColumnIndex(BeanPropEnum.AppDynamic.petname.toString()))
        record.city = cursor.getString(cursor.getColumnIndex(BeanPropEnum.AppDynamic.city.toString()))
        record.constellation = cursor.getString(cursor.getColumnIndex(BeanPropEnum.AppDynamic.constellation.toString()))
        record.tags = cursor.getString(cursor.getColumnIndex(BeanPropEnum.AppDynamic.tags.toString()))
        record.age = cursor.getInt(cursor.getColumnIndex(BeanPropEnum.AppDynamic.age.toString()))
        record.sex= cursor.getString(cursor.getColumnIndex(BeanPropEnum.AppDynamic.sex.toString()))
        record.token = cursor.getString(cursor.getColumnIndex(BeanPropEnum.AppDynamic.token.toString()))
        return record
    }
}