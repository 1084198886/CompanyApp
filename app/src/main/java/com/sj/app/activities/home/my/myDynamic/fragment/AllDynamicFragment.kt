package com.sj.app.activities.home.my.myDynamic.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.sj.app.R
import com.sj.app.activities.BaseFragment
import com.sj.app.activities.home.my.myDynamic.OnInteractionListener
import com.supwisdom.commonlib.utils.GsonUtil

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * 动态
 */
class AllDynamicFragment : BaseFragment(), DyDataChangeListener {
    private var param1: String? = null
    private var param2: String? = null
    private var rootView: View? = null
    private var listener: OnInteractionListener? = null


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
            rootView = inflater.inflate(R.layout.item_dynamic_fragment, container, false)
            initView(rootView!!)
        }
        return rootView!!
    }

    private lateinit var tv: TextView
    private fun initView(rootView: View) {
        tv = rootView.findViewById<View>(R.id.textView) as TextView
    }

    override fun dynamicDataChanged() {
        val list = listener?.getAll()
        tv.text = GsonUtil.GsonString(list)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllDynamicFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
