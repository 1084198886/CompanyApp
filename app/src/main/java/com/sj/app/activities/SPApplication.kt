package com.sj.app.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.sj.app.service.BackgroundTaskService
import com.sj.timlib.TIMTerminal
import com.supwisdom.commonlib.utils.CommonUtil
import com.supwisdom.orderlib.PhoneTerminal
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy

/**
 * @desc : 应用扩展
 */
class SPApplication : MultiDexApplication() {
    companion object {
        @Volatile
        private var mInstance: SPApplication? = null

        fun getInstance(): SPApplication {
            return mInstance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        startService()
        CrashHandler.getInstance().init(applicationContext)
        initCrashReportSdk()
        initTUIKit()
    }

    private fun initCrashReportSdk() {
        // 获取当前进程名
        val processName = CommonUtil.getProcessName(android.os.Process.myPid())
        // 设置是否为上报进程
        val strategy = UserStrategy(applicationContext)
        strategy.isUploadProcess = processName == null || processName == packageName
        CrashReport.initCrashReport(this, "3c109075d8", true, strategy)
    }

    private fun initTUIKit() {
        if (getTIMTerminal().isMainProcess()) {
            getTIMTerminal().initUIKit()
            registerActivityLifecycleCallbacks(ActLifecycleCallback())
        }
    }

    inner class ActLifecycleCallback : ActivityLifecycleCallbacks {
        private var foregroundActivities = 0
        private var isChangingConfiguration = false
        private val TAG = javaClass.simpleName

        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity) {
            foregroundActivities++
            if (foregroundActivities == 1 && !isChangingConfiguration) {
                getTIMTerminal().doForeground()
            }
            isChangingConfiguration = false
        }

        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity) {
            foregroundActivities--
            if (foregroundActivities == 0) {
                getTIMTerminal().doBackground()
            }
            isChangingConfiguration = activity.isChangingConfigurations
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        }

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    fun getPhoneTerminal(): PhoneTerminal {
        return PhoneTerminal.getInstance(applicationContext)
    }

    fun getTIMTerminal(): TIMTerminal {
        return TIMTerminal.getInstance(applicationContext)
    }

    private fun startService() {
        startService(Intent(this, BackgroundTaskService::class.java))
    }

    fun stopService() {
        stopService(Intent(this, BackgroundTaskService::class.java))
    }
}