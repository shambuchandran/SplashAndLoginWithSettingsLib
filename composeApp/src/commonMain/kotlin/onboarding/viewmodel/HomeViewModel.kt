package onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import onboarding.data.UserProfile
import onboarding.domain.UserRepository

class HomeViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    val userProfile: StateFlow<UserProfile?> = userRepository.getUserProfile()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun logout(onLogout:() -> Unit) {
        viewModelScope.launch {
            userRepository.clearUserProfile()
            onLogout()
        }
    }
}