package com.sj.app.activities.home.my

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import co.lujun.androidtagview.TagContainerLayout
import co.lujun.androidtagview.TagView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.sj.app.R
import com.sj.app.activities.BaseFragment
import com.sj.app.activities.home.HomeActivity
import com.sj.app.activities.home.my.activityCollect.CollectAttentionActivity
import com.sj.app.activities.home.my.adviceMyCreate.AdviceMyCreateActivity
import com.sj.app.activities.home.my.adviceMyJoin.AdviceMyJoinActivity
import com.sj.app.activities.home.my.applyForGroup.ApplyForGroupActivity
import com.sj.app.activities.home.my.examineActivity.ExamineActivity
import com.sj.app.activities.home.my.myPublishGroup.MyPublishGroupActivity
import com.sj.app.activities.home.my.groupMyJoin.GroupMyJoinActivity
import com.sj.app.activities.home.my.groupMyStart.GroupMyStartActivity
import com.sj.app.activities.home.my.mailList.MailListActivity
import com.sj.app.activities.home.my.myDynamic.MyDynamicActivity
import com.sj.app.activities.home.my.settting.SettingActivity
import com.sj.app.activities.home.my.userCenter.UserCenterActivity
import com.sj.app.activities.home.my.videoTest.VideoTestActivity
import com.supwisdom.commonlib.utils.CommonUtil
import com.supwisdom.orderlib.entity.DynamicParaRecord

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 *  我的
 */
class MyFragment : BaseFragment(), MyView {
    private var param1: String? = null
    private var param2: String? = null
    private var rootView: View? = null

    private lateinit var mVHeaderphoto: ImageView
    private lateinit var mVPetname: TextView
    private lateinit var mVSignature: TextView
    private lateinit var mTagContainer: TagContainerLayout

    private var activity: HomeActivity? = null
    private lateinit var presenter: MyPresenter

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
            rootView = inflater.inflate(R.layout.home_my_fragment, container, false)
            initView(rootView!!)
        }
        return rootView!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    private fun initData() {
        presenter = MyPresenter(this)
    }

    private fun initView(rootView: View) {
        statusBar = rootView.findViewById<View>(R.id.v_statebar)
        statusBar!!.setBackgroundColor(ContextCompat.getColor(context!!, R.color.light_blue))

        rootView.findViewById<View>(R.id.v_setting).setOnClickListener {
            jumpToActivity(activity!!, SettingActivity::class.java)
        }
        rootView.findViewById<View>(R.id.panel_personal_info).setOnClickListener {
            jumpToActivity(activity!!, UserCenterActivity::class.java)
        }
        mVHeaderphoto = rootView.findViewById(R.id.v_head_photo) as ImageView
        mVPetname = rootView.findViewById(R.id.v_petname) as TextView
        mVSignature = rootView.findViewById(R.id.v_signature) as TextView
        mTagContainer = rootView.findViewById(R.id.tag_container) as TagContainerLayout

        rootView.findViewById<View>(R.id.v_group_i_join).setOnClickListener {
            jumpToActivity(activity!!, GroupMyJoinActivity::class.java)
        }
        rootView.findViewById<View>(R.id.v_group_i_start).setOnClickListener {
            jumpToActivity(activity!!, GroupMyStartActivity::class.java)
        }
        rootView.findViewById<View>(R.id.v_examine_activity).setOnClickListener {
            jumpToActivity(activity!!, ExamineActivity::class.java)
        }

        rootView.findViewById<View>(R.id.v_collected_activity).setOnClickListener {
            jumpToActivity(activity!!, CollectAttentionActivity::class.java)
        }
        rootView.findViewById<View>(R.id.v_create_advice).setOnClickListener {
            jumpToActivity(activity!!, AdviceMyCreateActivity::class.java)
        }
        rootView.findViewById<View>(R.id.v_signup_advice).setOnClickListener {
            jumpToActivity(activity!!, AdviceMyJoinActivity::class.java)
        }


        rootView.findViewById<View>(R.id.v_examine_regist).setOnClickListener {

        }
        rootView.findViewById<View>(R.id.v_apply_for_group).setOnClickListener {
            jumpToActivity(activity!!, ApplyForGroupActivity::class.java)
        }
        rootView.findViewById<View>(R.id.v_my_dynamics).setOnClickListener {
            jumpToActivity(activity!!, MyDynamicActivity::class.java)
        }


        rootView.findViewById<View>(R.id.v_circle_content_manage).setOnClickListener {
            jumpToActivity(activity!!, MyPublishGroupActivity::class.java)
        }
        rootView.findViewById<View>(R.id.v_mail_list).setOnClickListener {
            jumpToActivity(activity!!, MailListActivity::class.java)
        }
    }

    private fun refreshTagViewStyle() {
        mTagContainer.borderColor = ContextCompat.getColor(context!!, R.color.transparent)
        mTagContainer.backgroundColor = ContextCompat.getColor(context!!, R.color.transparent)

        for (i in 0 until mTagContainer.childCount) {
            val tagView = mTagContainer.getChildAt(i) as TagView
            tagView.setTextSize(CommonUtil.sp2px(14).toFloat())
            tagView.setTagTextColor(ContextCompat.getColor(context!!, R.color.black))
            tagView.setHorizontalPadding(CommonUtil.dip2px(5f))
            tagView.setVerticalPadding(CommonUtil.dip2px(5f))
            tagView.isViewClickable = false

            tagView.setBorderWidth(CommonUtil.dip2px(1f).toFloat())
            tagView.setBorderRadius(CommonUtil.dip2px(5f).toFloat())
            tagView.setTagBorderColor(ContextCompat.getColor(context!!, R.color.cl_red))
            tagView.setTagBackgroundColor(ContextCompat.getColor(context!!, R.color.transparent))
        }
    }

    override fun getMyActivity(): Activity {
        return activity!!
    }

    override fun showMyInfo(myInfo: DynamicParaRecord) {
        Glide.with(this)
            .load(myInfo.headphoto)
            .apply(RequestOptions().circleCrop().placeholder(R.drawable.loading_white))
            .into(mVHeaderphoto)

        mVPetname.text = myInfo.petname ?: "--"
        mVSignature.text = myInfo.signature ?: "--"

        val tagList = arrayListOf<String>()
        if (!TextUtils.isEmpty(myInfo.city)) {
            tagList.add(myInfo.city!!)
        }
        tagList.add("${myInfo.age}岁")
        tagList.add("--星座")
        if (!TextUtils.isEmpty(myInfo.userid)) {
            tagList.add(myInfo.userid!!)
        }

        if (!TextUtils.isEmpty(myInfo.tags)) {
            val tags = presenter.getTagList(tagList, myInfo.tags)
            if (tags != null) {
                mTagContainer.tags = tags
            }
        }
        refreshTagViewStyle()
    }

    override fun onResume() {
        super.onResume()
        presenter.getMyInfo()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as HomeActivity
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
