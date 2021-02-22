package com.sj.app.activities.init.perfectInfo

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.sj.app.R
import com.sj.app.activities.BaseActivity
import com.sj.app.activities.init.labelSelect.LabelSelectActivity
import com.sj.app.view.DialogAgeSelect
import com.sj.app.view.DialogProgress
import com.sj.app.view.DialogSexSelect
import com.sj.app.view.ToastUtil

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.0
 * @see
 * @desc  完善资料
 */
class PerfectInfoActivity : BaseActivity(), PerfectInfoView {
    private lateinit var mInputPetName: EditText
    private lateinit var mInputSex: TextView
    private lateinit var mInputAge: TextView
    private lateinit var mVHeadPhoto: ImageView
    private lateinit var presenter: PerfectInfoPresenter
    private lateinit var dialogProgress: DialogProgress


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfect_custinfo)
        initView()
        initData()
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "完善资料"
        mInputPetName = findViewById<View>(R.id.input_pet_name) as EditText
        mInputSex = findViewById<View>(R.id.input_sex) as TextView
        findViewById<View>(R.id.panel_sex).setOnClickListener {
            showSexSelectDialog()
        }
        mInputAge = findViewById<View>(R.id.input_age) as TextView
        findViewById<View>(R.id.panel_age).setOnClickListener {
            showAgeSelectDialog()
        }
        mVHeadPhoto = findViewById<View>(R.id.v_head_photo) as ImageView
        mVHeadPhoto.setOnClickListener {
            doSelectPhoto()
        }
        findViewById<View>(R.id.btn_next).setOnClickListener {
            doNext()
        }
    }

    private fun initData() {
        presenter = PerfectInfoPresenter(this)
        dialogProgress = DialogProgress.getInstance(this)
    }

    private fun doSelectPhoto() {
    }

    private var ageSelectDialog: DialogAgeSelect? = null
    private fun showAgeSelectDialog() {
        if (ageSelectDialog == null) {
            ageSelectDialog = DialogAgeSelect(this)
            ageSelectDialog!!.itemListener = object : DialogAgeSelect.AgeSelectListener {
                override fun onSelect(positon: Int, data: String) {
                    mInputAge.text = data
                }
            }
        }
        ageSelectDialog!!.show()
    }

    private var sexSelectDialog: DialogSexSelect? = null
    private fun showSexSelectDialog() {
        if (sexSelectDialog == null) {
            sexSelectDialog = DialogSexSelect(this)
            sexSelectDialog!!.itemListener = object : DialogSexSelect.SexSelectListener {
                override fun onSelect(sex: DialogSexSelect.Sex) {
                    when (sex) {
                        DialogSexSelect.Sex.MALE -> {
                            mInputSex.text = "男"
                        }
                        DialogSexSelect.Sex.FEMALE -> {
                            mInputSex.text = "女"
                        }
                    }
                }
            }
        }
        sexSelectDialog!!.show()
    }

    private fun doNext() {
        val petName = mInputPetName.text.toString()
        if (TextUtils.isEmpty(petName)) {
            return showToast("请输入你的昵称")
        }
        val selectSex = mInputSex.text.toString()
        if (TextUtils.isEmpty(selectSex)) {
            return showToast("请选择性别,性别选定保存后,不可修改")
        }
        val sexArgs = if (selectSex == DialogSexSelect.Sex.MALE.desc) "1" else "0"

        val age = mInputAge.text.toString()
        if (TextUtils.isEmpty(age)) {
            return showToast("请选年龄,年龄确认保存之后,不可修改")
        }
//        val drawable = mVHeadPhoto.drawable
//        if (drawable == null || drawable !is BitmapDrawable) {
//            return showToast("请选择头像")
//        }
//        val bitmap = drawable.bitmap
//        // TODO
//        presenter.saveCustInfo(petName, sex, age, bitmap)

        saveCustInfoSuccess("")
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

    override fun saveCustInfoSuccess(msg: String) {
        jumpToActivity(LabelSelectActivity::class.java)
    }

    override fun showToast(msg: String) {
        ToastUtil.show(this, msg)
    }

}