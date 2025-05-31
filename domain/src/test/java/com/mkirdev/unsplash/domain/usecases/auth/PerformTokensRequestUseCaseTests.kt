package com.mkirdev.unsplash.domain.usecases.auth

import com.mkirdev.unsplash.domain.repository.AuthRepository
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class PerformTokensRequestUseCaseTests {

    private lateinit var repositoryMock: AuthRepository

    @Before
    fun setup() {
        repositoryMock = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun givenRepository_whenPerformTokenRequestRequested_thenExecutedOnce() = runTest {
        val tokenRequestJson = "{}"

        val useCase = PerformTokensRequestUseCase(repository = repositoryMock)

        useCase.execute(tokenRequestJson)

        coVerify(exactly = 1) { repositoryMock.performTokensRequest(tokenRequestJson) }
    }
}