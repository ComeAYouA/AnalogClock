package com.example.analogclock

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Canvas
import android.text.format.DateUtils.SECOND_IN_MILLIS
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import java.util.Calendar
import java.util.TimeZone
import kotlin.math.min


class AnalogClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr){

    private val dial = AppCompatResources.getDrawable(context, R.drawable.ic_dial)!!
    private val secondHand = AppCompatResources.getDrawable(context, R.drawable.ic_second_hand)!!
    private val minuteHand = AppCompatResources.getDrawable(context, R.drawable.ic_minute_hand)!!
    private val hourHand = AppCompatResources.getDrawable(context, R.drawable.ic_hour_hand)!!

    private val dialWidth = dial.intrinsicWidth
    private val dialHeight = dial.intrinsicHeight

    private val time = Calendar.getInstance()
    private var seconds = time.get(Calendar.SECOND)
    private var minutes = time.get(Calendar.MINUTE)
    private var hours = time.get(Calendar.HOUR)

    private val clockTick = object: Runnable{
        override fun run() {
            onTimeChanged()
            val now = System.currentTimeMillis()
            val delay = SECOND_IN_MILLIS - now % SECOND_IN_MILLIS;
            postDelayed(this, delay)
        }
    }

    private val timeChangeReceiver = TimeChangeBroadCastReceiver(
        onTimeZoneChanged = { timeZone ->
            time.timeZone = TimeZone.getTimeZone(timeZone)
            onTimeChanged()
        }
    )

    init {
        Log.d("myTag", "view init")
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_TIME_CHANGED)
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED)

        context.registerReceiver(
            timeChangeReceiver,
            filter
        )

        clockTick.run()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        var widthScale = 1.0
        var heightScale = 1.0

        if (widthMode != MeasureSpec.UNSPECIFIED) {
            widthScale = widthSize / dialWidth.toDouble()
        }

        if (heightMode != MeasureSpec.UNSPECIFIED) {
            heightScale = heightSize / dialHeight.toDouble()
        }

        val scale = min(widthScale, heightScale)

        setMeasuredDimension(
            resolveSize((dialWidth * scale).toInt(), widthMeasureSpec),
            resolveSize((dialHeight * scale).toInt(), heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas) {
        val hSize = right - left
        val vSize = bottom - top
        val size = min(hSize, vSize)
        val hPadding = (hSize - size) / 2
        val vPadding = (vSize - size) / 2

        drawDial(canvas, size, hPadding, vPadding)
        drawHourHand(canvas, size, hPadding, vPadding)
        drawMinuteHand(canvas, size, hPadding, vPadding)
        drawSecondHand(canvas, size, hPadding, vPadding)
    }

    private fun drawDial(
        canvas: Canvas,
        size: Int,
        horizontalPadding: Int,
        verticalPadding: Int
    ){
        dial.setBounds(
            horizontalPadding,
            verticalPadding,
            size + horizontalPadding,
            size + verticalPadding
        )

        dial.draw(canvas)
    }

    private fun drawHourHand(
        canvas: Canvas,
        size: Int,
        horizontalPadding: Int,
        verticalPadding: Int
    ){
        canvas.save()
        canvas.rotate(
            hours / 12.0f * 360.0f + minutes / 12 / 60.0f * 360.0f,
            width / 2.0f,
            height / 2.0f
        )

        hourHand.setBounds(
            horizontalPadding,
            verticalPadding,
            size + horizontalPadding,
            size + verticalPadding
        )

        hourHand.draw(canvas)
        canvas.restore()
    }

    private fun drawMinuteHand(
        canvas: Canvas,
        size: Int,
        horizontalPadding: Int,
        verticalPadding: Int
    ){
        canvas.save()
        canvas.rotate(
            minutes / 60.0f * 360.0f,
            width / 2.0f,
            height / 2.0f
        )

        minuteHand.setBounds(
            horizontalPadding,
            verticalPadding,
            size + horizontalPadding,
            size + verticalPadding
        )

        minuteHand.draw(canvas)
        canvas.restore()
    }

    private fun drawSecondHand(
        canvas: Canvas,
        size: Int,
        horizontalPadding: Int,
        verticalPadding: Int
    ){
        canvas.save()
        canvas.rotate(
            seconds / 60.0f * 360.0f,
            width / 2.0f,
            height / 2.0f
        )

        secondHand.setBounds(
            horizontalPadding,
            verticalPadding,
            size + horizontalPadding,
            size + verticalPadding
        )

        secondHand.draw(canvas)
        canvas.restore()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        context.unregisterReceiver(timeChangeReceiver)
        removeCallbacks(clockTick)
    }

    fun onTimeChanged(){
        time.timeInMillis = System.currentTimeMillis()

        seconds = time.get(Calendar.SECOND)
        minutes = time.get(Calendar.MINUTE)
        hours = time.get(Calendar.HOUR)

        invalidate()
    }


}