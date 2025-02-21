package onboarding.domain

import kotlinx.coroutines.flow.Flow
import onboarding.data.UserProfile

interface UserRepository {
    suspend fun saveUserProfile(userProfile: UserProfile)
    fun getUserProfile():Flow<UserProfile?>
    suspend fun clearUserProfile()
}