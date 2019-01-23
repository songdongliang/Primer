package com.sdl.primer.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sdl.primer.R
import java.util.*

/**
 * create by songdongliang on 2018/10/9
 */
class SimpleTitleBehaviorAdapter(var list: MutableList<String>, var context: Context): RecyclerView.Adapter<SimpleTitleBehaviorAdapter.MyViewHolder>() {

    private val colors by lazy {
        arrayOf(0x123456,0x538DD9,0xD825DB,0x765876,0x357954)
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_text_height_150, p0, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        (p0.itemView as TextView).text = list[p1]
        p0.itemView.setBackgroundColor(colors[Random().nextInt(5)])
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}