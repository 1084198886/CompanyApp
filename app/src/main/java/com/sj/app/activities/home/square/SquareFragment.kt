package com.sj.app.activities.home.square

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sj.app.R
import com.sj.app.activities.BaseFragment
import com.sj.app.activities.home.HomeActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * 广场
 */
class SquereFragment: BaseFragment() {
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
            rootView = inflater.inflate(R.layout.home_square_fragment, container, false)
            initView(rootView!!)
        }
        return rootView!!
    }

    private fun initView(rootView: View) {
        statusBar = rootView.findViewById<View>(R.id.v_statebar)
        statusBar!!.setBackgroundColor(ContextCompat.getColor(context!!, R.color.light_green5))
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
            SquereFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
