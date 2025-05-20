package com.mkirdev.unsplash.domain.usecases.onboarding

import com.mkirdev.unsplash.domain.repository.OnboardingRepository
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetOnboardingFlagUseCaseTest {

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
    fun getFlagFromRepositoryIsRequestedOnlyOnce() = runTest {
        val useCase = GetOnboardingFlagUseCase(onboardingRepository = repositoryMock)

        useCase.execute()

        coVerify(exactly = 1) { repositoryMock.getFlag() }
    }

}