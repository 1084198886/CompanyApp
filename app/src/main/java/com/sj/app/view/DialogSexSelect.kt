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
 * @desc  性别选择
 */
class DialogSexSelect constructor(context: Context) : DialogBase2(context) {
    var itemListener: SexSelectListener? = null

    init {
        setContentView(R.layout.dialog_sex_select)
        findViewById<TextView>(R.id.v_male).setOnClickListener {
            itemListener!!.onSelect(Sex.MALE)
        }
        findViewById<TextView>(R.id.v_female).setOnClickListener {
            itemListener!!.onSelect(Sex.FEMALE)
            dismiss()
        }
        findViewById<View>(R.id.v_cancel).setOnClickListener {
            dismiss()
        }
        this.window!!.attributes.gravity = Gravity.BOTTOM
        this.window!!.attributes.width = CommonUtil.getScreenWith()
        setCanceledOnTouchOutside(true)
        this.setCancelable(true)
    }

    interface SexSelectListener {
        fun onSelect(sex: Sex)
    }

    enum class Sex constructor(desc: String) {
        MALE("男"), FEMALE("女");

        val desc = desc
    }
}