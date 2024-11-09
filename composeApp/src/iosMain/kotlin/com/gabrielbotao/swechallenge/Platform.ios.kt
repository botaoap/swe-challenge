package com.gabrielbotao.swechallenge

import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val type: PlatFormTypeEnum = PlatFormTypeEnum.IOS
}

actual fun getPlatform(): Platform = IOSPlatform()