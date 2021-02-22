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
 * @desc  年龄范围
 */
class DialogAgeRangeSelect constructor(context: Context) : DialogBase2(context) {
    private var vAgePickerLeft: WheelPicker
    private var vAgePickerRight: WheelPicker
    var itemListener: AgeRangeSelectListener? = null

    init {
        setContentView(R.layout.dialog_age_range_select)

        findViewById<View>(R.id.v_save).setOnClickListener {
            dismiss()
        }
        vAgePickerLeft = findViewById<WheelPicker>(R.id.v_age_picker_left)
        val ages = getAgeData()
        vAgePickerLeft.data = ages
        vAgePickerRight = findViewById<WheelPicker>(R.id.v_age_picker_right)
        vAgePickerRight.data = ages

        this.window!!.attributes.gravity = Gravity.BOTTOM
        this.window!!.attributes.width = CommonUtil.getScreenWith()
        setCanceledOnTouchOutside(true)
        this.setCancelable(true)
    }

    private fun getAgeData(): List<String> {
        val ageList = arrayListOf<String>()
        for (i in 1 until 99) {
            ageList.add(i.toString())
        }
        return ageList
    }

    interface AgeRangeSelectListener {
        fun onSelect()
    }
}