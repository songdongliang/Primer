package com.sdl.primer.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sdl.primer.R

/**
 * create by songdongliang on 2019/2/26
 */
class SimpleTextAdapter(val mContext: Context): RecyclerView.Adapter<SimpleTextAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_text_height_50, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 16
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}