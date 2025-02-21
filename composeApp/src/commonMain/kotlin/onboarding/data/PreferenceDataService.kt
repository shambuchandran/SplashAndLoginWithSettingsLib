package onboarding.data

import kotlinx.coroutines.flow.Flow

interface PreferenceDataService {
    suspend fun saveProfile(userProfile: UserProfile)
    fun getProfile():Flow<UserProfile?>
    suspend fun clearProfile()
}