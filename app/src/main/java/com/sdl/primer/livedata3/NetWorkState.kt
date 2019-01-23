package com.sdl.primer.livedata3

import android.arch.lifecycle.LiveData
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.sdl.primer.net.NetWorkUtil

/**
 * create by songdongliang on 2019/1/7
 */
class NetWorkState(val context: Context): LiveData<Boolean>() {

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            value = NetWorkUtil.isNetworkConnected(context)
        }
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(broadcastReceiver)
    }

    override fun onActive() {
        super.onActive()

        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(broadcastReceiver, intentFilter)
    }
}