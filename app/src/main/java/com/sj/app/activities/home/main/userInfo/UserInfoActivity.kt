package com.sj.app.activities.home.main.userInfo

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.sj.app.R
import com.sj.app.activities.BaseActivity
import com.sj.app.utils.AppPublicDef
import com.sj.app.view.DialogProgress
import com.sj.app.view.ToastUtil

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.0
 * @see
 * @desc 用户信息
 */
class UserInfoActivity : BaseActivity(), UserInfoView {
    private var userId: String? = null
    private var presenter: UserInfoPresenter? = null
    private lateinit var dialogProgress: DialogProgress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        initView()
        initData()
    }

    private fun initData() {
        presenter = UserInfoPresenter(this)
        dialogProgress = DialogProgress.getInstance(this)

        userId = intent.getStringExtra(AppPublicDef.EXTRA_USERID)
        getUserInfo()
    }

    private fun getUserInfo() {
        presenter!!.getUserInfo(userId)
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "用户信息"
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

    override fun showToast(msg: String) {
        ToastUtil.show(this, msg)
    }

    override fun showUserInfo() {
    }

}