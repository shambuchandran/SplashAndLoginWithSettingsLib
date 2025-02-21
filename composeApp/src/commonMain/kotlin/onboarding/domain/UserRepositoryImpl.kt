package onboarding.domain

import kotlinx.coroutines.flow.Flow
import onboarding.data.PreferenceDataService
import onboarding.data.UserProfile

class UserRepositoryImpl(private val preferenceDataService: PreferenceDataService) :
    UserRepository {
    override suspend fun saveUserProfile(userProfile: UserProfile) {
        preferenceDataService.saveProfile(userProfile)
    }

    override fun getUserProfile(): Flow<UserProfile?> {

        return preferenceDataService.getProfile()
    }

    override suspend fun clearUserProfile() {
        preferenceDataService.clearProfile()
    }
}