package com.sj.app.activities.home.my.examineActivity.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sj.app.R
import com.supwisdom.orderlib.bean.Detail

/**
 * @author gqy
 * @date 2019/8/14
 * @since 1.0.0
 * @see
 * @desc  审批报名活动详情
 */
class ExamineDetailAdapter(context: Context) : RecyclerView.Adapter<ExamineDetailAdapter.Holder>() {
    private val context = context
    private val list = arrayListOf<Detail>()
    private val inflater = LayoutInflater.from(context)

    fun clear() {
        list.clear()
    }

    fun add(data: List<Detail>?) {
        if (data != null && data.isNotEmpty()) {
            list.addAll(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val rootView = inflater.inflate(R.layout.item_examine_detail, parent, false)
        return Holder(rootView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = list[position]
        Glide.with(context)
            .load(data.image)
            .apply(RequestOptions().circleCrop().placeholder(R.drawable.loading_white))
            .into(holder.vHeadPhoto)
        holder.vPetname.text = data.petname

        holder.vBtn1.visibility = View.VISIBLE
        holder.vBtn2.visibility = View.VISIBLE
    }

    inner class Holder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var vHeadPhoto = itemView.findViewById(R.id.v_headerphoto) as ImageView
        var vPetname = itemView.findViewById(R.id.v_petname) as TextView
        var vBtn1 = itemView.findViewById(R.id.v_btn1) as Button
        var vBtn2 = itemView.findViewById(R.id.v_btn2) as Button
    }
}