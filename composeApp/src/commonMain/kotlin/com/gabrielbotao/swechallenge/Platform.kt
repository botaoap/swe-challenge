@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.gabrielbotao.swechallenge

interface Platform {
    val name: String
    val type: PlatFormTypeEnum
}

expect fun getPlatform(): Platform

expect enum class PlatFormTypeEnum {
    IOS,
    ANDROID
}