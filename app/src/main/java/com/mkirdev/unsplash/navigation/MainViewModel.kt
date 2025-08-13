package com.mkirdev.unsplash.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.mkirdev.unsplash.domain.usecases.auth.GetLogoutEventUseCase
import com.mkirdev.unsplash.domain.usecases.auth.GetSavedTokenUseCase
import com.mkirdev.unsplash.domain.usecases.onboarding.GetOnboardingFlagUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel(
    private val getLogoutEventUseCase: GetLogoutEventUseCase,
    private val getSavedTokenUseCase: GetSavedTokenUseCase,
    private val getOnboardingFlagUseCase: GetOnboardingFlagUseCase
) : ViewModel() {

    private val _onboardingState = MutableStateFlow<Boolean?>(null)

    val onboardingState = _onboardingState.asStateFlow()

    private val _authState = MutableStateFlow<Boolean?>(null)

    val authState = _authState.asStateFlow()

    private val _logoutState = MutableSharedFlow<Unit>()

    val logoutState = _logoutState.asSharedFlow()

    init {
        viewModelScope.launch {

            _onboardingState.update {
                getOnboardingFlagUseCase.execute()
            }

            val token = getSavedTokenUseCase.execute()

            if (token.isNotEmpty()) {
                _authState.update {
                    true
                }
            } else {
                _authState.update {
                    false
                }
            }

            getLogoutEventUseCase.execute().collect {
                _logoutState.emit(it)
            }
        }
    }
}

class MainViewModelFactory @Inject constructor(
    private val getLogoutEventUseCase: GetLogoutEventUseCase,
    private val getSavedTokenUseCase: GetSavedTokenUseCase,
    private val getOnboardingFlagUseCase: GetOnboardingFlagUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return MainViewModel(
            getLogoutEventUseCase = getLogoutEventUseCase,
            getSavedTokenUseCase = getSavedTokenUseCase,
            getOnboardingFlagUseCase = getOnboardingFlagUseCase
        ) as T
    }
}