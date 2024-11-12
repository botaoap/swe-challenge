package com.gabrielbotao.swechallenge.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginModel(
    val username: String,
    val fullName: String,
    val email: String,
    val image: String
)