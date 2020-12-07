package com.example.hella

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.map.*

import android.content.Intent
import android.media.MediaRecorder
import android.nfc.Tag
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.util.*

import androidx.annotation.RequiresApi
import android.media.MediaPlayer

//@RequiresApi(api = Build.VERSION_CODES.M)
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val PERM_FLAG = 99
    private lateinit var mMap: GoogleMap

    private val TAG = "RecognitionListener"
    private val RECORD_REQUEST_CODE = 101

    private var mSpeechRecognizer : SpeechRecognizer? = null
    private var speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

    private var listItems = mutableListOf("")

    private var mRecorder: MediaRecorder? = null
    private var mPlayer: MediaPlayer? = null
    private var fileName: String? = null

    private val RECORD_AUDIO_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getPermissionToRecordAudio()
//        }

        // 권한이 있으면 startProcess 실행
        if (isPermitted()) {
            startProcess()
//            mSpeechRecognizer?.startListening(speechRecognizerIntent)
        } else {
            ActivityCompat.requestPermissions( this, permissions, PERM_FLAG)
//            startProcess()
//            mSpeechRecognizer?.startListening(speechRecognizerIntent)
        }


        listItems.removeAt(0)

        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().getLanguage())
        speechRecognizerIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.packageName)
//        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(applicationContext)
        mSpeechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Log.d(TAG, "onReadyForSpeech")
            }

            override fun onRmsChanged(rmsdB: Float) {
                Log.d(TAG, "onRmsChanged")
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                Log.d(TAG, "onBufferReceived")
            }

            override fun onBeginningOfSpeech() {
                Log.d(TAG, "onBeginningOfSpeech")
                prepareRecording()
                startRecording()
                Log.d(TAG, "RecordingStart")
            }

            override fun onEndOfSpeech() {
                Log.d(TAG, "onEndOfSpeech")
                prepareStop()
                stopRecording()
                Log.d(TAG, "RecordingStop")
            }

            override fun onError(error: Int) {
                var errorCode = ""
                when (error) {
                    SpeechRecognizer.ERROR_AUDIO -> errorCode = "Audio recording error"
                    SpeechRecognizer.ERROR_CLIENT -> errorCode = "Other client side errors"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> errorCode = "Insufficient permissions"
                    SpeechRecognizer.ERROR_NETWORK -> errorCode = "Network related errors"
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> errorCode = "Network operation timed out"
                    SpeechRecognizer.ERROR_NO_MATCH -> errorCode = "No recognition result matched"
                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> errorCode = "RecognitionService busy"
                    SpeechRecognizer.ERROR_SERVER -> errorCode = "Server sends error status"
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> errorCode = "No speech input"
                }
                Log.d("RecognitionListener", "onError:" + errorCode)
                try {
                    GlobalScope.launch {
                        runOnUiThread {
                            mSpeechRecognizer?.cancel()
                        }
                        delay(1000)
                        runOnUiThread {
                            mSpeechRecognizer?.startListening(speechRecognizerIntent)
                        }
                    }
                } catch (ex: Exception) {

                }
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                Log.d(TAG, "onEvent")
            }

            override fun onPartialResults(partialResults: Bundle) {
                Log.d(TAG, "onPartialResults")
                val result = partialResults.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION)
                Log.i(TAG, "onPartialResults" + result.toString())
            }

            override fun onResults(results: Bundle) {
                Log.d(TAG, "onResults")

                val result = results.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION)

                Log.i(TAG, "onResults" + result.toString())
                listItems.add(result.toString())
                if (listItems.count() > 10){
                    listItems.removeAt(0)
                }
                var text = ""
                listItems.forEach {
                    text += it + "\n"
                }
                try {
                    GlobalScope.launch {
                        runOnUiThread {
//                            textViewSpeech.text = text
                        }
                        runOnUiThread {
                            mSpeechRecognizer?.startListening(speechRecognizerIntent)
                        }
                    }
                } catch (ex: Exception) {

                }
            }
        })

        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), RECORD_REQUEST_CODE)
        }
        else {
            Log.i(TAG, "Permission is granted")
            mSpeechRecognizer?.startListening(speechRecognizerIntent)
            Log.i(TAG, "Start listening")
        }
    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        if (requestCode == RECORD_REQUEST_CODE) {
//            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
//                Log.i(TAG, "Permission has been denied by user")
//            }
//            else{
//                Log.i(TAG, "Permission has been granted by user")
//                mSpeechRecognizer?.startListening(speechRecognizerIntent)
//                Log.i(TAG, "Start listening")
//            }
//        }
//    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            RECORD_AUDIO_REQUEST_CODE -> {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    mSpeechRecognizer?.startListening(speechRecognizerIntent)
//                }
//            }
//        }
//    }


    fun isPermitted() : Boolean {
        for (perm in permissions) {
            if(ContextCompat.checkSelfPermission(this, perm) != PERMISSION_GRANTED){
                return false // false 반환 후 권한 요청
            }
        }
        return true
    }

    fun startProcess() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setUpdateLocationListner()

    }

