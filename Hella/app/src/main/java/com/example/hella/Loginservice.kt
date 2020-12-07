package com.example.hella

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

data class ResponseDTO(var result:String? = null)

interface LoginService{

    //    @FormUrlEncoded
    @Headers("Content-Type: application/json; charset=utf8")
    // POST 방식으로 보낼 때. 실제 주소는 https://iu1gdegfi3.execute-api.us-east-1.amazonaws.com/dev/login이 됨
    @POST("login")
    fun requestLogin(
        // 실제로 값이 보내지는 부분 api에서 함수가 실행되고 반환된 결과는 Call 안의 클래스로 보내짐
        @Body jsonObject: JsonObject
    ) : Call<Login>
}