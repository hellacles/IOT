//package com.example.hella
//
//import android.Manifest
//import android.annotation.SuppressLint
//import android.content.Context
//import android.content.pm.PackageManager
//import android.content.pm.PackageManager.PERMISSION_GRANTED
//import android.graphics.Bitmap
//import android.graphics.drawable.BitmapDrawable
//import android.location.Location
//import android.location.LocationManager
//import android.os.Build
//import android.os.Bundle
//import android.os.Looper
//import android.util.Log
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.google.android.gms.location.*
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.*
//import kotlinx.android.synthetic.main.map.*
//
//class MapActivity : AppCompatActivity(), OnMapReadyCallback {
//
//    val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
//    val PERM_FLAG = 99
//    private lateinit var mMap: GoogleMap
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.map)
//
//        // 권한이 있으면 startProcess 실행
//        if (isPermitted()) {
//            startProcess()
//        } else {
//            ActivityCompat.requestPermissions( this, permissions, PERM_FLAG)
//        }
//    }
//
//    fun isPermitted() : Boolean {
//        for (perm in permissions) {
//            if(ContextCompat.checkSelfPermission(this, perm) != PERMISSION_GRANTED){
//                return false // false 반환 후 권한 요청
//            }
//        }
//        return true
//    }
//
//    fun startProcess() {
//        val mapFragment = supportFragmentManager
//                .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//    }
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        setUpdateLocationListner()
//
//    }
////        setUpdateLocationListner()
////        val descriptor = getDescriptorFromDrawable(R.drawable.marker_green)
//
////        val markerGreen = MarkerOptions()
////            .position(seoul)
////            .title("서울 marker")
////            .icon(descriptor)
//
////        mMap.addMarker(markerGreen)
//
//    // 내 위치를 가져옴
//    lateinit var fusedLocationClient:FusedLocationProviderClient
//
//    lateinit var locationCallback:LocationCallback
//
//    @SuppressLint("MissingPermission")
//    fun setUpdateLocationListner() {
//        val locationRequest = LocationRequest.create()
//        locationRequest.run {
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//            interval = 1000
//            // 1초에 한번 좌표를 가져옴
//        }
//
//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult?) {
//
//                // null이 아닐 경우 실행. let을 실행하면 locationResult를 it로 사옹갸능
//                locationResult?.let {
//                    for((i, location) in it.locations.withIndex()) {
//                        // withIndex로 index를 함께 꺼낼 수 있음.(log를 찍기 위해)
//                        Log.d("location", "$i ${location.latitude}, ${location.longitude}")
//                        setLastLocation(location)
//                    }
//                }
//            }
//        }
//
//        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
//    }
//
//
//    // 카메라 위치
////        val cameraOption = CameraPosition.Builder()
////            .target(seoul)
////            .zoom(15f)
////            .build()
////        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)
////        mMap.moveCamera(camera) // 지도를 보는 좌표계가 이동
//
//
//    // 마커와 카메라
//    fun setLastLocation(location : Location) {
//        val myLocation = LatLng(location.latitude, location.longitude)
//        val markerOption = MarkerOptions()
//                .position(myLocation)
//                .title("My Location")
//        val cameraOption = CameraPosition.Builder()
//                .target(myLocation)
//                .zoom(15f)
//                .build()
//        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)
//        mMap.addMarker(markerOption)
//        mMap.moveCamera(camera)
//    }
//
//
//    override fun onRequestPermissionsResult(
//            requestCode: Int,
//            permissions: Array<out String>,
//            grantResults: IntArray
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
//
//    fun getDescriptorFromDrawable(drawableId : Int) : BitmapDescriptor {
//        var bitmapDrawable:BitmapDrawable
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            bitmapDrawable = getDrawable(drawableId) as BitmapDrawable
//        } else {
//            bitmapDrawable = resources.getDrawable(drawableId) as BitmapDrawable
//        }
//
//        // 마커 크기 변환
//        val scaledBitmap = Bitmap.createScaledBitmap(bitmapDrawable.bitmap, 70, 100, false)
//        return BitmapDescriptorFactory.fromBitmap(scaledBitmap)
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
////class MapActivity : AppCompatActivity() {
////
////    val PERMISSIONS = arrayOf(
////        Manifest.permission.ACCESS_COARSE_LOCATION,
////        Manifest.permission.ACCESS_FINE_LOCATION)
////
////    val REQUEST_PERMISSION_CODE = 1
////
////    val DEFAULT_ZOOM_LEVEL = 17f
////
////    val CITY_HALL = LatLng(37.5662952, 126.97794509999994)
////
////    var googleMap: GoogleMap? = null
////
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_main)
////
////        mapView.onCreate(savedInstanceState)
////
////        if (checkPermissions()) {
////            initMap()
////        } else {
////            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_CODE)
////        }
////
////        myLocationButton.setOnClickListener { onMyLocationButtonClick() }
////    }
////
////    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
////
////        initMap()
////    }
////
////    private fun checkPermissions(): Boolean {
////
////        for (permission in PERMISSIONS) {
////            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
////                return false
////            }
////        }
////        return true
////    }
////
////    @SuppressLint("MissingPermission")
////    fun initMap() {
////        mapView.getMapAsync {
////
////            googleMap = it
////
////            it.uiSettings.isMyLocationButtonEnabled = false
////
////            when {
////                checkPermissions() -> {
////                    it.isMyLocationEnabled = true
////                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL))
////                }
////                else -> {
////                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(CITY_HALL, DEFAULT_ZOOM_LEVEL))
////                }
////            }
////        }
////    }
////
////    @SuppressLint("MissingPermission")
////    fun getMyLocation(): LatLng {
////
////        val locationProvider: String = LocationManager.GPS_PROVIDER
////
////        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
////
////        val lastKnownLocation: Location? = locationManager.getLastKnownLocation(locationProvider)
////
////        return LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
////    }
////
////    private fun onMyLocationButtonClick() {
////        when {
////            checkPermissions() -> googleMap?.moveCamera(
////                CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL)
////            )
////            else -> Toast.makeText(applicationContext, "위치사용권한 설정에 동의해주세요", Toast.LENGTH_LONG).show()
////        }
////    }
////
////    override fun onResume() {
////        super.onResume()
////        mapView.onResume()
////    }
////
////    override fun onPause() {
////        super.onPause()
////        mapView.onPause()
////    }
////
////    override fun onDestroy() {
////        super.onDestroy()
////        mapView.onDestroy()
////    }
////
////    override fun onLowMemory() {
////        super.onLowMemory()
////        mapView.onLowMemory()
////    }
////}
