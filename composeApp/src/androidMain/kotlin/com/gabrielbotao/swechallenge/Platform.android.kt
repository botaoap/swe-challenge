package com.gabrielbotao.swechallenge

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val type: PlatFormTypeEnum = PlatFormTypeEnum.ANDROID
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual enum class PlatFormTypeEnum {
    IOS, ANDROID
}