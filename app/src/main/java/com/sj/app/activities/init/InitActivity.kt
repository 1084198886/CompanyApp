package com.sj.app.activities.init

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.view.View
import android.widget.*
import com.sj.app.R
import com.sj.app.activities.BaseActivity
import com.sj.app.activities.SPApplication
import com.sj.app.activities.init.bindPhone.BindPhoneActivity
import com.sj.app.activities.init.resetPwd.ResetPwdActivity
import com.sj.app.activities.home.HomeActivity
import com.sj.app.activities.init.perfectInfo.PerfectInfoActivity
import com.sj.app.activities.init.protocol.ProtocolActivity
import com.sj.app.utils.AppPublicDef
import com.sj.app.view.DialogProgress
import com.sj.app.view.ToastUtil
import com.supwisdom.commonlib.utils.CommonUtil
import com.supwisdom.orderlib.entity.ConfigParaRecord

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.0
 * @desc  初始化页
 */
class InitActivity : BaseActivity(), InitView {
    private lateinit var dialogProgress: DialogProgress
    private lateinit var mTabLogin: Button
    private lateinit var mTabRegist: Button

    private lateinit var mVPanelLogin: TableLayout
    private lateinit var mInputAccount: EditText
    private lateinit var mInputPwd: EditText
    private lateinit var mVLosePwd: TextView

    private lateinit var mVPanelRegist: TableLayout
    private lateinit var mInputPhoneNum: EditText
    private lateinit var mInputVerifyno: EditText
    private lateinit var mInputRegistPwd: EditText
    private lateinit var mInputRegistPwdAgain: EditText
    private lateinit var mVAgreement: TextView

