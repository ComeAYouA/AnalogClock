package com.example.analogclock

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
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

    private val seconds = 37
    private val minutes = 16
    private val hours = 0

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

        dial.setBounds(
            hPadding,
            vPadding,
            size + hPadding,
            size + vPadding
        )

        dial.draw(canvas)


        canvas.save()
        canvas.rotate(hours / 60.0f * 360.0f, width / 2.0f, height / 2.0f)
        hourHand.setBounds(
            hPadding,
            vPadding,
            size + hPadding,
            size + vPadding
        )
        hourHand.draw(canvas)
        canvas.restore()

        canvas.save()
        canvas.rotate(minutes / 60.0f * 360.0f, width / 2.0f, height / 2.0f)
        minuteHand.setBounds(
            hPadding,
            vPadding,
            size + hPadding,
            size + vPadding
        )
        minuteHand.draw(canvas)
        canvas.restore()

        canvas.save()
        canvas.rotate(seconds / 60.0f * 360.0f, width / 2.0f, height / 2.0f)
        secondHand.setBounds(
            hPadding,
            vPadding,
            size + hPadding,
            size + vPadding
        )
        secondHand.draw(canvas)
        canvas.restore()
    }



}