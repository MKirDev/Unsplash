package com.mkirdev.unsplash.data.repository

import androidx.datastore.core.CorruptionException
import com.mkirdev.unsplash.data.exceptions.OnboardingException
import com.mkirdev.unsplash.data.repository.onboarding.OnboardingRepositoryImpl
import com.mkirdev.unsplash.data.storages.datastore.onboarding.OnboardingStorage
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingRepositoryTest {

    private lateinit var storage: OnboardingStorage
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
       storage = mockk(relaxed = true)
    }

    @After
    fun teardown() {
        clearAllMocks()
    }

    @Test
    fun addFlagFromStorageIsRequestedOnlyOnce() = runTest(dispatcher) {
        val repository = OnboardingRepositoryImpl(
            onboardingStorage = storage,
            dispatcher = dispatcher,
        )

        val expected = true

        repository.saveFlag(isOnboardingEnded = true)

        coVerify(exactly = 1) { storage.addFlag(expected) }
    }

    @Test
    fun getFlagFromStorageIsRequestedOnlyOnce() = runTest(dispatcher) {
        val repository = OnboardingRepositoryImpl(
            onboardingStorage = storage,
            dispatcher = dispatcher,
        )

        repository.getFlag()

        coVerify(exactly = 1) { storage.getFlag() }
    }

    @Test
    fun clearFromStorageIsRequestedOnlyOnce() = runTest(dispatcher) {
        val repository = OnboardingRepositoryImpl(
            onboardingStorage = storage,
            dispatcher = dispatcher,
        )

        repository.clear()

        coVerify(exactly = 1) { storage.clear() }
    }

    @Test
    fun shouldThrowGetFlagExceptionWhenStorageIsCorrupted() = runTest(dispatcher) {
        val repository = OnboardingRepositoryImpl(
            onboardingStorage = storage,
            dispatcher = dispatcher,
        )

        coEvery { storage.getFlag() } throws CorruptionException("Datastore exception")

        assertThrows(
            "",
            OnboardingException.GetFlagException::class.java
        ) {
            runTest(dispatcher) { repository.getFlag() }
        }
    }


}