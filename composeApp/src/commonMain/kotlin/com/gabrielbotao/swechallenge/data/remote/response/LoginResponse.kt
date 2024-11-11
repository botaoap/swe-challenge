package com.gabrielbotao.swechallenge.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val username: String
)