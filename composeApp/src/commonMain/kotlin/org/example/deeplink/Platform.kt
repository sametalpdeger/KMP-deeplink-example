package org.example.deeplink

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform