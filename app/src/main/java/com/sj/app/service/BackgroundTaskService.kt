package com.sj.app.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * @author gqy
 * @date 2019/8/8 0008.
 * @since 1.0.1
 * @see
 * @desc  TODO
 */
class BackgroundTaskService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }
}