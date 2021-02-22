package com.sj.app.activities.home.main.filter

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.sj.app.R
import com.sj.app.activities.BaseActivity
import co.lujun.androidtagview.TagContainerLayout
import co.lujun.androidtagview.TagView
import com.sj.app.view.DialogAgeRangeSelect
import com.sj.app.view.DialogLocationSelect
import com.sj.app.view.ToastUtil
import com.supwisdom.commonlib.utils.CommonUtil

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.0
 * @see
 * @desc 筛选
 */
class FilterActivity : BaseActivity() {
    private lateinit var mVAgeDistance: TextView
    private lateinit var mVLocation: TextView
    private lateinit var mTagContainer: TagContainerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        initView()
        initData()
    }

    private fun initData() {
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "筛选"

        findViewById<View>(R.id.panel_age_distance).setOnClickListener {
            showAgeRangeSelectDialog()
        }
        mVAgeDistance = findViewById<View>(R.id.v_age_distance) as TextView
        findViewById<View>(R.id.panel_location).setOnClickListener {
            showLocationSelectDialog()
        }
        mVLocation = findViewById<View>(R.id.v_location) as TextView
        mTagContainer = findViewById<View>(R.id.v_tag_container) as TagContainerLayout

        val tagList = arrayListOf<String>()
        tagList.add("tag2")
        tagList.add("tag3")
        tagList.add("tag5ffffffffff")
        tagList.add("tag5ffffffff")
        tagList.add("tag5ffffff")
        tagList.add("tag5fffff")
        mTagContainer.tags = tagList

        findViewById<View>(R.id.btn_ok).setOnClickListener {

        }
        refreshTagViewStyle()
    }

    private fun refreshTagViewStyle() {
        mTagContainer.borderColor = ContextCompat.getColor(this, R.color.transparent)
        mTagContainer.backgroundColor = ContextCompat.getColor(this, R.color.transparent)
        mTagContainer.isTagViewClickable = true
        mTagContainer.setOnTagClickListener(object : TagView.OnTagClickListener {
            override fun onTagLongClick(position: Int, text: String) {
            }

            override fun onTagClick(position: Int, text: String) {
                ToastUtil.show(this@FilterActivity, text)
            }
        })

        for (i in 0 until mTagContainer.childCount) {
            val tagView = mTagContainer.getChildAt(i) as TagView
            tagView.setTextSize(CommonUtil.sp2px(14).toFloat())
            tagView.setTagTextColor(ContextCompat.getColor(this, R.color.black))
            tagView.setHorizontalPadding(CommonUtil.dip2px(5f))
            tagView.setVerticalPadding(CommonUtil.dip2px(5f))

            tagView.setBorderWidth(CommonUtil.dip2px(1f).toFloat())
            tagView.isViewClickable = true
            tagView.setBorderRadius(CommonUtil.dip2px(5f).toFloat())
            tagView.setTagBorderColor(ContextCompat.getColor(this, R.color.cl_red))
            tagView.setTagBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
        }
    }

    private var ageRangeSelectDialog: DialogAgeRangeSelect? = null
    private fun showAgeRangeSelectDialog() {
        if (ageRangeSelectDialog == null) {
            ageRangeSelectDialog = DialogAgeRangeSelect(this)
            ageRangeSelectDialog!!.itemListener = object : DialogAgeRangeSelect.AgeRangeSelectListener {
                override fun onSelect() {
                }
            }
        }
        ageRangeSelectDialog!!.show()
    }

    private var locationSelectDialog: DialogLocationSelect? = null
    private fun showLocationSelectDialog() {
        if (locationSelectDialog == null) {
            locationSelectDialog = DialogLocationSelect(this)
            locationSelectDialog!!.itemListener = object : DialogLocationSelect.LocationSelectListener {
                override fun onSelect() {
                }
            }
        }
        locationSelectDialog!!.show()
    }


}