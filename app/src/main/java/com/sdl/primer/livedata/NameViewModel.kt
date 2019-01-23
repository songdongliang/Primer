package com.sdl.primer.livedata

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * create by songdongliang on 2019/1/2
 */
class NameViewModel: ViewModel() {

    private var mCurrentName: MutableLiveData<String>? = null

    fun getCurrentName(): MutableLiveData<String> {
        return mCurrentName ?: MutableLiveData()
    }
}