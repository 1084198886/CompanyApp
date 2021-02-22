package com.sj.app.activities.home.my.settting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.sj.app.R
import com.sj.app.activities.BaseActivity
import com.sj.app.activities.home.my.feedBack.FeedBackActivity
import com.sj.app.activities.home.my.realnameAuth.RealNameAuthActivity
import com.sj.app.activities.init.InitActivity
import com.sj.app.activities.init.resetPwd.ResetPwdActivity
import com.sj.app.activities.home.my.settting.bean.SettingsBean
import com.sj.app.utils.AppPublicDef
import com.sj.app.view.DialogConfirm
import com.sj.app.view.DialogProgress
import com.sj.app.view.ToastUtil
import com.suke.widget.SwitchButton

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.0
 * @see
 * @desc 设置
 */
class SettingActivity : BaseActivity(), SettingView {
    private var presenter: SettingPresenter? = null
    private lateinit var dialogProgress: DialogProgress
    private lateinit var switchRecvLetter: SwitchButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initView()
        initData()
    }

    private fun initData() {
        presenter = SettingPresenter(this)
        dialogProgress = DialogProgress.getInstance(this)
    }

    override fun onResume() {
        super.onResume()
        presenter!!.getSettings("")
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "设置"

        findViewById<View>(R.id.panel_real_name_auth).setOnClickListener {
            jumpToActivity(RealNameAuthActivity::class.java)
        }
        findViewById<View>(R.id.panel_feedback).setOnClickListener {
            jumpToActivity(FeedBackActivity::class.java)
        }
        findViewById<View>(R.id.panel_modify_pwd).setOnClickListener {
            jumpToActivity(ResetPwdActivity::class.java)
        }
        switchRecvLetter = findViewById<View>(R.id.switch_recv_letter) as SwitchButton
        switchRecvLetter.setOnCheckedChangeListener { _, isChecked ->
            doSaveRecvLetterSwitch(isChecked)
        }

        findViewById<View>(R.id.panel_login_out).setOnClickListener {
            val confirmDialog = DialogConfirm(this@SettingActivity).setMessage("确认退出登录?")
            confirmDialog.callBack = object : DialogConfirm.IConfirmCallBack {
                override fun clickOk() {
                    presenter!!.doLoginOut()
                }
            }
            confirmDialog.show()
        }
    }

    private fun doSaveRecvLetterSwitch(checked: Boolean) {
        val state = if (checked) "1" else "0"
        presenter!!.doSaveRecvLetterSwitch(state)
    }

    override fun loginOutSuccess() {
        val intent = Intent(this, InitActivity::class.java)
        intent.putExtra(AppPublicDef.EXTRA_LOGIN, "doLogin")
        startActivity(intent)
        finish()
    }

    override fun showSettings(settings: SettingsBean) {
        switchRecvLetter.isChecked = !settings.recvPrivateLetter
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun saveRecvLetterSwitchSuc(msg: String) {
        showToast(msg)
        // TODO 刷新开关状态
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

}