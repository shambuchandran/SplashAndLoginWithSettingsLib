package onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import onboarding.data.UserProfile
import onboarding.domain.UserRepository

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.getUserProfile().collect { userProfile ->
                delay(10000)
                _uiState.value = _uiState.value.copy(isLoggedIn = userProfile != null)
                println("User: $userProfile")
            }
        }
    }

    fun login(mobileNumber: String, otp: String) {
        if (mobileNumber.isBlank() || otp.isBlank()) {
            updateState(error = "Please fill all fields")
            return
        }

        if (otp != "12345") {
            updateState(error = "Invalid OTP")
            return
        }

        updateState(isLoading = true)

        viewModelScope.launch {
            runCatching {
                userRepository.saveUserProfile(UserProfile(mobileNumber))
            }.onSuccess {
                updateState(isLoggedIn = true)
            }.onFailure {
                updateState(error = "Login failed: ${it.message}")
            }
        }
    }

    private fun updateState(
        isLoading: Boolean = false,
        isLoggedIn: Boolean = false,
        error: String? = null
    ) {
        _uiState.value = LoginUiState(
            isLoading = isLoading,
            isLoggedIn = isLoggedIn,
            error = error
        )
    }
}

data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null
)
