package com.mkirdev.unsplash.bottom_menu.impl

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.mkirdev.unsplash.core.navigation.IconicTopDestinations
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Stable
class BottomMenuViewModel(
    private val iconicTopDestination: IconicTopDestinations,
) : ViewModel(), BottomMenuContract {

    private val _uiState = MutableStateFlow<BottomMenuContract.State>(
        BottomMenuContract.State.Idle
    )

    private val _effect = MutableStateFlow<BottomMenuContract.Effect?>(null)

    @Stable
    override val uiState: StateFlow<BottomMenuContract.State> = _uiState.asStateFlow()

    @Stable
    override val effect: StateFlow<BottomMenuContract.Effect?> = _effect.asStateFlow()

    init {
        _uiState.update {
            BottomMenuContract.State.Success(
                iconicTopDestinations = iconicTopDestination.destinations.toPersistentList(),
            )
        }
    }

    override fun handleEvent(event: BottomMenuContract.Event) {
        when (event) {
            is BottomMenuContract.Event.TopLevelClickedEvent -> onTopLevelClicked(event.route)
        }
    }

    override fun resetEffect() {
        _effect.update { null }
    }

    private fun onTopLevelClicked(route: String) {
        _effect.update {
            BottomMenuContract.Effect.TopLevelDestination(route)
        }
    }
}

internal class BottomMenuViewModelFactory(
    private val iconicTopDestination: IconicTopDestinations,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return BottomMenuViewModel(
            iconicTopDestination = iconicTopDestination,
        ) as T
    }
}