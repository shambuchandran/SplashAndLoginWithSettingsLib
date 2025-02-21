package onboarding.data

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class PreferenceDataServiceImpl(
    private val settings: Settings
) : PreferenceDataService {
    private val PROFILE_KEY = "user_profile"
    private val profileFlow = MutableStateFlow<UserProfile?>(null)

    init {
        profileFlow.value = getStoredProfile()
    }


    override suspend fun saveProfile(userProfile: UserProfile) {
        settings.putString(PROFILE_KEY, Json.encodeToString(userProfile))
        profileFlow.value = userProfile
    }

    override fun getProfile(): Flow<UserProfile?> = profileFlow.asStateFlow()

    override suspend fun clearProfile() {
        settings.remove(PROFILE_KEY)
        profileFlow.value = null
    }
    private fun getStoredProfile(): UserProfile? {
        return settings.getStringOrNull(PROFILE_KEY)?.let {
            Json.decodeFromString<UserProfile>(it)
        }
    }
}