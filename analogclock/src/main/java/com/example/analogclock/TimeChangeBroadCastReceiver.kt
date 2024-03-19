package com.example.analogclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.TimeZone

class TimeChangeBroadCastReceiver(
    val onTimeZoneChanged: (TimeZone) -> Unit = {}
): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null){
            val timeZone = intent.extras?.getString("time-zone")!!
            onTimeZoneChanged(TimeZone.getTimeZone(timeZone))
        }
    }

}