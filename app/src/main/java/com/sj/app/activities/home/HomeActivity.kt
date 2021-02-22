package com.sj.app.activities.home

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sj.app.R
import com.sj.app.activities.home.advice.AdviceFragment
import com.sj.app.activities.home.message.MessageFragment
import com.sj.app.activities.home.main.MainFragment
import com.sj.app.activities.home.my.MyFragment
import com.sj.app.activities.home.square.SquereFragment
import com.sj.app.utils.AppExitUtil
import com.sj.app.view.ToastUtil

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.0
 * @see
 * @desc  home页
 */
class HomeActivity : FragmentActivity() {
    private lateinit var mIvTabMain: ImageView
    private lateinit var mTvTabMain: TextView
    private lateinit var mIvTabMessage: ImageView
    private lateinit var mTvTabMessage: TextView
    private lateinit var mIvTabAdvice: ImageView
    private lateinit var mTvTabAdvice: TextView
    private lateinit var mIvTabSquare: ImageView
    private lateinit var mTvTabSquare: TextView
    private lateinit var mIvTabMy: ImageView
    private lateinit var mTvTabMy: TextView
    private var clickTabId = 0

    private lateinit var mainFragment: MainFragment
    private lateinit var messageFragment: MessageFragment
    private lateinit var adviceFragment: AdviceFragment
    private lateinit var squereFragment: SquereFragment
    private lateinit var myFragment: MyFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        AppExitUtil.add(this)
        initView()
        initData()
    }

    private fun initView() {
        findViewById<View>(R.id.tab_main).setOnClickListener {
            switchTabView(R.id.tab_main)
        }
        mIvTabMain = findViewById<ImageView>(R.id.iv_tab_main)
        mTvTabMain = findViewById<TextView>(R.id.tv_tab_main)
        findViewById<View>(R.id.tab_message).setOnClickListener {
            switchTabView(R.id.tab_message)
        }
        mIvTabMessage = findViewById<ImageView>(R.id.iv_tab_message)
        mTvTabMessage = findViewById<TextView>(R.id.tv_tab_message)

        findViewById<View>(R.id.tab_advice).setOnClickListener {
            switchTabView(R.id.tab_advice)
        }
        mIvTabAdvice = findViewById<ImageView>(R.id.iv_tab_advice)
        mTvTabAdvice = findViewById<TextView>(R.id.tv_tab_advice)

        findViewById<View>(R.id.tab_square).setOnClickListener {
            switchTabView(R.id.tab_square)
        }
        mIvTabSquare = findViewById<ImageView>(R.id.iv_tab_square)
        mTvTabSquare = findViewById<TextView>(R.id.tv_tab_square)

        findViewById<View>(R.id.tab_my).setOnClickListener {
            // TODO 没有登录时跳转登录页面
            switchTabView(R.id.tab_my)
        }
        mIvTabMy = findViewById<ImageView>(R.id.iv_tab_my)
        mTvTabMy = findViewById<TextView>(R.id.tv_tab_my)
    }

    private fun switchTabView(newClickId: Int) {
        if (clickTabId == newClickId) {
            return
        }
        unSelectTabView()
        when (newClickId) {
            R.id.tab_main -> {
                mIvTabMain.setBackgroundResource(R.drawable.loading_white)
                mTvTabMain.setTextColor(ContextCompat.getColor(this, R.color.cl_red))
                supportFragmentManager.beginTransaction().show(mainFragment).commit()
            }
            R.id.tab_message -> {
                mIvTabMessage.setBackgroundResource(R.drawable.loading_white)
                mTvTabMessage.setTextColor(ContextCompat.getColor(this, R.color.cl_red))
                supportFragmentManager.beginTransaction().show(messageFragment).commit()
            }
            R.id.tab_square -> {
                mIvTabSquare.setBackgroundResource(R.drawable.loading_white)
                mTvTabSquare.setTextColor(ContextCompat.getColor(this, R.color.cl_red))
                supportFragmentManager.beginTransaction().show(squereFragment).commit()
            }
            R.id.tab_advice -> {
                mIvTabAdvice.setBackgroundResource(R.drawable.loading_white)
                mTvTabAdvice.setTextColor(ContextCompat.getColor(this, R.color.cl_red))
                supportFragmentManager.beginTransaction().show(adviceFragment).commit()
            }
            R.id.tab_my -> {
                mIvTabMy.setBackgroundResource(R.drawable.loading_white)
                mTvTabMy.setTextColor(ContextCompat.getColor(this, R.color.cl_red))
                supportFragmentManager.beginTransaction().show(myFragment).commit()
            }
        }
        clickTabId = newClickId
    }

    private fun unSelectTabView() {
        when (clickTabId) {
            R.id.tab_main -> {
                // TODO
                mIvTabMain.setBackgroundResource(R.drawable.loading_white)
                mTvTabMain.setTextColor(ContextCompat.getColor(this, R.color.black))
                supportFragmentManager.beginTransaction().hide(mainFragment).commit()
            }
            R.id.tab_message -> {
                mIvTabMessage.setBackgroundResource(R.drawable.loading_white)
                mTvTabMessage.setTextColor(ContextCompat.getColor(this, R.color.black))
                supportFragmentManager.beginTransaction().hide(messageFragment).commit()
            }
            R.id.tab_square -> {
                mIvTabSquare.setBackgroundResource(R.drawable.loading_white)
                mTvTabSquare.setTextColor(ContextCompat.getColor(this, R.color.black))
                supportFragmentManager.beginTransaction().hide(squereFragment).commit()
            }
            R.id.tab_advice -> {
                mIvTabAdvice.setBackgroundResource(R.drawable.loading_white)
                mTvTabAdvice.setTextColor(ContextCompat.getColor(this, R.color.black))
                supportFragmentManager.beginTransaction().hide(adviceFragment).commit()
            }
            R.id.tab_my -> {
                mIvTabMy.setBackgroundResource(R.drawable.loading_white)
                mTvTabMy.setTextColor(ContextCompat.getColor(this, R.color.black))
                supportFragmentManager.beginTransaction().hide(myFragment).commit()
            }
        }
    }

    private var clickTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - clickTime < 500) {
                AppExitUtil.exit()
            } else {
                clickTime = System.currentTimeMillis()
                ToastUtil.show(this, "再按一次退出")
            }
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun initData() {
        initFragments()
        findViewById<View>(R.id.tab_main).performClick()
    }

    private fun initFragments() {
        mainFragment = MainFragment.newInstance("", "")
        messageFragment = MessageFragment.newInstance("", "")
        adviceFragment = AdviceFragment.newInstance("", "")
        squereFragment = SquereFragment.newInstance("", "")
        myFragment = MyFragment.newInstance("", "")
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.v_container, mainFragment)
        transaction.add(R.id.v_container, messageFragment)
        transaction.add(R.id.v_container, adviceFragment)
        transaction.add(R.id.v_container, squereFragment)
        transaction.add(R.id.v_container, myFragment)

        transaction.hide(mainFragment)
        transaction.hide(messageFragment)
        transaction.hide(adviceFragment)
        transaction.hide(squereFragment)
        transaction.hide(myFragment)
        transaction.commit()
    }

}
