package com.sj.app.activities.home.my.activityDetail

import android.app.Activity
import android.os.Bundle
import android.view.View
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
 * @desc  活动详情
 */
class ActivityDetailActivity : BaseActivity(), ActivityDetailView {
    private lateinit var dialogProgress: DialogProgress
    private var presenter: ActivityDetailPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initView()
        initData()
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "活动详情"
    }

    private fun loadData() {
        presenter!!.getActivityDetails()
    }

    override fun loadSuccess() {
    }

    private fun initData() {
        presenter = ActivityDetailPresenter(this)
        dialogProgress = DialogProgress.getInstance(this)
        loadData()
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

    override fun showToast(msg: String) {
        ToastUtil.show(this, msg)
    }
}