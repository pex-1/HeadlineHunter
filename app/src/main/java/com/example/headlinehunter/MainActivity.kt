package com.example.headlinehunter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.headlinehunter.core.presentation.components.HeadlineHunterBottomBar
import com.example.headlinehunter.ui.theme.HeadlineHunterTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainViewModel by inject()

        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            val state = viewModel.theme.collectAsStateWithLifecycle()

            HeadlineHunterTheme(darkTheme = state.value) {

                val navController = rememberNavController()
                HeadlineHunterBottomBar(navController)
            }
        }

    }
}