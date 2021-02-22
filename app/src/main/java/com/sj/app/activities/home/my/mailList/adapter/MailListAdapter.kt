package com.sj.app.activities.home.my.mailList.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sj.app.R
import com.supwisdom.orderlib.bean.ApplyForGroup

/**
 * @author gqy
 * @date 2019/8/14
 * @since 1.0.0
 * @see
 * @desc  通讯录
 */
class MailListAdapter(context: Context) : RecyclerView.Adapter<MailListAdapter.Holder>() {
    private val list = arrayListOf<ApplyForGroup>()
    private val inflater = LayoutInflater.from(context)

    fun clear() {
        list.clear()
    }

    fun add(data: ArrayList<ApplyForGroup>?) {
        if (data != null && data.isNotEmpty()) {
            list.addAll(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val rootView = inflater.inflate(R.layout.item_mail_list, parent, false)
        return Holder(rootView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = list[position]
        holder.vApplyInfo.text = "${data.applyUser}申请加入${data.applygroup}群聊"
    }


    inner class Holder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var vApplyInfo = itemView.findViewById(R.id.v_apply_info) as TextView
    }
}