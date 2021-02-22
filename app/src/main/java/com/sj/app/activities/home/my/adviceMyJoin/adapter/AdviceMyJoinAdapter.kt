package com.sj.app.activities.home.my.adviceMyJoin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sj.app.R
import com.supwisdom.orderlib.bean.MyStart

/**
 * @author gqy
 * @date 2019/8/14
 * @since 1.0.0
 * @see
 * @desc  我报名的征求
 */
class AdviceMyJoinAdapter(context: Context) : RecyclerView.Adapter<AdviceMyJoinAdapter.Holder>() {
    private val context = context
    private val list = arrayListOf<MyStart>()
    private val inflater = LayoutInflater.from(context)

    fun clear() {
        list.clear()
    }

    fun add(data: ArrayList<MyStart>?) {
        if (data != null && data.isNotEmpty()) {
            list.addAll(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val rootView = inflater.inflate(R.layout.item_advice_my_join, parent, false)
        return Holder(rootView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = list[position]
        Glide.with(context)
            .load(data.image)
            .apply(RequestOptions().placeholder(R.drawable.loading_white))
            .into(holder.vHeadPhoto)
        holder.vPetName.text = data.petname
        holder.vAdviceName.text = data.actname
        holder.vJoinNumber.text = "[${data.joincnt}人报名]"
    }


    inner class Holder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var vHeadPhoto = itemView.findViewById(R.id.v_head_photo) as ImageView
        var vPetName = itemView.findViewById(R.id.v_petname) as TextView
        var vAdviceName = itemView.findViewById(R.id.v_advice_name) as TextView
        var vJoinNumber = itemView.findViewById(R.id.v_join_number) as TextView
    }
}