package com.sj.app.activities.init.bindPhone

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
import com.supwisdom.commonlib.utils.CommonUtil

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.0
 * @see
 * @desc  绑定手机号
 */
class BindPhoneActivity : BaseActivity(), BindPhoneView {
    private lateinit var mInputPhoneNum: EditText
    private lateinit var mInputVerifyno: EditText
    private lateinit var dialogProgress: DialogProgress
    private var presenter: BindPhonePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bind_phone)
        initView()
        initData()
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "绑定手机号"
        mInputPhoneNum = findViewById<View>(R.id.input_phone_num) as EditText
        mInputVerifyno = findViewById<View>(R.id.input_verifyno) as EditText
        findViewById<View>(R.id.v_get_verifyno).setOnClickListener {
            doGetVerifyNo()
        }
        findViewById<View>(R.id.btn_login).setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() {
        val phone = mInputPhoneNum.text.toString()
        if (TextUtils.isEmpty(phone) || !CommonUtil.checkPhone(phone)) {
            return showToast("请输入正确的手机号")
        }
        val verifyNo = mInputVerifyno.text.toString()
        if (TextUtils.isEmpty(verifyNo)) {
            return showToast("请输入验证码")
        }
        presenter!!.bindPhone(phone, verifyNo)
    }

    private fun initData() {
        presenter = BindPhonePresenter(this)
        dialogProgress = DialogProgress.getInstance(this)
    }

    private fun doGetVerifyNo() {
        val phone = mInputPhoneNum.text.toString()
        if (TextUtils.isEmpty(phone) || !CommonUtil.checkPhone(phone)) {
            return showToast("请输入正确的手机号")
        }
        presenter!!.getVerifyNo(phone)
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun bindPhoneSuccess(msg: String) {
        showToast(msg)
        // TODO
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