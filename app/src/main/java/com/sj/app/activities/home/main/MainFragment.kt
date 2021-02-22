package com.sj.app.activities.home.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sj.app.R
import com.sj.app.activities.BaseFragment
import com.sj.app.activities.SPApplication
import com.sj.app.activities.home.main.filter.FilterActivity
import com.sj.app.activities.home.HomeActivity
import com.sj.app.activities.home.main.search.SearchActivity
import com.supwisdom.commonlib.bean.LocateCity
import com.supwisdom.orderlib.PhoneTerminal
import com.zaaach.citypicker.CityPicker
import com.zaaach.citypicker.adapter.OnPickListener
import com.zaaach.citypicker.model.City
import com.zaaach.citypicker.model.HotCity
import com.zaaach.citypicker.model.LocateState
import com.zaaach.citypicker.model.LocatedCity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * 首页
 */
class MainFragment : BaseFragment() {
    private val TAG = "MainFragment"

    private var param1: String? = null
    private var param2: String? = null
    private var rootView: View? = null
    private var activity: HomeActivity? = null
    private lateinit var vLocation: TextView

    private lateinit var vMatch: TextView
    private lateinit var vCircle: TextView
    private lateinit var vGroupChat: TextView

    private lateinit var matchFragment: MainMatchFragment
    private lateinit var circleFragment: MainCircleFragment
    private lateinit var groupChatFragment: MainGroupChatFragment
    private val phoneTerminal = SPApplication.getInstance().getPhoneTerminal()

    private var clickTabId = 0

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
            rootView = inflater.inflate(R.layout.home_main_fragment, container, false)
        }
        return rootView!!
    }

    private fun initView(rootView: View) {
        statusBar = rootView.findViewById<View>(R.id.v_statebar)
        statusBar!!.setBackgroundColor(ContextCompat.getColor(context!!, R.color.cl_red))

        rootView.findViewById<View>(R.id.v_search).setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
        vLocation = rootView.findViewById<View>(R.id.v_location) as TextView
        vLocation.setOnClickListener {
            cityPicker.show()
        }

        rootView.findViewById<View>(R.id.v_filter).setOnClickListener {
            val intent = Intent(activity, FilterActivity::class.java)
            startActivity(intent)
        }

        vMatch = rootView.findViewById<View>(R.id.v_match) as TextView
        vMatch.setOnClickListener {
            switchTabView(R.id.v_match)
        }
        vCircle = rootView.findViewById<View>(R.id.v_circle) as TextView
        vCircle.setOnClickListener {
            switchTabView(R.id.v_circle)
        }
        vGroupChat = rootView.findViewById<View>(R.id.v_group_chat) as TextView
        vGroupChat.setOnClickListener {
            switchTabView(R.id.v_group_chat)
        }
        initFragments()
        vMatch.performClick()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(rootView!!)
        initData()
    }

    private fun initData() {
        phoneTerminal.setViewRefreshListener(object : PhoneTerminal.ViewRefreshListener() {
            override fun onLocationChanged(locatedCity: LocateCity) {
                phoneTerminal.locatedCity = locatedCity
                cityPicker.locateComplete(
                    LocatedCity(locatedCity.city, locatedCity.province, locatedCity.cityCode),
                    LocateState.SUCCESS
                )
                vLocation.text = locatedCity.city
            }

            override fun onLocateError() {
                cityPicker.locateComplete(LocatedCity("定位失败", "未知", "-1"), LocateState.FAILURE)
                if (phoneTerminal.locatedCity == null) {
                    vLocation.text = "定位失败"
                }
            }
        })
        initCityPicker()
        phoneTerminal.startLocation()
    }

    private lateinit var cityPicker: CityPicker
    private fun initCityPicker() {
        //TODO 更新数据
        val hotCities = ArrayList<HotCity>()
        hotCities.add(HotCity("北京", "北京", "101010100"))
        hotCities.add(HotCity("上海", "上海", "101020100"))
        hotCities.add(HotCity("广州", "广东", "101280101"))
        hotCities.add(HotCity("深圳", "广东", "101280601"))
        hotCities.add(HotCity("杭州", "浙江", "101210101"))

        cityPicker = CityPicker.from(this)
            .enableAnimation(true)
            .setHotCities(hotCities)
            .setOnPickListener(object : OnPickListener {
                override fun onPick(position: Int, selectedCity: City) {
                    phoneTerminal.locatedCity = LocateCity(selectedCity.name, selectedCity.province, selectedCity.code)
                    vLocation.text = selectedCity.name
                }

                override fun onCancel() {
                }

                override fun onLocate() {
                    phoneTerminal.startLocation()
                }
            })
    }

    private fun switchTabView(newClickId: Int) {
        if (clickTabId == newClickId) {
            return
        }
        unSelectTabView()
        when (newClickId) {
            R.id.v_match -> {
                vMatch.setTextColor(ContextCompat.getColor(activity!!, R.color.cl_red))
                activity!!.supportFragmentManager.beginTransaction().show(matchFragment).commit()
            }
            R.id.v_circle -> {
                vCircle.setTextColor(ContextCompat.getColor(activity!!, R.color.cl_red))
                activity!!.supportFragmentManager.beginTransaction().show(circleFragment).commit()
            }
            R.id.v_group_chat -> {
                vGroupChat.setTextColor(ContextCompat.getColor(activity!!, R.color.cl_red))
                activity!!.supportFragmentManager.beginTransaction().show(groupChatFragment).commit()
            }
        }
        clickTabId = newClickId
    }

    private fun unSelectTabView() {
        when (clickTabId) {
            R.id.v_match -> {
                vMatch.setTextColor(ContextCompat.getColor(activity!!, R.color.black))
                activity!!.supportFragmentManager.beginTransaction().hide(matchFragment).commit()
            }
            R.id.v_circle -> {
                vCircle.setTextColor(ContextCompat.getColor(activity!!, R.color.black))
                activity!!.supportFragmentManager.beginTransaction().hide(circleFragment).commit()
            }
            R.id.v_group_chat -> {
                vGroupChat.setTextColor(ContextCompat.getColor(activity!!, R.color.black))
                activity!!.supportFragmentManager.beginTransaction().hide(groupChatFragment).commit()
            }
        }
    }

    private fun initFragments() {
        matchFragment = MainMatchFragment.newInstance("", "")
        circleFragment = MainCircleFragment.newInstance("", "")
        groupChatFragment = MainGroupChatFragment.newInstance("", "")

        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.add(R.id.main_container, matchFragment)
        transaction.add(R.id.main_container, circleFragment)
        transaction.add(R.id.main_container, groupChatFragment)

        transaction.hide(matchFragment)
        transaction.hide(circleFragment)
        transaction.hide(groupChatFragment)
        transaction.commit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun onDetach() {
        super.onDetach()
        phoneTerminal.destroyAMapClient()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
