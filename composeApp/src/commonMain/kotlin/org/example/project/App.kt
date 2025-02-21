package org.example.project

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import onboarding.di.initKoin
import onboarding.navigation.MainNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(isLoading :MutableState<Boolean>) {
    initKoin()
    MaterialTheme {
        MainNavigation(isLoading)
    }
}