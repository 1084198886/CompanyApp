package com.sj.app.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.WindowManager
import com.sj.app.R
import com.sj.app.utils.AppCommonUtil
import com.sj.app.utils.AppExitUtil

/**
 * @desc 基类activity
 */
open class BaseFragmentActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppExitUtil.add(this)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun onResume() {
        super.onResume()
        /**
         * 设置view高度为状态栏高度
         */
        val statebarView = findViewById<View>(R.id.v_statebar)
        if (statebarView != null) {
            AppCommonUtil.setTopBarHeight(this, statebarView)
            AppCommonUtil.fullScreen(this)
        }
    }

    override fun onDestroy() {
        AppExitUtil.remove(this)
        super.onDestroy()
    }

    override fun onStop() {
        super.onStop()
    }

    fun <T> jumpToActivity(cls: Class<T>) {
        startActivity(Intent().setClass(this, cls))
    }
}
