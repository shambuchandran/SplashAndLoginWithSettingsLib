package onboarding.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import onboarding.domain.UserRepository
import onboarding.ui.screens.HomeScreen
import onboarding.ui.screens.LoginScreen
import onboarding.viewmodel.HomeViewModel
import onboarding.viewmodel.LoginViewModel
import org.koin.mp.KoinPlatform.getKoin


sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Home : Screen("home")
}

@Composable
fun MainNavigation(isLoading: MutableState<Boolean>) {
    val navController = rememberNavController()
    val userRepository: UserRepository = getKoin().get()
    var startDestination by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            userRepository.getUserProfile().collect { userProfile ->
                //delay(5000)
                startDestination =
                    if (userProfile != null) Screen.Home.route else Screen.Login.route
                println("Start try $startDestination")
                //delay(5000)
                isLoading.value = false
            }
        } catch (e: Exception) {
            isLoading.value = false
            startDestination = Screen.Login.route
            println(e.toString())
        }
    }

    if (startDestination.isNotEmpty()) {
        println("Start nav $startDestination")
        NavHost(navController, startDestination = startDestination) {
            composable(Screen.Login.route) {
                val viewModel: LoginViewModel = getKoin().get()
                LoginScreen(
                    viewModel = viewModel,
                    onLoginSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Home.route) {
                val viewModel: HomeViewModel = getKoin().get()
                HomeScreen(
                    viewModel = viewModel,
                    onLogout = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    } else {
        Box(
            Modifier.fillMaxSize().background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}