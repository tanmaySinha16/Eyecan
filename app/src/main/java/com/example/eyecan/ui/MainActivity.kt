 package com.example.eyecan.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eyecan.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.NonCancellable.start
import java.util.*


 class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setupWithNavController(findViewById<FrameLayout>(R.id.navHostFragment).findNavController())
        findViewById<FrameLayout>(R.id.navHostFragment).findNavController()
            .addOnDestinationChangedListener{ _,destination,_ ->
                when(destination.id) {
                    R.id.settingsFragment,R.id.articlesFragment ->
                        findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility=
                            View.VISIBLE
                    else -> findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility=
                        View.GONE
                }
            }
    }


}