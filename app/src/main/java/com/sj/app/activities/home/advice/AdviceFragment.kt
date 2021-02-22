package com.sj.app.activities.home.advice

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.sj.app.R
import com.sj.app.activities.BaseFragment
import com.sj.app.activities.home.HomeActivity
import com.sj.app.activities.home.advice.startGroup.StartGroupActivity
import com.sj.app.view.RecyclerViewUtil
import com.supwisdom.commonlib.utils.CommonUtil
import kotlinx.android.synthetic.main.home_advice_fragment.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * 征求
 */
class AdviceFragment : BaseFragment(), AdviceView {
    private lateinit var presenter: AdvicePresenter
    private lateinit var adapter: AdviceAdapter
    private lateinit var recyAdvices: RecyclerView
    private var param1: String? = null
    private var param2: String? = null
    private var rootView: View? = null
    private var homeActivity: HomeActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (null != rootView) {
            val parent = rootView!!.parent as ViewGroup
            parent.removeView(rootView)
        } else {
            rootView = inflater.inflate(R.layout.home_advice_fragment, container, false)
            initView(rootView!!)
        }
        return rootView!!
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        presenter = AdvicePresenter(this)
        loadData(true)
    }

    private fun initView(rootView: View) {
        statusBar = rootView.findViewById<View>(R.id.v_statebar)
        statusBar!!.setBackgroundColor(ContextCompat.getColor(context!!, R.color.dark_yellow))

        val vRefreshLayout = rootView.findViewById<View>(R.id.vRefreshLayout) as SwipeRefreshLayout
        vRefreshLayout.setProgressViewEndTarget(false, 100)
        vRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT)
        vRefreshLayout.setColorSchemeResources(R.color.black)
        vRefreshLayout.setOnRefreshListener {
            loadData(true)
        }
        recyAdvices = rootView.findViewById<RecyclerView>(R.id.v_recy_advices)

        val gridManager = GridLayoutManager(context, 2)
        recyAdvices.layoutManager = gridManager
        recyAdvices.addItemDecoration(RecyclerViewUtil.ItemDecoration(0, 5, 0, CommonUtil.dip2px(5f)))
        adapter = AdviceAdapter(this)
        recyAdvices.adapter = adapter

        rootView.findViewById<ImageView>(R.id.v_start_group).setOnClickListener {
            jumpToActivity(activity!!, StartGroupActivity::class.java)
        }
    }

    private fun loadData(isPullDownRefresh: Boolean) {
        presenter.getAdviceData(isPullDownRefresh)
    }

    override fun getHomeActivity(): Activity {
        return homeActivity!!
    }

    override fun loadDataSuccess(dataList: List<String>, isPullDownRefresh: Boolean) {
        if (isPullDownRefresh) {
            adapter.setList(dataList)
        } else {
            adapter.add(dataList)
        }
        adapter.notifyDataSetChanged()
    }

    override fun showProgressDialog(msg: String) {
    }

    override fun showToast(msg: String) {
    }

    override fun closeProgressDialog() {
    }

    override fun loadFinished() {
        vRefreshLayout.isRefreshing = false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdviceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
