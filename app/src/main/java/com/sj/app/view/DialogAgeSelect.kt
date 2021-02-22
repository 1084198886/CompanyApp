package com.sj.app.view

import android.content.Context
import android.view.Gravity
import android.view.View
import com.aigestudio.wheelpicker.WheelPicker
import com.sj.app.R
import com.supwisdom.commonlib.utils.CommonUtil

/**
 * @author gqy
 * @date 2019/8/9
 * @version 1.0.0
 * @desc  年龄选择
 */
class DialogAgeSelect constructor(context: Context) : DialogBase2(context) {
    private var vAgePicker: WheelPicker
    var itemListener: AgeSelectListener? = null

    init {
        setContentView(R.layout.dialog_age_select)
        vAgePicker = findViewById<WheelPicker>(R.id.v_age_picker)
        vAgePicker.data = getAgesData()
        findViewById<View>(R.id.v_cancel).setOnClickListener {
            dismiss()
        }
        findViewById<View>(R.id.v_ok).setOnClickListener {
            itemListener!!.onSelect(
                vAgePicker.currentItemPosition,
                vAgePicker.data[vAgePicker.currentItemPosition].toString()
            )
            dismiss()
        }
        this.window!!.attributes.gravity = Gravity.BOTTOM
        this.window!!.attributes.width = CommonUtil.getScreenWith()
        setCanceledOnTouchOutside(true)
        this.setCancelable(true)
    }

    private fun getAgesData(): List<String> {
        val ageList = arrayListOf<String>()
        for (i in 10 until 100) {
            ageList.add(i.toString())
        }
        return ageList
    }

    interface AgeSelectListener {
        fun onSelect(positon: Int, data: String)
    }
}