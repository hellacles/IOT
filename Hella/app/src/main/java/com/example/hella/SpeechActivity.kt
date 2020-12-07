//package com.example.hella
//
//import android.Manifest
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.media.MediaRecorder
//import android.os.Build
//import android.os.Bundle
//import android.os.Handler
//import android.speech.RecognitionListener
//import android.speech.RecognizerIntent
//import android.speech.SpeechRecognizer
//import android.util.Log
//import android.view.View
//import android.widget.ProgressBar
//import android.widget.TextView
//import android.widget.Toast
//import android.widget.ToggleButton
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import java.io.File
//import java.io.IOException
//
//class SpeechActivity : AppCompatActivity(), RecognitionListener {
//
//    private val permission = 100
//    private lateinit var returnedText: TextView
//    private lateinit var toggleButton: ToggleButton
////    private lateinit var progressBar: ProgressBar
//    private lateinit var speech: SpeechRecognizer
//    private lateinit var recognizerIntent: Intent
//
//    private var logTag = "VoiceRecognitionActivity"
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.map)
//
//
//        title = "KotlinApp"
//        returnedText = findViewById(R.id.textView)
////        progressBar = findViewById(R.id.progressBar)
//        toggleButton = findViewById(R.id.toggleButton)
////        progressBar.visibility = View.VISIBLE
//        speech = SpeechRecognizer.createSpeechRecognizer(this) //음성인식 객체
//        Log.i(logTag, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this))
//        speech.setRecognitionListener(this) //음성인식 리스너 등록
//        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH) //음성인식 intent생성
//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "ko-KR") //음성인식 언어 설정
//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
//        toggleButton.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
////                progressBar.visibility = View.VISIBLE
////                progressBar.isIndeterminate = true
//                ActivityCompat.requestPermissions(this@SpeechActivity,
//                        arrayOf(Manifest.permission.RECORD_AUDIO),
//                        permission)
//            } else {
////                progressBar.isIndeterminate = false
////                progressBar.visibility = View.VISIBLE
//                speech.stopListening()
//            }
//        }
//    }
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>,
//                                            grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            permission -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager
//                            .PERMISSION_GRANTED) {
//                speech.startListening(recognizerIntent)
//            } else {
//                Toast.makeText(this@SpeechActivity, "Permission Denied!",
//                        Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//    override fun onStop() {
//
//        super.onStop()
//        speech.destroy()
//        Log.i(logTag, "destroy")
//    }
//    override fun onReadyForSpeech(params: Bundle?) {
//    }
//    override fun onRmsChanged(rmsdB: Float) {
////        progressBar.progress = rmsdB.toInt()
//    }
//    override fun onBufferReceived(buffer: ByteArray?) {
//    }
//    override fun onPartialResults(partialResults: Bundle?) {
//    }
//    override fun onEvent(eventType: Int, params: Bundle?) {
//    }
//    override fun onBeginningOfSpeech() {
//        Log.i(logTag, "onBeginningOfSpeech")
////        progressBar.isIndeterminate = false
////        progressBar.max = 10
////        RecordActivity.startRecording(this)
//    }
//    override fun onEndOfSpeech() {
////        progressBar.isIndeterminate = true
//        toggleButton.isChecked = false
//    }
//    override fun onError(error: Int) {
//        val errorMessage: String = getErrorText(error)
//        Log.d(logTag, "FAILED $errorMessage")
//        returnedText.text = errorMessage
//        toggleButton.isChecked = false
//    }
//    private fun getErrorText(error: Int): String {
//        var message = ""
//        message = when (error) {
//            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
//            SpeechRecognizer.ERROR_CLIENT -> "Client side error"
//            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
//            SpeechRecognizer.ERROR_NETWORK -> "Network error"
//            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
//            SpeechRecognizer.ERROR_NO_MATCH -> "No match"
//            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
//            SpeechRecognizer.ERROR_SERVER -> "error from server"
//            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
//            else -> "Didn't understand, please try again."
//        }
//        return message
//    }
//    override fun onResults(results: Bundle?) {
//        Log.i(logTag, "onResults")
//        val matches = results!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
//        var text = ""
////        for (result in matches!!) text = """
////      $result
////      """.trimIndent()
//        returnedText.text = text
//    }
//
//}
