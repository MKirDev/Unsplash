package com.mkirdev.unsplash.onboarding.impl

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.mkirdev.unsplash.content_creation.api.ContentCreationFeatureApi
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.bodyLargeMedium
import com.mkirdev.unsplash.core.ui.theme.padding_30
import com.mkirdev.unsplash.core.ui.theme.padding_70
import com.mkirdev.unsplash.core.ui.widgets.ClosableErrorField
import com.mkirdev.unsplash.core.ui.widgets.OnboardingBoxWithArc
import com.mkirdev.unsplash.core.utils.ExternalIntentHelper
import com.mkirdev.unsplash.core.utils.PermissionChecker
import com.mkirdev.unsplash.notification.api.NotificationFeatureApi
import com.mkirdev.unsplash.onboarding.widgets.FinishButton
import com.mkirdev.unsplash.social_collections.api.SocialCollectionsFeatureApi
import com.mkirdev.unsplash.upload_and_track.api.UploadAndTrackFeatureApi

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
internal fun OnboardingScreen(
    uiState: OnboardingContract.State,
    contentCreationFeatureApi: ContentCreationFeatureApi,
    socialCollectionsFeatureApi: SocialCollectionsFeatureApi,
    uploadAndTrackFeatureApi: UploadAndTrackFeatureApi,
    notificationFeatureApi: NotificationFeatureApi,
    onAuthClick: () -> Unit,
    onCloseFieldClick: () -> Unit
) {
    when (uiState) {
        OnboardingContract.State.Idle -> {}
        is OnboardingContract.State.Onboarding -> {

            val pagerState = rememberPagerState(
                initialPage = uiState.pages.indexOf(uiState.pages.first()),
                pageCount = { uiState.pages.size }
            )

            val context = LocalContext.current
            val activity = context as? Activity

            val notificationPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    onAuthClick()
                } else {
                    val shouldShowRationale =
                        activity?.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) == true
                    if (!shouldShowRationale) {
                        ExternalIntentHelper.startSettingsIntent(context)
                    }
                }
            }

            OnboardingBoxWithArc(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(horizontal = padding_30)
                    .verticalScroll(rememberScrollState()),
                arcHeightFactor = pagerState.currentPage.toFloat(),
                centerOffsetFactor = pagerState.currentPage.toFloat(),
                contentAlignment = Alignment.TopCenter
            ) {

                val currentPage = pagerState.currentPage

                val currentImageRes by remember(currentPage) {
                    derivedStateOf {
                        when (uiState.pages[currentPage]) {
                            OnboardingPage.First, OnboardingPage.Second, OnboardingPage.Third -> R.drawable.background_cameras
                            OnboardingPage.Fourth -> R.drawable.notification
                        }
                    }
                }

                AnimatedContent(
                    targetState = currentImageRes,
                    transitionSpec = {
                        val isForward = initialState == R.drawable.background_cameras &&
                                targetState == R.drawable.notification
                        val isBackward = initialState == R.drawable.notification &&
                                targetState == R.drawable.background_cameras
                        when {
                            isForward -> {
                                slideInHorizontally(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioNoBouncy,
                                        stiffness = Spring.StiffnessMedium
                                    )
                                ) { fullWidth -> fullWidth } togetherWith
                                        slideOutHorizontally(
                                            animationSpec = spring(
                                                Spring.DampingRatioNoBouncy,
                                                stiffness = Spring.StiffnessMedium
                                            )
                                        ) { fullWidth -> -fullWidth }
                            }

                            isBackward -> {
                                slideInHorizontally(
                                    animationSpec = spring(
                                        Spring.DampingRatioNoBouncy,
                                        stiffness = Spring.StiffnessMedium
                                    )
                                ) { fullWidth -> -fullWidth } togetherWith
                                        slideOutHorizontally(
                                            animationSpec = spring(
                                                Spring.DampingRatioNoBouncy,
                                                stiffness = Spring.StiffnessMedium
                                            )
                                        ) { fullWidth -> fullWidth }
                            }

                            else -> {
                                fadeIn() togetherWith fadeOut()
                            }
                        }
                    }
                ) { res ->
                    Column {
                        Image(
                            painter = painterResource(id = res),
                            contentDescription = null,
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .wrapContentWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(top = padding_70),
                            contentScale = ContentScale.Fit
                        )
                        if (res == R.drawable.notification) {
                            with(notificationFeatureApi) {
                                NotificationsFeature(text = stringResource(R.string.onboarding_notifications))
                            }
                        }
                    }
                }

                val configuration = LocalConfiguration.current
                val screenHeight = configuration.screenHeightDp.dp

                val topBottomPadding by remember(configuration) {
                    derivedStateOf {
                        val top = screenHeight * 0.28f
                        val bottom = screenHeight * 0.12f
                        PaddingValues(top = top, bottom = bottom)
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = topBottomPadding,
                    verticalAlignment = Alignment.Top
                ) { position ->
                    when (uiState.pages[position]) {
                        OnboardingPage.First -> {
                            with(contentCreationFeatureApi) {
                                ContentCreationFeature(text = stringResource(id = R.string.content_creation))
                            }
                        }

                        OnboardingPage.Second -> {
                            with(uploadAndTrackFeatureApi) {
                                UploadAndTrackFeature(text = stringResource(id = R.string.upload_and_track))
                            }
                        }

                        OnboardingPage.Third -> {
                            with(socialCollectionsFeatureApi) {
                                SocialCollectionsFeature(text = stringResource(id = R.string.social_collections))
                            }
                        }

                        OnboardingPage.Fourth -> {
                            with(notificationFeatureApi) {
                                SettingsFeature(text = stringResource(R.string.onboarding_settings_for_notifications))
                            }
                        }
                    }
                }
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = padding_30), contentAlignment = Alignment.Center
            ) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    pageCount = uiState.pages.size
                )
            }
            Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomCenter) {
                FinishButton(
                    modifier = Modifier.fillMaxWidth(),
                    pagerState = pagerState,
                    onClick = {
                        PermissionChecker.checkPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS,
                            onAuthClick,
                            launcher = notificationPermissionLauncher
                        )
                    }
                )
            }
            Box(modifier = Modifier.fillMaxSize()) {
                when (uiState.isError) {
                    false -> {}

                    true -> {
                        ClosableErrorField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter),
                            text = stringResource(id = R.string.onboarding_data_store_error),
                            textStyle = MaterialTheme.typography.bodyLargeMedium,
                            onClick = onCloseFieldClick
                        )
                    }
                }
            }
        }
    }
}