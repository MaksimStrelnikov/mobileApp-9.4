package dev.tp_94.mobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.tp_94.mobileapp.selfmadecake.presentation.SelfMadeCakeScreen
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val isLoading = mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                isLoading.value
            }
        }

        setContent {
            LaunchedEffect(Unit) {
                delay(2000)
                isLoading.value = false
            }

            val navController = rememberNavController()
            NavHost(navController, startDestination = "main") {
                composable("main") { SelfMadeCakeScreen() }
            }
        }
    }
}
