package com.example.analogclock.preview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.analogclock.AnalogClockView
import com.example.analogclock.R
import com.example.analogclock.model.PreviewTimeZone
import java.util.TimeZone

class PreviewFragment: Fragment() {

    interface Callbacks{
        fun onNextPreviewButtonClicked()
    }

    private lateinit var nextPreviewButton: Button
    private lateinit var analogClock: AnalogClockView
    private lateinit var diffTimeZoneClockView: AnalogClockView
    private lateinit var timeZoneSpinner: Spinner
    private lateinit var timeZoneSpinnerAdapter: ArrayAdapter<String>

    private var callbacks: Callbacks? = null
    private val onTimeZoneSelectedListener = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            diffTimeZoneClockView.timeZone = TimeZone.getTimeZone(
                PreviewTimeZone.entries[position].timeZone
            )
        }

        override fun onNothingSelected(parent: AdapterView<*>?) = Unit
    }

    override fun onAttach(context: Context) {
        callbacks = context as Callbacks

        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setupTimeZoneSpinnerAdapter()

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_preview,
            container,
            false,
            )

        analogClock = view.findViewById(R.id.analogClockView)
        diffTimeZoneClockView = view.findViewById(R.id.analogClockView2)
        timeZoneSpinner = view.findViewById(R.id.time_zone_spinner)
        nextPreviewButton = view.findViewById(R.id.next_preview_button)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupTimeZoneSpinner()

        nextPreviewButton.setOnClickListener {
            callbacks?.onNextPreviewButtonClicked()
        }
    }

    override fun onDetach() {
        callbacks = null

        super.onDetach()
    }


    private fun setupTimeZoneSpinnerAdapter(){
        timeZoneSpinnerAdapter = ArrayAdapter<String>(requireContext(), R.layout.item_time_zone, R.id.time_zone_text_view)

        PreviewTimeZone.entries.forEach {
            timeZoneSpinnerAdapter.add(it.name)
        }
    }

    private fun setupTimeZoneSpinner(){
        timeZoneSpinner.adapter = timeZoneSpinnerAdapter
        timeZoneSpinner.onItemSelectedListener = onTimeZoneSelectedListener
    }
}