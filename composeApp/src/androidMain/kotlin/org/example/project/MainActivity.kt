package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val isLoading = mutableStateOf(true)
        installSplashScreen().setKeepOnScreenCondition{
            isLoading.value
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App(isLoading)
//            val darkTheme = isSystemInDarkTheme()
//            val view = LocalView.current
//            if (!view.isInEditMode) {
//                SideEffect {
//                    val window = this.window
//                    window.statusBarColor = Color.Black.toArgb()
//                    window.navigationBarColor = Color.Black.toArgb()
//                    val wic = WindowCompat.getInsetsController(window, view)
//                    wic.isAppearanceLightStatusBars = false
//                    wic.isAppearanceLightNavigationBars = !darkTheme
//                }
//            }
        }
    }
}


//@Preview
//@Composable
//fun AppAndroidPreview() {
//    App()
//}