package com.sj.app.activities.home.my.adviceMyJoin

import android.app.Activity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.sj.app.R
import com.sj.app.activities.BaseActivity
import com.sj.app.activities.home.my.adviceMyJoin.adapter.AdviceMyJoinAdapter
import com.sj.app.view.DialogProgress
import com.sj.app.view.RecyclerViewUtil
import com.sj.app.view.ToastUtil
import com.supwisdom.commonlib.utils.CommonUtil
import com.supwisdom.orderlib.bean.MyStart

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.0
 * @see
 * @desc  我报名的征求
 */
class AdviceMyJoinActivity : BaseActivity(), AdviceMyJoinView {
    private lateinit var adapter: AdviceMyJoinAdapter
    private lateinit var recyMyJoin: RecyclerView
    private lateinit var vRefreshLayout: SwipeRefreshLayout
    private lateinit var dialogProgress: DialogProgress
    private var presenter: AdviceMyJoinPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advice_my_join)
        initView()
        initData()
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "我报名的征求"

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
        adapter = AdviceMyJoinAdapter(this)
        recyMyJoin.adapter = adapter
    }

    private fun loadData(isRefresh: Boolean) {
        presenter!!.getAdviceMyJoin(isRefresh)
    }

    override fun loadSuccess(refresh: Boolean, dataList: ArrayList<MyStart>?) {
        vRefreshLayout.isRefreshing = false
        if (refresh) {
            adapter.clear()
        }
        adapter.add(dataList)
        adapter.notifyDataSetChanged()
    }

    private fun initData() {
        presenter = AdviceMyJoinPresenter(this)
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