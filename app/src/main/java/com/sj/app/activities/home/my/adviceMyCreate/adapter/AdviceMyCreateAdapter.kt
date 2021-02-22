package com.sj.app.activities.home.my.adviceMyCreate.adapter

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
 * @desc  我创建的征求
 */
class AdviceMyCreateAdapter(private val context: Context) : RecyclerView.Adapter<AdviceMyCreateAdapter.Holder>() {
    private val list = arrayListOf<MyStart>()
    private val inflater = LayoutInflater.from(context)

    fun clear() {
        list.clear()
    }

    fun getData(): List<MyStart> {
        return list
    }

    fun add(data: ArrayList<MyStart>?) {
        if (data != null && data.isNotEmpty()) {
            list.addAll(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val rootView = inflater.inflate(R.layout.item_advice_my_create, parent, false)
        return Holder(rootView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            partialRefresh(holder, list[position])
        }
    }

    /**
     * 局部刷新
     */
    private fun partialRefresh(holder: Holder, data: MyStart) {
        if (data.isSelect) {
            holder.vSelect.setBackgroundResource(R.drawable.check_select)
        } else {
            holder.vSelect.setBackgroundResource(R.drawable.check_normal)
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = list[position]
        Glide.with(context)
            .load(data.image)
            .apply(RequestOptions().placeholder(R.drawable.loading_white))
            .into(holder.vHeadPhoto)

        if (data.isVisible) {
            holder.vSelect.visibility = View.VISIBLE
        } else {
            holder.vSelect.visibility = View.GONE
        }
        partialRefresh(holder, data)

        holder.rootView.setOnClickListener {
            itemListener!!.clickCallBack(data, position)
        }
        holder.vPetName.text = data.petname
        holder.vActivityName.text = data.actname

        holder.vJoinNumber.text = "[${data.joincnt}]人报名"
    }

    inner class Holder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rootView = itemView
        var vHeadPhoto = itemView.findViewById(R.id.v_head_photo) as ImageView
        var vSelect = itemView.findViewById(R.id.v_select) as ImageView
        var vPetName = itemView.findViewById(R.id.v_petname) as TextView
        var vActivityName = itemView.findViewById(R.id.v_activity_name) as TextView
        var vJoinNumber = itemView.findViewById(R.id.v_join_number) as TextView
    }

    var itemListener: ItemClickListener? = null

    interface ItemClickListener {
        fun clickCallBack(myStart: MyStart, position: Int)
    }
}