package com.sdl.primer.livedata3

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sdl.primer.BR
import com.sdl.primer.R
import com.sdl.primer.databinding.AdapterMainBinding

/**
 * create by songdongliang on 2019/1/4
 */
class MainAdapter(private val beans: ArrayList<Data1>)
    : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MainViewHolder {
        val viewDataBinding = DataBindingUtil
                .inflate<AdapterMainBinding>(
                        LayoutInflater.from(p0.context),
                        R.layout.adapter_main,
                        p0,
                        false)
        return MainViewHolder(viewDataBinding)
    }

    override fun getItemCount(): Int {
        return beans.size
    }

    override fun onBindViewHolder(p0: MainViewHolder, p1: Int) {
        p0.viewDataBinding.setVariable(BR.data1, beans[p1])
        p0.viewDataBinding.executePendingBindings()
    }


    class MainViewHolder(viewDataBinding: ViewDataBinding)
        : RecyclerView.ViewHolder(viewDataBinding.root) {
        val viewDataBinding: ViewDataBinding = viewDataBinding
    }
}