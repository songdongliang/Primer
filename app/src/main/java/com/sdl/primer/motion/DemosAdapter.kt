package com.sdl.primer.motion

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.sdl.primer.R

/**
 * create by songdongliang on 2019/3/22
 */
class DemosAdapter(private val dataset: Array<DemosAdapter.Demo>)
    : RecyclerView.Adapter<DemosAdapter.ViewHolder>() {

    data class Demo(val title: String, val description: String, val layout: Int = 0,
                    val activity: Class<*> = DemoActivity::class.java) {
        constructor(title: String, description: String, activity: Class<*> = DemoActivity::class.java) : this(title, description, 0, activity)
    }

    class ViewHolder(val layout: ConstraintLayout): RecyclerView.ViewHolder(layout) {
        var title = layout.findViewById<TextView>(R.id.title)
        var description = layout.findViewById<TextView>(R.id.description)
        var layoutFileId = 0
        var activity: Class<*>? = null

        init {
            layout.setOnClickListener {
                val context = it?.context as MotionActivity
                activity?.let {
                    context.start(it, layoutFileId)
                }
            }
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val row = LayoutInflater.from(p0.context).inflate(R.layout.row, p0, false)
                as ConstraintLayout
        return ViewHolder(row)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataset[position].title
        holder.description.text = dataset[position].description
        holder.layoutFileId = dataset[position].layout
        holder.activity = dataset[position].activity
    }
}