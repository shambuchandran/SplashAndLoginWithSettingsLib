package onboarding.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import onboarding.data.PreferenceDataService
import onboarding.data.UserProfile

class UserRepositoryImpl(private val preferenceDataService: PreferenceDataService) :
    UserRepository {
    override suspend fun saveUserProfile(userProfile: UserProfile) {
        preferenceDataService.saveProfile(userProfile)
    }

    override fun getUserProfile(): StateFlow<UserProfile?> {
        return preferenceDataService.getProfile()
    }

    override suspend fun clearUserProfile() {
        preferenceDataService.clearProfile()
    }
}