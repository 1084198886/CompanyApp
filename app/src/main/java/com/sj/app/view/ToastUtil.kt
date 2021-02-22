package com.sj.app.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.sj.app.R

/**
 * @desc  界面操作提示
 */
@SuppressLint("StaticFieldLeak")
object ToastUtil {
    private var toast1: Toast? = null
    private var toastText1: TextView? = null

    fun show(context: Context, msg: String) {
        if (toast1 == null) {
            toast1 = Toast(context)
            val view = LayoutInflater.from(context).inflate(R.layout.util_toast, null)
            toastText1 = view.findViewById(R.id.toasttext) as TextView
            toast1!!.setGravity(Gravity.CENTER, 0, 0)
            toast1!!.duration = Toast.LENGTH_SHORT
            toast1!!.view = view
        }
        toastText1!!.text = msg
        toast1!!.show()
    }
}