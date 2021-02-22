package com.sj.app.activities.home.my.videoTest

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.VideoView
import com.sj.app.R
import com.sj.app.activities.BaseActivity
import android.widget.MediaController


/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.0
 * @see
 * @desc  视频测试
 * http://rbv01.ku6.com/omtSn0z_PTREtneb3GRtGg.mp4
http://rbv01.ku6.com/7lut5JlEO-v6a8K3X9xBNg.mp4
https://key003.ku6.com/movie/1af61f05352547bc8468a40ba2d29a1d.mp4
https://key002.ku6.com/xy/d7b3278e106341908664638ac5e92802.mp4
 */
class VideoTestActivity : BaseActivity() {
    private lateinit var mVVideoTest: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_test)
        initView()
        initData()
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "视频测试"

        mVVideoTest = findViewById<View>(R.id.v_video_test) as VideoView
    }

    private fun initData() {
        mVVideoTest.setVideoPath("http://rbv01.ku6.com/7lut5JlEO-v6a8K3X9xBNg.mp4")
        mVVideoTest.setMediaController(MediaController(this))
        mVVideoTest.setOnPreparedListener {
            mVVideoTest.start()
        }

        mVVideoTest.setOnCompletionListener {
            mVVideoTest.start()
        }
    }

}