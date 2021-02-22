package com.sj.app.utils

import android.app.AlertDialog
import android.content.Context

/**
 * @author zzq
 * @date 2019/4/28
 * @desc 错误提示
 */
object AlertError {

    fun showDialog(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }
}
