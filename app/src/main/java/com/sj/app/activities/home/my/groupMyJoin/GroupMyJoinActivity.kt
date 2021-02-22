package com.sj.app.activities.home.my.groupMyJoin

import android.app.Activity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.sj.app.R
import com.sj.app.activities.BaseActivity
import com.sj.app.activities.home.my.groupMyJoin.adapter.GroupMyJoinAdapter
import com.sj.app.view.DialogProgress
import com.sj.app.view.RecyclerViewUtil
import com.sj.app.view.ToastUtil
import com.supwisdom.commonlib.utils.CommonUtil

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.0
 * @see
 * @desc  我加入的团
 */
class GroupMyJoinActivity : BaseActivity(), GroupMyJoinView {
    private lateinit var adapter: GroupMyJoinAdapter
    private lateinit var recyMyJoin: RecyclerView
    private lateinit var vRefreshLayout: SwipeRefreshLayout
    private lateinit var dialogProgress: DialogProgress
    private var presenter: GroupMyJoinPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_my_join)
        initView()
        initData()
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "我加入的团"

        vRefreshLayout = findViewById<View>(R.id.vRefreshLayout) as SwipeRefreshLayout
        vRefreshLayout.setProgressViewEndTarget(false, 100)
        vRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT)
        vRefreshLayout.setColorSchemeResources(R.color.black)
        vRefreshLayout.setOnRefreshListener {
            loadData(true)
        }
        recyMyJoin = findViewById<RecyclerView>(R.id.v_recy_join)

        val gridManager = GridLayoutManager(this, 2)
        recyMyJoin.layoutManager = gridManager
        recyMyJoin.addItemDecoration(RecyclerViewUtil.ItemDecoration(0, 5, 0, CommonUtil.dip2px(5f)))
        adapter = GroupMyJoinAdapter(this)
        recyMyJoin.adapter = adapter
    }

    private fun loadData(isRefresh: Boolean) {
        presenter!!.getGroupMyJoin(isRefresh)
    }

    override fun loadSuccess(refresh: Boolean) {
        vRefreshLayout.isRefreshing = false
        if (refresh) {
            adapter.clear()
        }
        // TODO
        adapter.add(null)
        adapter.notifyDataSetChanged()
    }

    private fun initData() {
        presenter = GroupMyJoinPresenter(this)
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