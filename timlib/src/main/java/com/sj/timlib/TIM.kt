package com.sj.timlib

import android.annotation.SuppressLint
import android.content.Context
import com.sj.timlib.db.TIMWorker
/**
 * @desc  数据库句柄
 */
@SuppressLint("StaticFieldLeak")
internal object TIM {
    var context: Context? = null
    private var INSTANCE: TIMWorker? = null

    fun getInstance(): TIMWorker {
        if (INSTANCE == null) {
            synchronized(TIM::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = TIMWorker(context!!)
                }
            }
        }
        return INSTANCE!!
    }
}
