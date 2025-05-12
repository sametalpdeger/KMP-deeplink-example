package org.example.deeplink

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main(args: Array<String>? = null) {
    println("args: $args")
    println("args.size: ${args?.size}")
    println("args: ${args?.contentToString()}")

    configureDeeplink()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "deeplink",
        ) {
            App()
        }
    }
}