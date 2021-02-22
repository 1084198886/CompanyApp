package com.supwisdom.orderlib

import android.annotation.SuppressLint
import android.content.Context
import com.supwisdom.orderlib.db.WorkPhone
/**
 * @desc  数据库句柄
 */
@SuppressLint("StaticFieldLeak")
internal object Phone {
    var context: Context? = null
    private var INSTANCE: WorkPhone? = null

    fun getInstance(): WorkPhone {
        if (INSTANCE == null) {
            synchronized(Phone::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = WorkPhone(context!!)
                }
            }
        }
        return INSTANCE!!
    }
}
