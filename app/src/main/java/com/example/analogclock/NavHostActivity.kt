package com.example.analogclock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.analogclock.preview.PreviewFragment

class NavHostActivity: AppCompatActivity(), PreviewFragment.Callbacks {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.fragmentContainerView)
    }

    override fun onNextPreviewButtonClicked() {
        navController.navigate(R.id.action_previewFragment_to_fullSizeWatchFragment)
    }


}