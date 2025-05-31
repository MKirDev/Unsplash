package com.mkirdev.unsplash.onboarding

import com.mkirdev.unsplash.domain.usecases.onboarding.GetOnboardingFlagUseCase
import com.mkirdev.unsplash.domain.usecases.onboarding.SaveOnboardingFlagUseCase
import com.mkirdev.unsplash.onboarding.impl.OnboardingContract
import com.mkirdev.unsplash.onboarding.impl.OnboardingPage
import com.mkirdev.unsplash.onboarding.impl.OnboardingViewModel
import com.mkirdev.unsplash.onboarding.utils.MainDispatcherRule
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OnboardingViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var saveOnboardingFlagUseCase: SaveOnboardingFlagUseCase
    private lateinit var getOnboardingFlagUseCase: GetOnboardingFlagUseCase
    private lateinit var viewModel: OnboardingViewModel

    @Before
    fun setup() {
        saveOnboardingFlagUseCase = mockk(relaxed = false)
        getOnboardingFlagUseCase = mockk(relaxed = true)

        viewModel = OnboardingViewModel(
            saveOnboardingFlagUseCase = saveOnboardingFlagUseCase,
            getOnboardingFlagUseCase = getOnboardingFlagUseCase
        )
    }

    @After
    fun teardown() {
        clearAllMocks()
    }

    @Test
    fun uiState_whenInitialized_thenShowsOnboarding() {
        assertEquals(
            OnboardingContract.State.Onboarding(
                pages = pagesStub,
                isError = false
            ), viewModel.uiState.value
        )
    }

    @Test
    fun uiState_whenOnCloseFieldRequested_thenDoesNotShowError() {

        viewModel.handleEvent(OnboardingContract.Event.FieldClosedEvent)

        assertEquals(
            OnboardingContract.State.Onboarding(
                pages = pagesStub,
                isError = false
            ), viewModel.uiState.value
        )
    }

    @Test
    fun effect_whenInitialized_thenNull() {
        assertEquals(
            null, viewModel.effect.value
        )
    }

    @Test
    fun effect_whenOnboardingFlagTrue_thenAuth() = runTest {

        coEvery { getOnboardingFlagUseCase.execute() } returns true

        viewModel.handleEvent(OnboardingContract.Event.AuthOpenedEvent)

        assertEquals(
            OnboardingContract.Effect.Auth, viewModel.effect.value
        )

    }

    @Test
    fun whenOnAuthRequested_thenSaveOnboardingFlagUseCaseExecuted() = runTest {

        viewModel.handleEvent(OnboardingContract.Event.AuthOpenedEvent)

        val expected = true

        coVerify { saveOnboardingFlagUseCase.execute(expected) }
    }

    @Test
    fun whenOnAuthRequested_thenGetOnboardingFlagUseCaseExecuted() = runTest {

        viewModel.handleEvent(OnboardingContract.Event.AuthOpenedEvent)

        coVerify { getOnboardingFlagUseCase.execute() }
    }

}

private val pagesStub = persistentListOf(
    OnboardingPage.First, OnboardingPage.Second, OnboardingPage.Third
)