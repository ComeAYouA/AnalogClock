package com.example.analogclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TimeChangeBroadCastReceiver(
    val onTimeZoneChanged: (String) -> Unit = {}
): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_TIMEZONE_CHANGED == intent?.action){
            val timeZone = intent.extras?.getString("time-zone")!!
            onTimeZoneChanged(timeZone)
        }
    }
}