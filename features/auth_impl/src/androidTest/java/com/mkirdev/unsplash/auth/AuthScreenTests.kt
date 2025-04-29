package com.mkirdev.unsplash.auth

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mkirdev.unsplash.auth.impl.AuthContract
import com.mkirdev.unsplash.auth.impl.AuthScreen
import com.mkirdev.unsplash.auth.impl.AuthScreenTags
import com.mkirdev.unsplash.core.ui.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthScreenTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    @Test
    fun auth_whenUiStateIsIdle_doesNotShowNotification() {
        composeTestRule.setContent {
            AuthScreen(
                uiState = AuthContract.State.Idle,
                onNotificationClick = {},
                onAuthClick = {}
            )
        }
        composeTestRule.onNodeWithTag(AuthScreenTags.NOTIFICATION_EMPTY).assertExists()
    }

    @Test
    fun auth_whenUiStateIsSuccess_showsNotificationInfo() {
        composeTestRule.setContent {
            AuthScreen(
                uiState = AuthContract.State.Success,
                onNotificationClick = {},
                onAuthClick = {}
            )
        }
        composeTestRule.onNodeWithTag(AuthScreenTags.NOTIFICATION_INFO).assertExists()
    }

    @Test
    fun auth_whenUiStateIsError_showsNotificationError() {
        val uiStateErrorStub = AuthContract.State.Error(composeTestRule.activity.getString(R.string.test_error))
        composeTestRule.setContent {
            AuthScreen(
                uiState = uiStateErrorStub,
                onNotificationClick = {},
                onAuthClick = {}
            )
        }
        composeTestRule.onNodeWithTag(AuthScreenTags.NOTIFICATION_ERROR).assertExists()
    }

}