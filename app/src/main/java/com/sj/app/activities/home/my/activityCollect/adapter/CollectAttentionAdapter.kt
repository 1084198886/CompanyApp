package com.sj.app.activities.home.my.activityCollect.adapter

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
 * @desc  收藏和关注的活动
 */
class CollectAttentionAdapter(private val context: Context) : RecyclerView.Adapter<CollectAttentionAdapter.Holder>() {
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
        val rootView = inflater.inflate(R.layout.item_collect_attention, parent, false)
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
            .into(holder.vImage)
        holder.vActiveName.text = "活动名称"
    }


    inner class Holder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var vImage = itemView.findViewById(R.id.v_image) as ImageView
        var vActiveName = itemView.findViewById(R.id.v_active_name) as TextView
    }
}