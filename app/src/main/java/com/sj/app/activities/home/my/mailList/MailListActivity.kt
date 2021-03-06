package com.sj.app.activities.home.my.mailList

import android.app.Activity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.sj.app.R
import com.sj.app.activities.BaseActivity
import com.sj.app.activities.home.my.mailList.adapter.MailListAdapter
import com.sj.app.view.DialogProgress
import com.sj.app.view.RecyclerViewUtil
import com.sj.app.view.ToastUtil
import com.supwisdom.commonlib.utils.CommonUtil

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.0
 * @see
 * @desc  通讯录
 */
class MailListActivity : BaseActivity(), MailListView {
    private lateinit var adapter: MailListAdapter
    private lateinit var recyMailList: RecyclerView
    private lateinit var vRefreshLayout: SwipeRefreshLayout
    private lateinit var dialogProgress: DialogProgress
    private var presenter: MailListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail_list)
        initView()
        initData()
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "通讯录"

        vRefreshLayout = findViewById<View>(R.id.vRefreshLayout) as SwipeRefreshLayout
        vRefreshLayout.setProgressViewEndTarget(false, 100)
        vRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT)
        vRefreshLayout.setColorSchemeResources(R.color.black)
        vRefreshLayout.setOnRefreshListener {
            loadData(true)
        }
        recyMailList = findViewById<RecyclerView>(R.id.v_recy_join)

        val lineManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyMailList.layoutManager = lineManager
        recyMailList.addItemDecoration(
            RecyclerViewUtil.ItemDecoration(
                CommonUtil.dip2px(5f),
                CommonUtil.dip2px(5f),
                0,
                0
            )
        )
        adapter = MailListAdapter(this)
        recyMailList.adapter = adapter
    }

    private fun loadData(isRefresh: Boolean) {
        presenter!!.getMailList(isRefresh)
    }

    override fun loadSuccess(isRefresh: Boolean) {
        vRefreshLayout.isRefreshing = false
        if (isRefresh) {
            adapter.clear()
        }
        adapter.add(null)
        adapter.notifyDataSetChanged()
    }

    private fun initData() {
        presenter = MailListPresenter(this)
        dialogProgress = DialogProgress.getInstance(this)
        loadData(true)
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