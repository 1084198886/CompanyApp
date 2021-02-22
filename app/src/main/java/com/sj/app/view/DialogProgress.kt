package com.sj.app.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.widget.TextView
import com.sj.app.R

/**
 * @author gqy
 * @date 2019/8/9
 * @version 1.0.1
 * @desc  事件进度提示
 */
class DialogProgress constructor(context: Context) : DialogBase(context) {
    private var tvMsg: TextView?

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: DialogProgress? = null

        fun getInstance(context: Context): DialogProgress {
            if (INSTANCE == null) {
                synchronized(DialogProgress::class) {
                    if (INSTANCE == null) {
                        INSTANCE = DialogProgress(context)
                    }
                }
            }
            return INSTANCE!!
        }
    }

    init {
        setContentView(R.layout.dialog_process)
        this.window!!.attributes.gravity = Gravity.CENTER
        tvMsg = this.findViewById(R.id.loadmsg) as TextView
        setCanceledOnTouchOutside(false)
        this.setCancelable(true)
    }

    fun setMessage(msg: String): DialogProgress {
        if (tvMsg != null) {
            tvMsg!!.text = msg
        }
        return this
    }
}