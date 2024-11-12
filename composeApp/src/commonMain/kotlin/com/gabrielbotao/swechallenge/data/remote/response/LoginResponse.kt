package com.gabrielbotao.swechallenge.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val image: String
)