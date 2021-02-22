package com.sj.app.activities.init.resetPwd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.sj.app.R
import com.sj.app.activities.BaseActivity
import com.sj.app.activities.init.InitActivity
import com.sj.app.utils.AppPublicDef
import com.sj.app.view.DialogProgress
import com.sj.app.view.ToastUtil

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.1
 * @see
 * @desc 找回密码
 */
class ResetPwdActivity : BaseActivity(), ResetPwdView {
    private lateinit var mInputPhoneNum: EditText
    private lateinit var mInputVerifyno: EditText
    private lateinit var mVGetVerifyno: Button
    private lateinit var mInputPwd: EditText
    private lateinit var mInputPwdAgain: EditText
    private lateinit var mBtnNext: Button
    private var presenter: ResetPwdPresenter? = null
    private lateinit var dialogProgress: DialogProgress


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pwd)
        initView()
        initData()
    }

    private fun initData() {
        presenter = ResetPwdPresenter(this)
        dialogProgress = DialogProgress.getInstance(this)
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "修改密码"

        mInputPhoneNum = findViewById<View>(R.id.input_phone_num) as EditText
        mInputVerifyno = findViewById<View>(R.id.input_verifyno) as EditText
        mVGetVerifyno = findViewById<View>(R.id.v_get_verifyno) as Button
        mVGetVerifyno.setOnClickListener {
            doGetVerifyNo()
        }
        mInputPwd = findViewById<View>(R.id.input_pwd) as EditText
        mInputPwdAgain = findViewById<View>(R.id.input_pwd_again) as EditText
        mBtnNext = findViewById<View>(R.id.btn_next) as Button
        mBtnNext.setOnClickListener {
            doNext()
        }
        findViewById<View>(R.id.v_regist).setOnClickListener {
            val intent = Intent(this@ResetPwdActivity, InitActivity::class.java)
            intent.putExtra(AppPublicDef.EXTRA_REGIST, "doRegist")
            startActivity(intent)
        }
    }

    private fun doGetVerifyNo() {
        val account = mInputPhoneNum.text.toString()
        if (TextUtils.isEmpty(account)) {
            return showToast("请输入账号")
        }
        presenter!!.getVerifyNo(account)
    }

    private fun doNext() {
        val account = mInputPhoneNum.text.toString()
        if (TextUtils.isEmpty(account)) {
            return showToast("请输入账号")
        }
        val verifyNo = mInputVerifyno.text.toString()
        if (TextUtils.isEmpty(verifyNo)) {
            return showToast("请输入验证码")
        }
        val pwd = mInputPwd.text.toString()
        val pwdAgain = mInputPwdAgain.text.toString()
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdAgain)) {
            return showToast("请输入密码")
        }
        if (pwd != pwdAgain) {
            return showToast("2次密码不一致")
        }
        presenter!!.resetPwd(account, verifyNo, pwd)
    }

    override fun showProgressDialog(msg: String) {
        dialogProgress.setMessage(msg).show()
    }

    override fun closeProgressDialog() {
        if (dialogProgress.isShowing) {
            dialogProgress.dismiss()
        }
    }

    override fun resetPwdSuccess(msg: String) {
        showToast(msg)
        val intent = Intent(this@ResetPwdActivity, InitActivity::class.java)
        intent.putExtra(AppPublicDef.EXTRA_LOGIN, "doLogin")
        startActivity(intent)
    }

    override fun showToast(msg: String) {
        ToastUtil.show(this, msg)
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun onStop() {
        super.onStop()
        closeProgressDialog()
    }

}