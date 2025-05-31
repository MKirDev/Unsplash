package com.mkirdev.unsplash.onboarding.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.mkirdev.unsplash.domain.usecases.onboarding.GetOnboardingFlagUseCase
import com.mkirdev.unsplash.domain.usecases.onboarding.SaveOnboardingFlagUseCase
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class OnboardingViewModel(
    private val saveOnboardingFlagUseCase: SaveOnboardingFlagUseCase,
    private val getOnboardingFlagUseCase: GetOnboardingFlagUseCase
) : ViewModel(), OnboardingContract {

    private val _uiState = MutableStateFlow<OnboardingContract.State>(OnboardingContract.State.Idle)
    override val uiState: StateFlow<OnboardingContract.State> = _uiState.asStateFlow()

    private val _effect = MutableStateFlow<OnboardingContract.Effect?>(null)
    override val effect: StateFlow<OnboardingContract.Effect?> = _effect.asStateFlow()

    init {
        _uiState.update {
            OnboardingContract.State.Onboarding(
                pages = persistentListOf(
                    OnboardingPage.First, OnboardingPage.Second, OnboardingPage.Third
                ),
                isError = false
            )
        }
    }

    override fun handleEvent(event: OnboardingContract.Event) {
        when (event) {
            OnboardingContract.Event.AuthOpenedEvent -> onAuth()
            OnboardingContract.Event.FieldClosedEvent -> onCloseFieldClick()
        }
    }

    override fun resetEffect() {
        _effect.update { null }
    }

    private fun onAuth() {
        viewModelScope.launch {
            try {
                saveOnboardingFlagUseCase.execute(true)
                if ((_uiState.value as OnboardingContract.State.Onboarding).isError) {
                    _uiState.update {
                        (it as OnboardingContract.State.Onboarding).copy(
                            isError = false
                        )
                    }
                }
            } catch (t: Throwable) {
                _uiState.update {
                    (it as OnboardingContract.State.Onboarding).copy(
                        isError = true
                    )
                }
            } finally {
                val isOnboardingEnded = getFlag()
                if (isOnboardingEnded) _effect.update { OnboardingContract.Effect.Auth }
            }
        }
    }

    private suspend fun getFlag(): Boolean {
        return getOnboardingFlagUseCase.execute()
    }

    private fun onCloseFieldClick() {
        _uiState.update {
            (it as OnboardingContract.State.Onboarding).copy(
                isError = false
            )
        }
    }

}

internal class OnboardingViewModelFactory(
    private val saveOnboardingFlagUseCase: SaveOnboardingFlagUseCase,
    private val getOnboardingFlagUseCase: GetOnboardingFlagUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return OnboardingViewModel(
            saveOnboardingFlagUseCase = saveOnboardingFlagUseCase,
            getOnboardingFlagUseCase = getOnboardingFlagUseCase
        ) as T
    }
}