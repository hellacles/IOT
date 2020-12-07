package com.example.hella

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var login:Login? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testBtn.setOnClickListener {
            val nextIntent = Intent(this, MapActivity::class.java)
            startActivity(nextIntent)

        }

        // 실제로 api를 연결할 주소
        var retrofit = Retrofit.Builder()
            .baseUrl("https://iu1gdegfi3.execute-api.us-east-1.amazonaws.com/dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // api에게 값을 전송하기 위해 설정하는 변수, LoginService.kt에 실제 interface가 정의됨
        var loginService: LoginService = retrofit.create(LoginService::class.java)

        val button:Button = findViewById(R.id.loginBtn)
        button.setOnClickListener{

            var text1 = findViewById<EditText>(R.id.idEdt).text.toString()
            var text2 = findViewById<EditText>(R.id.pwEdt).text.toString()

            // post 방식의 api 전송을 위해서는 Json형식으로 감싸서 보내줘야 함
            val jsonObject = JsonObject()
            jsonObject.addProperty("userid", text1)
            jsonObject.addProperty("userpw", text2)

            loginService.requestLogin(jsonObject)?.enqueue(object: Callback<Login>{
                override fun onFailure(call: Call<Login>, t: Throwable) {
//                    Log.e("LOGIN", t.message)
                    var dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("error")
                    dialog.setMessage("실패")
                    dialog.show()
                }

                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    login = response.body()
                    var res = response.code()
                    Log.d("LOGIN", "상태코드: " + res)
                    Log.d("LOGIN", "상태코드: " + login)
                    Log.d("LOGIN", "msg : " + login?.statusCode)
                    Log.d("LOGIN", "code : " + login?.body)
                    var dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle(login?.statusCode)
                    dialog.setMessage(login?.body)
                    dialog.show()
                }
            })
        }
    }





}


//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
////        로그인 버튼이 눌렸을때 => 검사 수행.
//        loginBtn.setOnClickListener {
//
////            아이디에 입력된 값 (코틀린에서 xml 속성 접근) / 비번에 입력된 값 저장 (변수)
//
//            val inputId = idEdt.text.toString()
//            val inputPw = pwEdt.text.toString()
//
////            저장된 값들을 검사 => 상황에 따른 행동 (조건문)
//
//            if (inputId == "admin@test.com" && inputPw == "qwer") {
////                로그인 성공
//                Toast.makeText(this, "관리자입니다.", Toast.LENGTH_SHORT).show()
//            }
//            else {
////                아이디건, 비번이건 하나라도 틀린 경우 => 로그인 실패
//                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
//            }
//
//        }
//
//    }
//}

