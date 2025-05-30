package com.mkirdev.unsplash.data.repository


import androidx.datastore.core.CorruptionException
import com.mkirdev.unsplash.data.exceptions.AuthException
import com.mkirdev.unsplash.data.network.auth.appauth.AppAuth
import com.mkirdev.unsplash.data.network.auth.models.TokensNetwork
import com.mkirdev.unsplash.data.repository.auth.AuthRepositoryImpl
import com.mkirdev.unsplash.data.storages.datastore.auth.AuthStorage
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import net.openid.appauth.AuthorizationService
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryTest {

    private lateinit var authService: AuthorizationService
    private lateinit var tokens: TokensNetwork
    private lateinit var appAuth: AppAuth
    private lateinit var authStorage: AuthStorage
    private val dispatcher = UnconfinedTestDispatcher()

    private val authCode = "auth_code"

    @Before
    fun setup() {
        authService = mockk(relaxed = true)
        appAuth = mockk(relaxed = true)
        authStorage = mockk(relaxed = true)


        tokens = mockk(relaxed = true) {
            coEvery { accessToken } returns "access_token"
            coEvery { refreshToken } returns "refresh_token"
            coEvery { idToken } returns "id_token"
        }
    }

    @After
    fun teardown() {
        clearAllMocks()
    }

    @Test
    fun getAuthRequestFromAppAuthIsRequestedOnlyOnce() = runTest(dispatcher) {
        val repository = AuthRepositoryImpl(
            authService = authService,
            appAuth = appAuth,
            authStorage = authStorage,
            dispatcher = dispatcher
        )

        repository.getAuthRequest()

        coVerify(exactly = 1) { appAuth.getAuthRequest() }
    }

    @Test
    fun addAccessTokenFromAuthStorageIsRequestedOnlyOnce() = runTest(dispatcher) {

        coEvery { appAuth.performTokenRequestSuspend(authService, authCode) } returns tokens

        val repository = AuthRepositoryImpl(
            authService = authService,
            appAuth = appAuth,
            authStorage = authStorage,
            dispatcher = dispatcher
        )

        repository.performTokensRequest(authCode = authCode)

        coVerify(exactly = 1) { authStorage.addAccessToken("access_token") }
    }

    @Test
    fun addRefreshTokenFromAuthStorageIsRequestedOnlyOnce() = runTest(dispatcher) {

        coEvery { appAuth.performTokenRequestSuspend(authService, authCode) } returns tokens

        val repository = AuthRepositoryImpl(
            authService = authService,
            appAuth = appAuth,
            authStorage = authStorage,
            dispatcher = dispatcher
        )

        repository.performTokensRequest(authCode = authCode)

        coVerify(exactly = 1) { authStorage.addRefreshToken("refresh_token") }
    }

    @Test
    fun addIdTokenFromAuthStorageIsRequestedOnlyOnce() = runTest(dispatcher) {

        coEvery { appAuth.performTokenRequestSuspend(authService, authCode) } returns tokens

        val repository = AuthRepositoryImpl(
            authService = authService,
            appAuth = appAuth,
            authStorage = authStorage,
            dispatcher = dispatcher
        )

        repository.performTokensRequest(authCode = authCode)

        coVerify(exactly = 1) { authStorage.addIdToken("id_token") }
    }

    @Test
    fun getAccessTokenFromAuthStorageIsRequestedOnlyOnce() = runTest(dispatcher) {
        val repository = AuthRepositoryImpl(
            authService = authService,
            appAuth = appAuth,
            authStorage = authStorage,
            dispatcher = dispatcher
        )

        repository.getSavedToken()

        coVerify(exactly = 1) { authStorage.getAccessToken() }
    }

    @Test
    fun shouldThrowGetAccessTokenExceptionWhenStorageIsCorrupted() = runTest(dispatcher) {
        val repository = AuthRepositoryImpl(
            authService = authService,
            appAuth = appAuth,
            authStorage = authStorage,
            dispatcher = dispatcher
        )

        coEvery { authStorage.getAccessToken() } throws CorruptionException("Datastore exception")

        Assert.assertThrows(
            "",
            AuthException.GetSavedTokenRequestException::class.java
        ) {
            runTest(dispatcher) { repository.getSavedToken() }
        }
    }

    @Test
    fun clearFromStorageIsRequestedOnlyOnce() = runTest(dispatcher) {
        val repository = AuthRepositoryImpl(
            authService = authService,
            appAuth = appAuth,
            authStorage = authStorage,
            dispatcher = dispatcher
        )

        repository.clear()

        coVerify(exactly = 1) { authStorage.clear() }
    }

}