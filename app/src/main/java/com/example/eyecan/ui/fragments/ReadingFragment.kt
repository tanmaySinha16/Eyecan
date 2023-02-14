package com.example.eyecan.ui.fragments

import TTS
import android.app.Activity
import android.content.Intent
import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eyecan.R
import com.example.eyecan.utils.Constants.PDF_REQUEST_CODE
import com.example.eyecan.utils.Constants.PITCH_VALUE
import com.example.eyecan.utils.Constants.SPEED_VALUE
import com.github.barteksc.pdfviewer.PDFView
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper



class ReadingFragment:Fragment(R.layout.fragment_reading) {
    private lateinit var pdfView: PDFView
    private lateinit var speakButton: Button
    private lateinit var stopButton: Button
    private lateinit var tts: TTS
    private var isSpeaking = false
    private var firstTime = true
    private lateinit var contentResolver:ContentResolver

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pdfView = view.findViewById(R.id.pdfView)
        speakButton = view.findViewById(R.id.speakButton)
        stopButton = view.findViewById(R.id.stopButton)
        tts = TTS(requireContext())
        contentResolver = requireActivity().contentResolver
        loadPDF()

        pdfView.setOnClickListener {
            if(pdfView.isEmpty())
            {
                loadPDF()
            }
        }


        speakButton.setOnClickListener {

            val text = extractedText
            Log.d("extracted text",text)

            if(!isSpeaking && firstTime) {
                tts.start(text)
                isSpeaking = true
                firstTime = false
                speakButton.text = "PAUSE"
            }
            else if(!isSpeaking && !firstTime){
                tts.resume();
                isSpeaking=true
                speakButton.text = "PAUSE"
            }
            else if(isSpeaking) {
                tts.pause()
                isSpeaking = false;
                speakButton.text = "RESUME"
            }

        }
        stopButton.setOnClickListener {
          findNavController().navigate(R.id.action_readingFragment_to_articlesFragment)

        }

    }
    override fun onStart() {
        super.onStart()
        tts.init(requireContext())
        if(SPEED_VALUE!=0f)
            tts.setSpeechRate(SPEED_VALUE)
        if(PITCH_VALUE!=0f)
            tts.setPitch(PITCH_VALUE)
    }

    override fun onResume() {
        if(SPEED_VALUE!=0f)
            tts.setSpeechRate(SPEED_VALUE)
        if(PITCH_VALUE!=0f)
            tts.setPitch(PITCH_VALUE)
        super.onResume()
    }

    override fun onStop() {
        tts.stop()
        tts.destroy()
        super.onStop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == PDF_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            resultData?.data?.let { uri ->
                extractTextFromPDF(uri)
                pdfView.fromUri(uri)
                    .load()
            }
            Log.d("result",resultData?.data.toString())


        }
    }
    fun loadPDF(){
        val ir = Intent(Intent.ACTION_OPEN_DOCUMENT)
        ir.addCategory(Intent.CATEGORY_OPENABLE)
        ir.setType("application/pdf")
        startActivityForResult(ir, PDF_REQUEST_CODE)
    }
    private var extractedText = ""
    fun extractTextFromPDF(uri:Uri) {
        PDFBoxResourceLoader.init(context)
        val inputStream = contentResolver.openInputStream(uri)
        val document = PDDocument.load(inputStream)
        val stripper = PDFTextStripper()
        val text = stripper.getText(document)
        document.close()
        extractedText = text
        Log.d("Extracted Text",text)
    }


}