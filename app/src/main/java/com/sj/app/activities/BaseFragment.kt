package com.sj.app.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.sj.app.utils.AppCommonUtil
import com.supwisdom.commonlib.utils.LogUtil

/**
 * @author gqy
 * @date 2019/8/13
 * @since 1.0.0
 * @see
 * @desc  Fragment基类
 */
open class BaseFragment : Fragment() {
    protected var statusBar: View? = null


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (statusBar != null && activity != null) {
            AppCommonUtil.setTopBarHeight(activity!!, statusBar!!)
            AppCommonUtil.fullScreen(activity!!)
        }
    }

    fun <T> jumpToActivity(ctx: Context, cls: Class<T>) {
        startActivity(Intent().setClass(ctx, cls))
    }
}