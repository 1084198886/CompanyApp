package com.sj.app.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import com.sj.app.R

/**
 * @author gqy
 * @date 2019/8/9
 * @desc 基类dialog
 */
open class DialogBase2 constructor(context: Context) : Dialog(context, R.style.DialogStyle) {
    protected val ctx = context

    override fun show() {
        if (ctx is Activity) {
            if (!ctx.isFinishing) {
                super.show()
            }
        } else {
            super.show()
        }
    }

    override fun dismiss() {
        if (ctx is Activity) {
            if (!ctx.isFinishing) {
                super.dismiss()
            }
        } else {
            super.dismiss()
        }
    }
}