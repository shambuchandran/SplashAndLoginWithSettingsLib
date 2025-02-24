package onboarding.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import onboarding.data.UserProfile

interface UserRepository {
    suspend fun saveUserProfile(userProfile: UserProfile)
    fun getUserProfile():StateFlow<UserProfile?>
    suspend fun clearUserProfile()
}