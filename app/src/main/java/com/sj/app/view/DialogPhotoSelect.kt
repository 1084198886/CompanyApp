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
 * @desc  图片选择
 */
class DialogPhotoSelect constructor(context: Context) : DialogBase2(context) {
    var itemListener: PhotoSelectListener? = null

    init {
        setContentView(R.layout.dialog_photo_select)
        findViewById<TextView>(R.id.v_album).setOnClickListener {
            dismiss()
            itemListener!!.onSelect(Photo.ALBUM)
        }
        findViewById<TextView>(R.id.v_camera).setOnClickListener {
            dismiss()
            itemListener!!.onSelect(Photo.CAMERA)
        }
        findViewById<View>(R.id.v_cancel).setOnClickListener {
            dismiss()
        }
        this.window!!.attributes.gravity = Gravity.BOTTOM
        this.window!!.attributes.width = CommonUtil.getScreenWith()
        setCanceledOnTouchOutside(true)
        this.setCancelable(true)
    }

    interface PhotoSelectListener {
        fun onSelect(photo: Photo)
    }

    enum class Photo constructor(desc: String) {
        ALBUM("相册"), CAMERA("拍照");

        val desc = desc
    }
}