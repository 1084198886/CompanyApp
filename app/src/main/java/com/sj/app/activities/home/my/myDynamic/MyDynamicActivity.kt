package com.sj.app.activities.home.my.myDynamic

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.TextView
import com.sj.app.R
import com.sj.app.activities.BaseFragmentActivity
import com.sj.app.activities.home.my.myDynamic.adapter.MyFragmentPageAdapter
import com.sj.app.activities.home.my.myDynamic.fragment.*
import com.sj.app.activities.home.my.publishDynamic.PublishDynamicActivity
import com.sj.app.view.DialogProgress
import com.sj.app.view.ToastUtil
import com.supwisdom.orderlib.bean.MyDynamic

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.0
 * @see
 * @desc  我的动态
 */
class MyDynamicActivity : BaseFragmentActivity(), MyDynamicView, OnInteractionListener {
    private lateinit var vTabLayout: TabLayout
    private lateinit var vViewPager: ViewPager
    private lateinit var dialogProgress: DialogProgress
    private var presenter: MyDynamicPresenter? = null
    private val fragmentList = arrayListOf<DyDataChangeListener>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_dynamic)
        initView()
        initData()
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "我的动态"

        val mVRight = findViewById<View>(R.id.v_right) as TextView
        mVRight.visibility = View.VISIBLE
        mVRight.text = "发布动态"
        mVRight.setOnClickListener {
            jumpToActivity(PublishDynamicActivity::class.java)
        }

        vTabLayout = findViewById<View>(R.id.v_tablayout) as TabLayout

        vViewPager = findViewById<View>(R.id.vp_content) as ViewPager
        vViewPager.offscreenPageLimit = 3
        val pageAdapter = MyFragmentPageAdapter(supportFragmentManager)

        val fragments = arrayListOf<Fragment>()
        fragments.add(AllDynamicFragment.newInstance("", ""))
        fragments.add(PhotoDynamicFragment.newInstance("", ""))
        fragments.add(VideoDynamicFragment.newInstance("", ""))
        pageAdapter.setList(fragments)

        fragmentList.clear()
        fragments.forEach {
            fragmentList.add(it as DyDataChangeListener)
        }

        vViewPager.adapter = pageAdapter
        vTabLayout.setupWithViewPager(vViewPager)
        vTabLayout.getTabAt(0)?.text = "全部"
        vTabLayout.getTabAt(1)?.text = "图片"
        vTabLayout.getTabAt(2)?.text = "视频"
    }

    private fun loadData() {
        presenter!!.getMyDynamic()
    }

    override fun loadSuccess(dataList: ArrayList<MyDynamic>?) {
        fragmentList.forEach {
            it.dynamicDataChanged()
        }
    }

    private fun initData() {
        presenter = MyDynamicPresenter(this)
        dialogProgress = DialogProgress.getInstance(this)
        loadData()
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun getAll(): List<String> {
        return presenter!!.getAll()
    }

    override fun getPhotoList(): List<String> {
        return presenter!!.getPhotoList()
    }

    override fun getVideoList(): List<String> {
        return presenter!!.getVideoList()
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