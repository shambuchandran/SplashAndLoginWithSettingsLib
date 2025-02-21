package onboarding.di

import com.russhwolf.settings.Settings
import onboarding.data.PreferenceDataService
import onboarding.data.PreferenceDataServiceImpl
import onboarding.domain.UserRepository
import onboarding.domain.UserRepositoryImpl
import onboarding.viewmodel.LoginViewModel
import onboarding.viewmodel.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}

val appModule = module {
    single<Settings> { Settings() }
    single<PreferenceDataService> { PreferenceDataServiceImpl(settings = get()) }
    single<UserRepository> { UserRepositoryImpl(preferenceDataService = get()) }
    factory { LoginViewModel(userRepository = get()) }
    factory { HomeViewModel(userRepository = get()) }
}