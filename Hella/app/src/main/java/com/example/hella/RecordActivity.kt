//package com.example.hella
//
//import android.Manifest
//import android.content.Context
//import android.content.pm.PackageManager
//import android.media.MediaPlayer
//import android.media.MediaRecorder
//import android.os.Build
//import android.os.Bundle
//import android.os.Handler
//import android.os.SystemClock
//import android.util.Log
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//import okhttp3.internal.Internal.instance
//import java.io.File
//import java.io.IOException
//
//class RecordActivity : AppCompatActivity() {
//    companion object {
//        lateinit var instance: RecordActivity
//            private set
//    }
//
//
//
//    private var mRecorder: MediaRecorder? = null
//    //    private var mPlayer: MediaPlayer? = null
//    private var fileName: String? = null
//    //    private var lastProgress = 0
//    private val mHandler = Handler()
//    private val RECORD_AUDIO_REQUEST_CODE = 101
////    private var isPlaying = false
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        instance = this
//        fun context(): Context = applicationContext
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getPermissionToRecordAudio()
//        }
//    }
//
//    private fun startRecording() {
//        mRecorder = MediaRecorder()
//        mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
//        mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
//        val root = android.os.Environment.getExternalStorageDirectory()
//        val file = File(root.absolutePath + "/AndroidCodility/Audios")
//        if (!file.exists()) {
//            file.mkdirs()
//        }
//
//        fileName = root.absolutePath + "/AndroidCodility/Audios/" + (System.currentTimeMillis().toString() + ".wav")
////        Log.d("filename", fileName)
//        mRecorder!!.setOutputFile(fileName)
//        mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
//
//        try {
//            mRecorder!!.prepare()
//            mRecorder!!.start()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
////        lastProgress = 0
////        seekBar.progress = 0
////        stopPlaying()
////        // making the imageView a stop button starting the chronometer
////        chronometer.base = SystemClock.elapsedRealtime()
////        chronometer.start()
//    }
//
//    private fun stopRecording() {
//        try {
//            mRecorder!!.stop()
//            mRecorder!!.release()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        mRecorder = null
////        //starting the chronometer
////        chronometer.stop()
////        chronometer.base = SystemClock.elapsedRealtime()
//        //showing the play button
//        Toast.makeText(this, "Recording saved successfully.", Toast.LENGTH_SHORT).show()
//    }
//
//    private fun getPermissionToRecordAudio() {
//        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid checking the build version since Context.checkSelfPermission(...) is only available in Marshmallow
//        // 2) Always check for permission (even if permission has already been granted) since the user can revoke permissions at any time through Settings
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE), RECORD_AUDIO_REQUEST_CODE)
//        }
//    }
//
//
//    // Callback with the request from calling requestPermissions(...)
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        // Make sure it's our original READ_CONTACTS request
//        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
//            if (grantResults.size == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
//                //Toast.makeText(this, "Record Audio permission granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "You must give permissions to use this app. App is exiting.", Toast.LENGTH_SHORT).show()
//                finishAffinity()
//            }
//        }
//    }
//}
