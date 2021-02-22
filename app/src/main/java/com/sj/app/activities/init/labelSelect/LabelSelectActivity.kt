package com.sj.app.activities.init.labelSelect

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.sj.app.R
import com.sj.app.activities.BaseActivity
import com.sj.app.activities.home.HomeActivity
import com.sj.app.view.DialogProgress
import com.sj.app.view.KeywordsFlow
import com.sj.app.view.ToastUtil

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.0
 * @see
 * @desc  标签选择
 */
class LabelSelectActivity : BaseActivity(), LabelSelectView {
    private lateinit var dialogProgress: DialogProgress
    private lateinit var presenter: LabelSelectPresenter
    private lateinit var keywordsFlow: KeywordsFlow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_label_select)
        initView()
        initData()
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "个性标签"

        findViewById<View>(R.id.v_enter).setOnClickListener {
            doEnter()
        }
        keywordsFlow = findViewById<View>(R.id.keywordsFlow) as KeywordsFlow
        keywordsFlow.duration = 500L
        keywordsFlow.itemClickListener = KeywordsFlow.ItemClickListener { view, position ->
            val cnt = keywordsFlow.selectCnt
            if (cnt >= 5) {
                showToast("标签只能选5个")
                return@ItemClickListener
            }
            if (view.isSelected) {
                view.setBackgroundResource(R.drawable.bg_personnal_label_normal)
            } else {
                view.setBackgroundResource(R.drawable.bg_personnal_label_selected)
            }
            view.isSelected = !view.isSelected
        }
        feedKeywordsFlow()
        keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN)
    }

    // TODO
    private fun feedKeywordsFlow() {
        val keywords = arrayOf("佛系交友", "游戏", "定心", "电音节", "单身主义", "游戏", "定心", "电音节", "单身主义", "蹦迪高手")
        keywords.forEach {
            keywordsFlow.feedKeyword(it)
        }
    }

    private fun doEnter() {
//        presenter.saveCustLabel("")
        saveLabelSuccess("")
    }

    private fun initData() {
        presenter = LabelSelectPresenter(this)
        dialogProgress = DialogProgress.getInstance(this)
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun saveLabelSuccess(msg: String) {
        jumpToActivity(HomeActivity::class.java)
    }

    override fun showProgressDialog(msg: String) {
        dialogProgress.setMessage(msg).show()
    }

    override fun closeProgressDialog() {
        if (dialogProgress.isShowing) {
            dialogProgress.dismiss()
        }
    }

    override fun onStop() {
        super.onStop()
        closeProgressDialog()
    }

    override fun showToast(msg: String) {
        ToastUtil.show(this, msg)
    }
}