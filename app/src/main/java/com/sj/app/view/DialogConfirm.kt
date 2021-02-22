package com.sj.app.view

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.sj.app.R
import com.supwisdom.commonlib.utils.CommonUtil

/**
 * @author gqy
 * @date 2019/8/9
 * @version 1.0.0
 * @desc  确认框
 */
class DialogConfirm constructor(context: Context) : DialogBase(context) {
    private var vMsg: TextView
    var callBack: IConfirmCallBack? = null

    init {
        setContentView(R.layout.dialog_confirm)
        vMsg = findViewById<TextView>(R.id.v_msg)

        findViewById<View>(R.id.v_cancel).setOnClickListener {
            dismiss()
        }
        findViewById<View>(R.id.v_ok).setOnClickListener {
            dismiss()
            callBack?.clickOk()
        }
        this.window!!.attributes.gravity = Gravity.CENTER
        this.window!!.attributes.width = CommonUtil.getScreenWith() * 2 / 3

        setCanceledOnTouchOutside(true)
        this.setCancelable(true)
    }

    fun setMessage(msg: String): DialogConfirm {
        vMsg.text = msg
        return this
    }

    interface IConfirmCallBack {
        fun clickOk()
    }
}