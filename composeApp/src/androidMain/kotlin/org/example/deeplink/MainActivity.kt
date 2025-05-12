package org.example.deeplink

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val uri = intent.data
        if (uri != null) {
            // Handle the deep link URI
            Log.d("DeepLink", "Received deep link: $uri")
            // You can extract query parameters or path segments from the URI
            // val param = uri.getQueryParameter("some_parameter")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navCtrl = rememberNavController()

            NavHost(
                navController = navCtrl,
                startDestination = HomeScreen,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                composable<HomeScreen> {
                    HomeScreen()
                }

                composable<DeepLinkScreen>(
                    deepLinks = listOf(
                        navDeepLink<DeepLinkScreen>(
                            basePath = "http://192.168.1.113:4050/deeplink",
                        )
                    )
                ) {
                    DeepLinkScreenView(it.arguments?.getString("token") ?: "")
                }
            }
        }
    }
}

@Serializable
data object HomeScreen

@Serializable
data class DeepLinkScreen(val token: String)