//        setUpdateLocationListner()
//        val descriptor = getDescriptorFromDrawable(R.drawable.marker_green)

//        val markerGreen = MarkerOptions()
//            .position(seoul)
//            .title("서울 marker")
//            .icon(descriptor)

//        mMap.addMarker(markerGreen)

        // 내 위치를 가져옴
        lateinit var fusedLocationClient:FusedLocationProviderClient

        lateinit var locationCallback:LocationCallback

        @SuppressLint("MissingPermission")
        fun setUpdateLocationListner() {
            val locationRequest = LocationRequest.create()
            locationRequest.run {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 1000
                // 1초에 한번 좌표를 가져옴
            }

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {

                    // null이 아닐 경우 실행. let을 실행하면 locationResult를 it로 사옹갸능
                    locationResult?.let {
                        for((i, location) in it.locations.withIndex()) {
                            // withIndex로 index를 함께 꺼낼 수 있음.(log를 찍기 위해)
                            Log.d("location", "$i ${location.latitude}, ${location.longitude}")
                            setLastLocation(location)
                        }
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        }


        // 카메라 위치
//        val cameraOption = CameraPosition.Builder()
//            .target(seoul)
//            .zoom(15f)
//            .build()
//        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)
//        mMap.moveCamera(camera) // 지도를 보는 좌표계가 이동


    // 마커와 카메라
    fun setLastLocation(location : Location) {
        val myLocation = LatLng(location.latitude, location.longitude)
        val markerOption = MarkerOptions()
                .position(myLocation)
                .title("My Location")
        val cameraOption = CameraPosition.Builder()
                .target(myLocation)
                .zoom(15f)
                .build()
        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)
        mMap.addMarker(markerOption)
        mMap.moveCamera(camera)
    }

//    fun restaurantMarker(latitude, ) {
//
////        val restaurantLocation
//    }


//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        when (requestCode) {
//            PERM_FLAG -> {
//                var check = true
//                for (grant in grantResults) {
//                    if (grant != PERMISSION_GRANTED) {
//                        check = false
//                        break
//                    }
//                }
//                if (check) {
//                    startProcess()
//                } else {
//                    Toast.makeText(this, "권한을 승인해야 앱을 사용할 수 있습니다", Toast.LENGTH_LONG).show()
//                    finish()
//                }
//            }
//        }
//    }

    fun getDescriptorFromDrawable(drawableId : Int) : BitmapDescriptor {
        var bitmapDrawable:BitmapDrawable
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bitmapDrawable = getDrawable(drawableId) as BitmapDrawable
        } else {
            bitmapDrawable = resources.getDrawable(drawableId) as BitmapDrawable
        }

        // 마커 크기 변환
        val scaledBitmap = Bitmap.createScaledBitmap(bitmapDrawable.bitmap, 70, 100, false)
        return BitmapDescriptorFactory.fromBitmap(scaledBitmap)
    }

    private fun startRecording() {
        mRecorder = MediaRecorder()
        mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        val root = android.os.Environment.getExternalStorageDirectory()
        val file = File(root.absolutePath + "/RecordTest")
        if (!file.exists()) {
            file.mkdirs()
        }
        fileName = root.absolutePath + "/RecordTest/" + (System.currentTimeMillis().toString() + ".wav")
        Log.d("filename1", fileName)
        mRecorder!!.setOutputFile(fileName)
        mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        try {
            mRecorder!!.prepare()
            mRecorder!!.start()
            Log.d("filename2", fileName)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("error", fileName)

        }
        stopPlaying()

    }

    private fun stopRecording() {
        try {
            mRecorder!!.stop()
            mRecorder!!.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mRecorder = null
        //showing the play button
        Toast.makeText(this, "Recording saved successfully.", Toast.LENGTH_SHORT).show()
    }

    private fun stopPlaying() {
        try {
            mPlayer!!.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mPlayer = null
        //showing the play button
//        imgViewPlay.setImageResource(R.drawable.ic_play_circle)
//        chronometer.stop()
    }

    private fun prepareRecording() {
//        TransitionManager.beginDelayedTransition(llRecorder)
//        imgBtRecord.visibility = View.GONE
//        imgBtStop.visibility = View.VISIBLE
//        llPlay.visibility = View.GONE
    }

    private fun prepareStop() {
//        TransitionManager.beginDelayedTransition(llRecorder)
//        imgBtRecord.visibility = View.VISIBLE
//        imgBtStop.visibility = View.GONE
//        llPlay.visibility = View.VISIBLE
    }

    private fun getPermissionToRecordAudio() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid checking the build version since Context.checkSelfPermission(...) is only available in Marshmallow
        // 2) Always check for permission (even if permission has already been granted) since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE), RECORD_AUDIO_REQUEST_CODE)
        }
    }
}











































