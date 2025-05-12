package org.example.deeplink

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "deeplink",
    ) {
        App()
    }
}