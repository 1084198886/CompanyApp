package com.sj.app.activities.home.advice.startGroup

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.sj.app.R
import com.sj.app.activities.BaseActivity
import com.sj.app.view.DialogProgress
import com.sj.app.view.ToastUtil
import android.widget.EditText
import com.supwisdom.commonlib.utils.CommonUtil

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.0
 * @see
 * @desc 开团
 */
class StartGroupActivity : BaseActivity(), StartGroupView {
    private lateinit var mInputGroupName: EditText
    private lateinit var mInputGroupType: TextView
    private lateinit var mInputGenderRequire: TextView
    private lateinit var mInputGroupCost: TextView
    private lateinit var mInputPerAmount: EditText
    private lateinit var mInputCollectNumber: EditText

    private var presenter: StartGroupPresenter? = null
    private lateinit var dialogProgress: DialogProgress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_group)
        initView()
        initData()
    }

    private fun initData() {
        presenter = StartGroupPresenter(this)
        dialogProgress = DialogProgress.getInstance(this)
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "开团"

        mInputGroupName = findViewById<View>(R.id.input_group_name) as EditText
        findViewById<View>(R.id.panel_group_type).setOnClickListener {

        }
        mInputGroupType = findViewById<View>(R.id.input_group_type) as TextView
        findViewById<View>(R.id.panel_gender_require).setOnClickListener {

        }
        mInputGenderRequire = findViewById<View>(R.id.input_gender_require) as TextView
        findViewById<View>(R.id.panel_group_cost).setOnClickListener {

        }
        mInputGroupCost = findViewById<View>(R.id.input_group_cost) as TextView
        mInputPerAmount = findViewById<View>(R.id.input_per_amount) as EditText
        mInputCollectNumber = findViewById<View>(R.id.input_collect_number) as EditText
        findViewById<View>(R.id.btn_next).setOnClickListener {
            doNext()
        }
    }

    private fun doNext() {
        val groupName = mInputGroupName.text.toString()
        if (TextUtils.isEmpty(groupName)) {
            return showToast("活动名称不能为空")
        }
        if (CommonUtil.clcZhNumber(groupName) > 20) {
            return showToast("活动名称限20字")
        }

        //TODO 性别限制  费用限制

        val perAmount = mInputPerAmount.text.toString()
        if (TextUtils.isEmpty(perAmount)) {
            return showToast("人均金额不能为空")
        }

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

}