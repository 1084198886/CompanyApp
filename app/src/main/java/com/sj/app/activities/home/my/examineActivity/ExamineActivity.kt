package com.sj.app.activities.home.my.examineActivity

import android.app.Activity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.sj.app.R
import com.sj.app.activities.BaseActivity
import com.sj.app.activities.home.my.examineActivity.adapter.ExamineAdapter
import com.sj.app.view.DialogProgress
import com.sj.app.view.ToastUtil
import com.supwisdom.orderlib.bean.ExamineData

/**
 * @author gqy
 * @date 2019/8/8
 * @since 1.0.0
 * @see
 * @desc  审批报名活动
 */
class ExamineActivity : BaseActivity(), ExamineView {
    private lateinit var adapter: ExamineAdapter
    private lateinit var recyMyExamine: RecyclerView
    private lateinit var vRefreshLayout: SwipeRefreshLayout
    private lateinit var dialogProgress: DialogProgress
    private var presenter: ExaminePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_examine)
        initView()
        initData()
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "审批报名活动"

        vRefreshLayout = findViewById<View>(R.id.vRefreshLayout) as SwipeRefreshLayout
        vRefreshLayout.setProgressViewEndTarget(false, 100)
        vRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT)
        vRefreshLayout.setColorSchemeResources(R.color.black)
        vRefreshLayout.setOnRefreshListener {
            loadData(true)
        }
        recyMyExamine = findViewById<RecyclerView>(R.id.v_recy_examine)

        val lineManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyMyExamine.layoutManager = lineManager

        val dividerDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.drawable_divider_big)!!)
        recyMyExamine.addItemDecoration(dividerDecoration)
        adapter = ExamineAdapter(this)
        recyMyExamine.adapter = adapter
    }

    private fun loadData(isRefresh: Boolean) {
        presenter!!.getExamineActivities(isRefresh)
    }

    override fun loadSuccess(dataList: List<ExamineData>?, refresh: Boolean) {
        vRefreshLayout.isRefreshing = false
        if (refresh) {
            adapter.clear()
        }
        adapter.add(dataList)
        adapter.notifyDataSetChanged()
    }

    private fun initData() {
        presenter = ExaminePresenter(this)
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