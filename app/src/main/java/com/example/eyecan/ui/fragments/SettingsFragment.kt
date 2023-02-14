package com.example.eyecan.ui.fragments

import TTS
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.eyecan.R
import com.example.eyecan.utils.Constants.PITCH_VALUE
import com.example.eyecan.utils.Constants.SPEED_VALUE

class SettingsFragment: Fragment(R.layout.fragment_settings) {
    private lateinit var tts: TTS
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val speedSeekBar = view.findViewById<SeekBar>(R.id.speedSeekBar)
        val pitchSeekBar = view.findViewById<SeekBar>(R.id.pitchSeekBar)
        val speedPitchValue = view.findViewById<TextView>(R.id.speedPitchValue)
        tts = TTS(requireContext())
        speedPitchValue.text = "Speed: ${tts.getSpeechRate()}x  Pitch: ${tts.getPitch()}x"
        val speedProgressValue = tts.getSpeechRate()*100.0f
        val pitchProgressValue = tts.getPitch()*100.0f
        speedSeekBar.setProgress(speedProgressValue.toInt(),false)
        pitchSeekBar.setProgress(pitchProgressValue.toInt(),false)

        speedSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val speed = progress / 100.0f
                tts.setSpeechRate(speed)
                speedPitchValue.text = "Speed: ${speed}x  Pitch: ${tts.getPitch()}x"
                SPEED_VALUE = tts.getSpeechRate()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        pitchSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val pitch = progress / 100.0f
                tts.setPitch(pitch)
                speedPitchValue.text = "Speed: ${tts.getSpeechRate()}x  Pitch: ${pitch}x"
                PITCH_VALUE = tts.getSpeechRate()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        view.findViewById<Button>(R.id.testButton).setOnClickListener {
            tts.speak(true)
        }


    }

    override fun onStart() {
        super.onStart()
        tts.init(requireContext())
    }

}