    private lateinit var mVOtherLoginLabel: TextView
    private lateinit var mVLoginWeixin: TextView
    private lateinit var mVLoginWeibo: TextView
    private var presenter: InitPresenter? = null
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)
        initView()
        initData()
    }

    private fun initView() {
        mTabLogin = findViewById<View>(R.id.tab_login) as Button
        mTabLogin.setOnClickListener {
            mTabLogin.setTextColor(ContextCompat.getColor(this, R.color.dark_red))
            mTabRegist.setTextColor(ContextCompat.getColor(this, R.color.black))
            mVOtherLoginLabel.text = "其他登录方式"
            mVPanelLogin.visibility = View.VISIBLE
            mVPanelRegist.visibility = View.GONE
        }
        mTabRegist = findViewById<View>(R.id.tab_regist) as Button
        mTabRegist.setOnClickListener {
            mVOtherLoginLabel.text = "其他注册方式"
            mTabLogin.setTextColor(ContextCompat.getColor(this, R.color.black))
            mTabRegist.setTextColor(ContextCompat.getColor(this, R.color.dark_red))
            mVPanelLogin.visibility = View.GONE
            mVPanelRegist.visibility = View.VISIBLE
        }
        mVPanelLogin = findViewById<View>(R.id.v_panel_login) as TableLayout
        mInputAccount = findViewById<View>(R.id.input_account) as EditText
        mInputPwd = findViewById<View>(R.id.input_pwd) as EditText
        findViewById<View>(R.id.btn_login).setOnClickListener {
            doLogin()
        }
        mVLosePwd = findViewById<View>(R.id.v_lose_pwd) as TextView
        mVLosePwd.setOnClickListener {
            jumpToActivity(ResetPwdActivity::class.java)
        }
        mVPanelRegist = findViewById<View>(R.id.v_panel_regist) as TableLayout
        mInputPhoneNum = findViewById<View>(R.id.input_phone_num) as EditText
        mInputVerifyno = findViewById<View>(R.id.input_verifyno) as EditText
        findViewById<View>(R.id.v_get_verifyno).setOnClickListener {
            doGetVerifyNo()
        }
        mInputRegistPwd = findViewById<View>(R.id.input_regist_pwd) as EditText
        mInputRegistPwdAgain = findViewById<View>(R.id.input_regist_pwd_again) as EditText
        findViewById<View>(R.id.btn_regist).setOnClickListener {
            doRegist()
        }
        mVAgreement = findViewById<View>(R.id.v_agreement) as TextView
        initAgreementText()

        mVOtherLoginLabel = findViewById<TextView>(R.id.v_other_login_label)
        mVLoginWeixin = findViewById<TextView>(R.id.v_login_weixin)
        mVLoginWeixin.setOnClickListener {
            doWeiXinLogin()
        }
        mVLoginWeibo = findViewById<TextView>(R.id.v_login_weibo)
        mVLoginWeibo.setOnClickListener {
            doWeiBoLogin()
        }
    }

    private fun doWeiBoLogin() {
        val intent = Intent(this, BindPhoneActivity::class.java)
        startActivity(intent)
    }

    private fun doWeiXinLogin() {
        val intent = Intent(this, BindPhoneActivity::class.java)
        startActivity(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        if (intent.hasExtra(AppPublicDef.EXTRA_REGIST)) {
            mTabRegist.performClick()
        } else if (intent.hasExtra(AppPublicDef.EXTRA_LOGIN)) {
            mTabLogin.performClick()
        }
    }

    private fun doRegist() {
        val phone = mInputPhoneNum.text.toString()
        if (TextUtils.isEmpty(phone) || !CommonUtil.checkPhone(phone)) {
            return showToast("请输入正确的手机号")
        }
        val verifyNo = mInputVerifyno.text.toString()
        if (TextUtils.isEmpty(verifyNo)) {
            return showToast("请输入验证码")
        }
        val pwd = mInputRegistPwd.text.toString()
        val pwdAgain = mInputRegistPwdAgain.text.toString()
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdAgain)) {
            return showToast("请输入密码")
        }
        if (pwd != pwdAgain) {
            return showToast("2次密码不一致")
        }
        presenter!!.appRegist(phone, verifyNo, pwd)
    }

    override fun showToast(msg: String) {
        ToastUtil.show(this, msg)
    }

    private fun doGetVerifyNo() {
        val phone = mInputPhoneNum.text.toString()
        if (TextUtils.isEmpty(phone) || !CommonUtil.checkPhone(phone)) {
            return showToast("请输入正确的手机号")
        }
        presenter!!.getVerifyNo(phone)
    }

    private fun initData() {
        presenter = InitPresenter(this)
        dialogProgress = DialogProgress.getInstance(this)
        var record = phoneTerminal.getConfig()
        if (record == null) {
            record = ConfigParaRecord()
            record.serverurl = AppPublicDef.SERVER_URL
        }
        phoneTerminal.saveConfig(record)
    }

    private fun doLogin() {
        val account = mInputAccount.text.toString()
        val password = mInputPwd.text.toString()
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            return ToastUtil.show(this, "登录信息不能为空")
        }
        presenter!!.appLogin(account, password)
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun loginSuccess() {
        presenter!!.timLogin()
        //  TODO  if 资料完善了 to HomeActivity else PerfectCustInfoActivity
        var isPerfect = false
        if (isPerfect) {
            jumpToActivity(HomeActivity::class.java)
        } else {
            jumpToActivity(PerfectInfoActivity::class.java)
        }
        finish()
    }

    override fun showProgressDialog(msg: String) {
        dialogProgress.setMessage(msg).show()
    }

    override fun closeProgressDialog() {
        if (dialogProgress.isShowing) {
            dialogProgress.dismiss()
        }
    }

    private var ssb = SpannableStringBuilder(" 注册即同意")
    private fun initAgreementText() {
        var isChecked = false
        setIconSpan(ssb, R.drawable.loading_white)
        ssb.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                if (widget is TextView) {
                    widget.highlightColor = Color.BLACK
                }
                // TODO 替换复选框
//                setIconSpan(ssb, if (isChecked) R.drawable.loading_white else R.drawable.loading_white)
                isChecked = !isChecked
                mVAgreement.text = ssb
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }, 0, ssb.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val protocolBuilder = SpannableStringBuilder("《用户协议》")
        protocolBuilder.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                if (widget is TextView) {
                    widget.highlightColor = Color.TRANSPARENT
                }
                jumpToActivity(ProtocolActivity::class.java)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }, 0, protocolBuilder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val foregroundColorSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary))
        protocolBuilder.setSpan(foregroundColorSpan, 0, protocolBuilder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssb.append(protocolBuilder)
        mVAgreement.text = ssb
        mVAgreement.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onStop() {
        super.onStop()
        closeProgressDialog()
    }

    private fun setIconSpan(ssb: SpannableStringBuilder, icon: Int) {
        ssb.setSpan(object : ImageSpan(this, BitmapFactory.decodeResource(resources, icon), 0) {
            override fun draw(
                canvas: Canvas,
                text: CharSequence?,
                start: Int,
                end: Int,
                x: Float,
                top: Int,
                y: Int,
                bottom: Int,
                paint: Paint
            ) {
                super.draw(canvas, text, start, end, x, top, y, bottom, paint)
                val drawable = drawable
                canvas.save()
                val fm = paint.fontMetricsInt

                //系统原有方法，默认是Bottom模式)
                var transY = bottom - drawable!!.bounds.bottom
                if (mVerticalAlignment == ALIGN_BASELINE) {
                    transY -= fm.descent
                } else if (mVerticalAlignment == ALIGN_BOTTOM) {
                    transY = (y + fm.descent + (y + fm.ascent)) / 2 - drawable.bounds.bottom / 2
                }
                canvas.translate(x, transY.toFloat())
                drawable.draw(canvas)
                canvas.restore()
            }
        }, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

}