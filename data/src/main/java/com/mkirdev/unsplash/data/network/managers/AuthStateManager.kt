package com.mkirdev.unsplash.data.network.managers

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AuthStateManager {

    private val _logoutEvents = MutableSharedFlow<Unit>(replay = 0)
    val logoutEvents: SharedFlow<Unit> = _logoutEvents.asSharedFlow()

    suspend fun emitLogout() {
        _logoutEvents.emit(Unit)
    }
}