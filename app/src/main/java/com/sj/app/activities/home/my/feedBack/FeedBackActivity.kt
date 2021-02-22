package com.sj.app.activities.home.my.feedBack

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.sj.app.R
import com.sj.app.activities.BaseActivity
import com.sj.app.view.DialogProgress
import com.sj.app.view.ToastUtil

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.0
 * @see
 * @desc  意见反馈
 */
class FeedBackActivity : BaseActivity(), FeedBackView {
    private lateinit var mInputAdvice: EditText

    private lateinit var dialogProgress: DialogProgress
    private var presenter: FeedBackPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        initView()
        initData()
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "意见反馈"

        mInputAdvice = findViewById<View>(R.id.input_advice) as EditText
        findViewById<View>(R.id.btn_commit).setOnClickListener {
            doFeedBack()
        }
    }

    private fun doFeedBack() {
        val advice = mInputAdvice.text.toString()
        if (TextUtils.isEmpty(advice)) {
            return showToast("请输入反馈内容")
        }
        presenter!!.feedBack(advice)
    }

    private fun initData() {
        presenter = FeedBackPresenter(this)
        dialogProgress = DialogProgress.getInstance(this)
    }

    override fun getActivity(): Activity {
        return this
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

    override fun feedBackSuccess(msg: String) {
        showToast(msg)
        finish()
    }

    override fun showToast(msg: String) {
        ToastUtil.show(this, msg)
    }
}