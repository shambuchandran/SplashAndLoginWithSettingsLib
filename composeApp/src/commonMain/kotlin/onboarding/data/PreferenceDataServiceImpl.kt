package onboarding.data

import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class PreferenceDataServiceImpl(
    private val settings: Settings
) : PreferenceDataService {
    private val PROFILE_KEY = "user_profile"

    //    private val profileFlow = MutableStateFlow<UserProfile?>(null)
//
//    init {
//        profileFlow.value = getStoredProfile()
//    }
    private val profileFlow = MutableStateFlow(getStoredProfile())


    override suspend fun saveProfile(userProfile: UserProfile) {
        settings.putString(PROFILE_KEY, Json.encodeToString(userProfile))
        profileFlow.value = userProfile
    }

    override fun getProfile(): StateFlow<UserProfile?> = profileFlow.asStateFlow()

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