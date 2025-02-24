package onboarding.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface PreferenceDataService {
    suspend fun saveProfile(userProfile: UserProfile)
    fun getProfile():StateFlow<UserProfile?>
    suspend fun clearProfile()
}