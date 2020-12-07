package com.example.hella

// api에서 실행된 결과를 받아오는 부분
data class Login(
    val statusCode: String,
    val body: String
)