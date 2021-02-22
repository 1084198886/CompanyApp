package com.sj.app.activities.home.my

import android.app.Activity
import com.supwisdom.orderlib.entity.DynamicParaRecord

/**
 * @author gqy
 * @date 2019/8/9
 * @since 1.0.0
 * @see
 * @desc
 */
interface MyView {
    fun getMyActivity(): Activity
    fun showMyInfo(myInfo: DynamicParaRecord)
}