//class MapActivity : AppCompatActivity() {
//
//    val PERMISSIONS = arrayOf(
//        Manifest.permission.ACCESS_COARSE_LOCATION,
//        Manifest.permission.ACCESS_FINE_LOCATION)
//
//    val REQUEST_PERMISSION_CODE = 1
//
//    val DEFAULT_ZOOM_LEVEL = 17f
//
//    val CITY_HALL = LatLng(37.5662952, 126.97794509999994)
//
//    var googleMap: GoogleMap? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        mapView.onCreate(savedInstanceState)
//
//        if (checkPermissions()) {
//            initMap()
//        } else {
//            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_CODE)
//        }
//
//        myLocationButton.setOnClickListener { onMyLocationButtonClick() }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        initMap()
//    }
//
//    private fun checkPermissions(): Boolean {
//
//        for (permission in PERMISSIONS) {
//            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
//                return false
//            }
//        }
//        return true
//    }
//
//    @SuppressLint("MissingPermission")
//    fun initMap() {
//        mapView.getMapAsync {
//
//            googleMap = it
//
//            it.uiSettings.isMyLocationButtonEnabled = false
//
//            when {
//                checkPermissions() -> {
//                    it.isMyLocationEnabled = true
//                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL))
//                }
//                else -> {
//                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(CITY_HALL, DEFAULT_ZOOM_LEVEL))
//                }
//            }
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    fun getMyLocation(): LatLng {
//
//        val locationProvider: String = LocationManager.GPS_PROVIDER
//
//        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//
//        val lastKnownLocation: Location? = locationManager.getLastKnownLocation(locationProvider)
//
//        return LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
//    }
//
//    private fun onMyLocationButtonClick() {
//        when {
//            checkPermissions() -> googleMap?.moveCamera(
//                CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL)
//            )
//            else -> Toast.makeText(applicationContext, "위치사용권한 설정에 동의해주세요", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        mapView.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        mapView.onPause()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mapView.onDestroy()
//    }
//
//    override fun onLowMemory() {
//        super.onLowMemory()
//        mapView.onLowMemory()
//    }
//}
