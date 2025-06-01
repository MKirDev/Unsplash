package com.mkirdev.unsplash.auth

import com.mkirdev.unsplash.auth.impl.AuthContract
import com.mkirdev.unsplash.auth.impl.AuthViewModel
import com.mkirdev.unsplash.auth.utils.MainDispatcherRule
import com.mkirdev.unsplash.domain.usecases.auth.GetAuthRequestUseCase
import com.mkirdev.unsplash.domain.usecases.auth.GetSavedTokenUseCase
import com.mkirdev.unsplash.domain.usecases.auth.PerformTokensRequestUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AuthViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var getAuthRequestUseCase: GetAuthRequestUseCase
    private lateinit var getSavedTokenUseCase: GetSavedTokenUseCase
    private lateinit var performTokensRequestUseCase: PerformTokensRequestUseCase
    private lateinit var viewModel: AuthViewModel

    @Before
    fun setup() {
        getAuthRequestUseCase = mockk(relaxed = true)
        getSavedTokenUseCase = mockk(relaxed = true)
        performTokensRequestUseCase = mockk(relaxed = true)
        viewModel = AuthViewModel(
            getAuthRequestUseCase = getAuthRequestUseCase,
            getSavedTokenUseCase = getSavedTokenUseCase,
            performTokensRequestUseCase = performTokensRequestUseCase
        )
    }

    @After
    fun teardown() {
        clearAllMocks()
    }

    @Test
    fun uiState_whenInitialized_thenIdle() {
        Assert.assertEquals(
            AuthContract.State.Idle, viewModel.uiState.value
        )
    }

    @Test
    fun uiState_whenOnNotificationCloseRequested_thenIdle() {

        viewModel.handleEvent(AuthContract.Event.NotificationReceivedEvent)

        Assert.assertEquals(
            AuthContract.State.Idle, viewModel.uiState.value
        )
    }

    @Test
    fun uiState_whenCodeReceivedFailure_thenError() {
        viewModel.handleEvent(AuthContract.Event.CodeReceivedFailureEvent(error = errorStub))

        Assert.assertEquals(
            AuthContract.State.Error(errorStub), viewModel.uiState.value
        )
    }

    @Test
    fun effect_whenInitialized_thenNull() {
        Assert.assertEquals(
            null, viewModel.effect.value
        )
    }

    @Test
    fun effect_whenAuthRequestSuccessRequested_thenAuth() = runTest {
        coEvery { getAuthRequestUseCase.execute() } returns codeStub
        viewModel.handleEvent(AuthContract.Event.AuthRequestedEvent)

        Assert.assertEquals(
            AuthContract.Effect.Auth(codeStub), viewModel.effect.value
        )
    }

    @Test
    fun effect_whenAuthCodeSuccessRequested_thenPostAuth() = runTest {

        coEvery { getSavedTokenUseCase.execute() } returns codeStub

        viewModel.handleEvent(AuthContract.Event.CodeReceivedSuccessEvent(codeStub))

        Assert.assertEquals(
            AuthContract.Effect.PostAuth, viewModel.effect.value
        )
    }

}

private val errorStub = "Test error"
private val codeStub = "Test auth code"

