package com.sj.app.activities.home.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sj.app.R
import com.sj.app.activities.home.HomeActivity
import com.sj.app.activities.home.main.userInfo.UserInfoActivity
import com.sj.app.utils.AppPublicDef

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * 首页-匹配
 */
class MainMatchFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var rootView: View? = null
    private var activity: HomeActivity? = null

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
            rootView = inflater.inflate(R.layout.home_main_match_fragment, container, false)
            initView(rootView!!)
        }
        return rootView!!
    }

    private fun initView(rootView: View) {
        rootView.findViewById<View>(R.id.v_test).setOnClickListener {
            val intent = Intent(activity!!, UserInfoActivity::class.java)
            intent.putExtra(AppPublicDef.EXTRA_USERID,"")
            startActivity(intent)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainMatchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
