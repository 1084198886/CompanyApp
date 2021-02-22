package com.sj.app.activities.home.my.myDynamic

/**
 * @author gqy
 * @date 2019/8/25
 * @since 1.0.0
 * @see
 * @desc  数据回调
 */
interface OnInteractionListener {
    fun getAll():List<String>
    fun getPhotoList():List<String>
    fun getVideoList():List<String>
}