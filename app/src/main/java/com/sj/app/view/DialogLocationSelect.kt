package com.sj.app.view

import android.content.Context
import android.view.Gravity
import android.view.View
import com.aigestudio.wheelpicker.widgets.WheelAreaPicker
import com.sj.app.R
import com.supwisdom.commonlib.utils.CommonUtil

/**
 * @author gqy
 * @date 2019/8/9
 * @version 1.0.0
 * @desc  省市区选择
 */
class DialogLocationSelect constructor(context: Context) : DialogBase2(context) {
    private var vAreaPicker: WheelAreaPicker
    var itemListener: LocationSelectListener? = null

    init {
        setContentView(R.layout.dialog_location_select)

        findViewById<View>(R.id.v_save).setOnClickListener {
            dismiss()
        }
        vAreaPicker = findViewById<WheelAreaPicker>(R.id.v_area_picker)
        this.window!!.attributes.gravity = Gravity.BOTTOM
        this.window!!.attributes.width = CommonUtil.getScreenWith()
        setCanceledOnTouchOutside(true)
        this.setCancelable(true)
    }

    interface LocationSelectListener {
        fun onSelect()
    }
}