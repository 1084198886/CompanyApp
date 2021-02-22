package com.sj.app.activities.home.my.realnameAuth

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
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
 * @desc  实名认证
 */
class RealNameAuthActivity : BaseActivity(), RealNameAuthView {
    private lateinit var mIdNationalEmblem: ImageView
    private lateinit var mIdHeadPhoto: ImageView

    private lateinit var dialogProgress: DialogProgress
    private var presenter: RealNameAuthPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_name_auth)
        initView()
        initData()
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "实名认证"

        mIdNationalEmblem = findViewById<View>(R.id.v_idcard_national_emblem) as ImageView
        mIdHeadPhoto = findViewById<View>(R.id.v_idcard_head_photo) as ImageView
        findViewById<View>(R.id.btn_commit).setOnClickListener {
            doRealNameAuth()
        }
    }

    private fun doRealNameAuth() {
//        val advice = mInputAdvice.text.toString()
//        if (TextUtils.isEmpty(advice)) {
//            return showToast("请输入反馈内容")
//        }
        presenter!!.realNameAuth()
    }

    private fun initData() {
        presenter = RealNameAuthPresenter(this)
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

    override fun authSuccess(msg: String) {
        showToast(msg)
        finish()
    }

    override fun showToast(msg: String) {
        ToastUtil.show(this, msg)
    }
}