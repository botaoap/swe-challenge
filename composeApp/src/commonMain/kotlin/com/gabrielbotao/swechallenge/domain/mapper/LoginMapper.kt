package com.gabrielbotao.swechallenge.domain.mapper

import com.gabrielbotao.swechallenge.data.remote.response.LoginResponse
import com.gabrielbotao.swechallenge.domain.model.LoginModel

class LoginMapper {
    fun getLogin(
        response: LoginResponse
    ) = LoginModel(
        username = response.username
    )
}