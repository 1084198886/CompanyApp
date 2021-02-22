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
 * @desc  星座选择
 */
class DialogConstellationSelect constructor(context: Context) : DialogBase2(context) {
    private var vConstellationPicker: WheelPicker
    var itemListener: ConstellationSelectListener? = null

    init {
        setContentView(R.layout.dialog_constellation_select)
        vConstellationPicker = findViewById<WheelPicker>(R.id.v_constellation_picker)
        findViewById<View>(R.id.v_cancel).setOnClickListener {
            dismiss()
        }
        findViewById<View>(R.id.v_ok).setOnClickListener {
            itemListener!!.onSelect(
                vConstellationPicker.currentItemPosition,
                vConstellationPicker.data[vConstellationPicker.currentItemPosition].toString()
            )
            dismiss()
        }
        this.window!!.attributes.gravity = Gravity.BOTTOM
        this.window!!.attributes.width = CommonUtil.getScreenWith()
        setCanceledOnTouchOutside(true)
        this.setCancelable(true)
    }


    interface ConstellationSelectListener {
        fun onSelect(positon: Int, data: String)
    }
}