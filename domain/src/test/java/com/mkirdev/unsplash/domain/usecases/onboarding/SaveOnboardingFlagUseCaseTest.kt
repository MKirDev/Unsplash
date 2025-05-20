package com.mkirdev.unsplash.domain.usecases.onboarding

import com.mkirdev.unsplash.domain.repository.OnboardingRepository
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class SaveOnboardingFlagUseCaseTest {

    private lateinit var repositoryMock: OnboardingRepository

    @Before
    fun setup() {
        repositoryMock = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
    @Test
    fun saveFlagFromRepositoryIsRequestedOnlyOnce() = runTest {
        val useCase = SaveOnboardingFlagUseCase(onboardingRepository = repositoryMock)

        val expected = true

        useCase.execute(expected)

        coVerify(exactly = 1) { repositoryMock.saveFlag(expected) }
    }


}