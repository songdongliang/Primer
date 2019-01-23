package com.sdl.primer.livedata3

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * create by songdongliang on 2019/1/4
 */
class MainViewModelFactory(private val autoRefresh: AutoRefresh)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (ViewModel::class.java.isAssignableFrom(modelClass)) {
            return MainViewModel(autoRefresh) as T
        }
        return super.create(modelClass)
    }
}