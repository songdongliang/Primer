package com.sdl.primer.livedata2

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sdl.primer.BR
import com.sdl.primer.R
import com.sdl.primer.databinding.AdapterRecyclerviewBinding

/**
 * create by songdongliang on 2019/1/4
 */
class RecyclerViewAdapter(private val teachers: ArrayList<Teacher2>)
    : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerViewHolder {
        val viewDataBinding = DataBindingUtil
                .inflate<AdapterRecyclerviewBinding>(
                        LayoutInflater.from(p0.context),
                        R.layout.adapter_recyclerview,
                        p0,
                        false)
        return RecyclerViewHolder(viewDataBinding)
    }

    override fun getItemCount(): Int {
        return teachers.size
    }

    override fun onBindViewHolder(p0: RecyclerViewHolder, p1: Int) {
        p0.dataBinding.setVariable(BR.teacher, teachers[p1])
        p0.dataBinding.setVariable(BR.handlers, MyHandlers())
        p0.dataBinding.executePendingBindings()
    }


    inner class RecyclerViewHolder(viewDataBinding: ViewDataBinding)
        : RecyclerView.ViewHolder(viewDataBinding.root) {
        var dataBinding: ViewDataBinding = viewDataBinding
    }

}