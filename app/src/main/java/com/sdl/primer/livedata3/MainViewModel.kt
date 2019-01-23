package com.sdl.primer.livedata3

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel

/**
 * create by songdongliang on 2019/1/4
 */
class MainViewModel(private val autoRefresh: AutoRefresh): ViewModel() {

    val id = MutableLiveData<String>()

    var response: LiveData<ArrayList<Data1>>? = null

    private var beans = ArrayList<Data1>()

    val adapter: MainAdapter by lazy {
        MainAdapter(beans)
    }

    init {
        response = Transformations.switchMap(id) { input ->
            if (input == null) {
                MutableLiveData<ArrayList<Data1>>()
            } else {
                Repos.getMainResponse(input)
            }
        }
    }

    fun notifyDataSetChanged(temp: ArrayList<Data1>) {
        beans.addAll(temp)
        autoRefresh.value!!.set(true)
    }

}