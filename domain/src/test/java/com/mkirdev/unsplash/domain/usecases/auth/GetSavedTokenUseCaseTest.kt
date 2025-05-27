package com.mkirdev.unsplash.domain.usecases.auth

import com.mkirdev.unsplash.domain.repository.AuthRepository
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetSavedTokenUseCaseTest {

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
    fun getSavedTokenFromRepositoryIsRequestedOnlyOnce() = runTest {
        val useCase = GetSavedTokenUseCase(repository = repositoryMock)

        useCase.execute()

        coVerify(exactly = 1) { repositoryMock.getSavedToken() }
    }
}