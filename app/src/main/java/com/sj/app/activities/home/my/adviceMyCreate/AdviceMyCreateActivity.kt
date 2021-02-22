package com.sj.app.activities.home.my.adviceMyCreate

import android.app.Activity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import com.sj.app.R
import com.sj.app.activities.BaseActivity
import com.sj.app.activities.home.my.activityDetail.ActivityDetailActivity
import com.sj.app.activities.home.my.adviceMyCreate.adapter.AdviceMyCreateAdapter
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
 * @desc  我创建的征求
 */
class AdviceMyCreateActivity : BaseActivity(), AdviceMyCreateView {
    private lateinit var adapter: AdviceMyCreateAdapter
    private lateinit var recyMyCreate: RecyclerView
    private lateinit var mVRight: TextView
    private lateinit var mVDelete: TextView
    private lateinit var vRefreshLayout: SwipeRefreshLayout
    private lateinit var dialogProgress: DialogProgress
    private var presenter: AdviceMyCreatePresenter? = null
    private var curBtnState = ButtonState.NORMAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advice_my_create)
        initView()
        initData()
    }

    private fun initView() {
        findViewById<View>(R.id.v_title_back).setOnClickListener {
            finish()
        }
        val mVTitle = findViewById<View>(R.id.v_title) as TextView
        mVTitle.text = "我创建的征求"

        mVRight = findViewById<TextView>(R.id.v_right) as TextView
        mVRight.visibility = View.VISIBLE
        mVRight.text = "管理"
        mVRight.setOnClickListener {
            doSwithBtnState()
        }
        mVDelete = findViewById<TextView>(R.id.v_delete) as TextView
        mVDelete.setOnClickListener {
            doDelete()
        }

        vRefreshLayout = findViewById<View>(R.id.vRefreshLayout) as SwipeRefreshLayout
        vRefreshLayout.setProgressViewEndTarget(false, 100)
        vRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT)
        vRefreshLayout.setColorSchemeResources(R.color.black)
        vRefreshLayout.setOnRefreshListener {
            loadData(true)
        }
        recyMyCreate = findViewById<RecyclerView>(R.id.v_recy_create)

        val gridManager = GridLayoutManager(this, 2)
        recyMyCreate.layoutManager = gridManager

        val divider = CommonUtil.dip2px(5f)
        recyMyCreate.addItemDecoration(
            RecyclerViewUtil.ItemDecoration(
                divider,
                divider,
                divider,
                divider
            )
        )
        adapter = AdviceMyCreateAdapter(this)
        adapter.itemListener = object : AdviceMyCreateAdapter.ItemClickListener {
            override fun clickCallBack(myStart: MyStart, position: Int) {
                if (curBtnState == ButtonState.MANAGE) {
                    myStart.isSelect = !myStart.isSelect
                    adapter.notifyItemChanged(position, "partial")
                } else {
                    jumpToActivity(ActivityDetailActivity::class.java)
                }
            }
        }
        recyMyCreate.adapter = adapter
    }

    private fun doDelete() {
        val dataList = adapter.getData()
        val list = arrayListOf<MyStart>()
        dataList.forEach {
            if (it.isSelect) {
                list.add(it)
            }
        }
        if (list.isNotEmpty()) {
            mVDelete.visibility = View.GONE
            presenter!!.deleteGroupMyStart()
        } else {
            showToast("请选择")
        }
    }

    private fun doSwithBtnState() {
        when (curBtnState) {
            ButtonState.NORMAL -> {
                curBtnState = ButtonState.MANAGE
                mVRight.text = "完成"
                if (adapter.itemCount > 0) {
                    val dataList = adapter.getData()
                    dataList.forEach {
                        it.isVisible = true
                        it.isSelect = false
                    }
                    adapter.notifyDataSetChanged()
                    mVDelete.visibility = View.VISIBLE
                }
            }
            else -> {
                curBtnState = ButtonState.NORMAL
                mVRight.text = "管理"
                if (adapter.itemCount > 0) {
                    val dataList = adapter.getData()
                    dataList.forEach {
                        it.isVisible = false
                        it.isSelect = false
                    }
                    adapter.notifyDataSetChanged()
                    mVDelete.visibility = View.GONE
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK && curBtnState == ButtonState.MANAGE) {
            doSwithBtnState()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun deleteGroupMyStartSuc(msg: String) {
        showToast(msg)
        loadData(true)
    }

    private fun loadData(isRefresh: Boolean) {
        presenter!!.getGroupMyStart(isRefresh)
    }

    override fun loadSuccess(refresh: Boolean, dataList: ArrayList<MyStart>?) {
        vRefreshLayout.isRefreshing = false
        if (curBtnState == ButtonState.MANAGE) {
            doSwithBtnState()
        }
        if (refresh) {
            adapter.clear()
        }
        adapter.add(dataList)
        adapter.notifyDataSetChanged()
    }


    enum class ButtonState {
        NORMAL, MANAGE
    }

    private fun initData() {
        presenter = AdviceMyCreatePresenter(this)
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