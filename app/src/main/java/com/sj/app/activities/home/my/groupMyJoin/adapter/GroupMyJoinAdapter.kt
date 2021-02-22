package com.sj.app.activities.home.my.groupMyJoin.adapter

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

/**
 * @author gqy
 * @date 2019/8/14
 * @since 1.0.0
 * @see
 * @desc  我加入的团
 */
class GroupMyJoinAdapter(context: Context) : RecyclerView.Adapter<GroupMyJoinAdapter.Holder>() {
    private val context = context
    private val list = arrayListOf<String>()
    private val inflater = LayoutInflater.from(context)

    fun clear() {
        list.clear()
    }

    fun add(data: List<String>?) {
        if (data != null && data.isNotEmpty()) {
            list.addAll(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val rootView = inflater.inflate(R.layout.item_group_my_join, parent, false)
        return Holder(rootView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = list[position]
        Glide.with(context)
            .load("https://gss2.bdstatic.com/-fo3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=0f270fffba19ebc4c078719fba1da8c1/37d12f2eb9389b5039d4d0c88c35e5dde7116e04.jpg")
            .apply(RequestOptions().placeholder(R.drawable.loading_white))
            .into(holder.vHeadPhoto)
        holder.vPetName.text = "用户昵称"
        holder.vAdviceName.text = "征求名称"

        holder.vJoinNumber.text = "报名人数[$]"
    }


    inner class Holder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var vHeadPhoto = itemView.findViewById(R.id.v_head_photo) as ImageView
        var vPetName = itemView.findViewById(R.id.v_petname) as TextView
        var vAdviceName = itemView.findViewById(R.id.v_advice_name) as TextView
        var vJoinNumber= itemView.findViewById(R.id.v_join_number) as TextView
    }
}