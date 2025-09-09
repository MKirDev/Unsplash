package com.mkirdev.unsplash.bottom_menu.impl

import androidx.compose.runtime.Immutable
import com.mkirdev.unsplash.core.contract.viewmodel.UniFlowViewModel
import com.mkirdev.unsplash.core.navigation.TopLevelDestination
import kotlinx.collections.immutable.ImmutableList

interface BottomMenuContract : UniFlowViewModel<BottomMenuContract.Event, BottomMenuContract.State, BottomMenuContract.Effect?> {

    sealed interface State {
        data object Idle : State

        @Immutable
        data class Success(
            val iconicTopDestinations: ImmutableList<TopLevelDestination>,
            ) : State
    }

    sealed interface Event {

        data class TopLevelClickedEvent(val route: String) : Event
    }

    sealed interface Effect {
        data class TopLevelDestination(val route: String) : Effect
    }
}