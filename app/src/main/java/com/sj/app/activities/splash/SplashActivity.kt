package com.sj.app.activities.splash

import android.os.Bundle
import android.widget.TextView
import com.sj.app.R
import com.sj.app.activities.BaseActivity
import com.sj.app.activities.SPApplication
import com.sj.app.activities.init.InitActivity
import com.supwisdom.commonlib.execption.TerminalConfigError
import com.supwisdom.commonlib.utils.CommonUtil
import com.supwisdom.commonlib.utils.ThreadPool

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.1
 * @see
 * @desc 闪屏页
 */
class SplashActivity : BaseActivity() {
    private lateinit var vCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
    }

    private fun initView() {
        vCount = this.findViewById<TextView>(R.id.splash_count)
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        ThreadPool.getShortPool().execute {
            var count = 3
            while (0 < count) {
                runOnUiThread {
                    vCount.text = String.format("%ds", count)
                    --count
                }
                CommonUtil.doSleep(1000)
            }
            try {
                SPApplication.getInstance().getPhoneTerminal().loadConfig()
            } catch (ex: TerminalConfigError) {
                jumpToActivity(InitActivity::class.java)
            }
        }
    }
}