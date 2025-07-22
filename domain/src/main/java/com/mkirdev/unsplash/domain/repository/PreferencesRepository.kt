package com.mkirdev.unsplash.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    suspend fun saveScheduleFlag(isStarted: Boolean)

    suspend fun getScheduleFlag(): Flow<Boolean?>

    suspend fun deleteScheduleFlag()
}