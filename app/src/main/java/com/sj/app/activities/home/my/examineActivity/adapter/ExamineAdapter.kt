package com.sj.app.activities.home.my.examineActivity.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sj.app.R
import com.sj.app.view.RecyclerViewUtil
import com.supwisdom.commonlib.utils.CommonUtil
import com.supwisdom.orderlib.bean.ExamineData

/**
 * @author gqy
 * @date 2019/8/14
 * @since 1.0.0
 * @see
 * @desc  审批报名活动
 */
class ExamineAdapter(context: Context) : RecyclerView.Adapter<ExamineAdapter.Holder>() {
    private val context = context
    private val list = arrayListOf<ExamineData>()
    private val inflater = LayoutInflater.from(context)

    fun clear() {
        list.clear()
    }

    fun add(data: List<ExamineData>?) {
        if (data != null && data.isNotEmpty()) {
            list.addAll(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val rootView = inflater.inflate(R.layout.item_examine_activity, parent, false)
        return Holder(rootView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = list[position]
        holder.vRecyDetail.adapter = null
        val itemDecorationCount = holder.vRecyDetail.itemDecorationCount
        for (i in 0 until itemDecorationCount) {
            holder.vRecyDetail.removeItemDecorationAt(i)
        }

        holder.vTitle.text = data.title
        val lineLayManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.vRecyDetail.layoutManager = lineLayManager
        val bottomMargin = CommonUtil.dip2px(10f)
        holder.vRecyDetail.addItemDecoration(RecyclerViewUtil.ItemDecoration(0, bottomMargin, 0, 0))

        val detailAdapter = ExamineDetailAdapter(context)
        detailAdapter.clear()
        detailAdapter.add(data.list)
        holder.vRecyDetail.adapter = detailAdapter

        /**
         * 计算子View的高度
         */
        val childCount = holder.vRecyDetail.childCount
        if (childCount > 0) {
            var totalHeight = 0
            for (i in 0 until childCount) {
                val childView = holder.vRecyDetail.getChildAt(i)
                childView.measure(0, 0)
                totalHeight += childView.measuredHeight
            }
            val layoutParam = holder.vRecyDetail.layoutParams
            layoutParam.height = totalHeight + childCount * bottomMargin
            holder.vRecyDetail.layoutParams = layoutParam
        }
    }

    inner class Holder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var vTitle = itemView.findViewById(R.id.v_title) as TextView
        var vRecyDetail = itemView.findViewById(R.id.recy_detail) as RecyclerView
    }
}