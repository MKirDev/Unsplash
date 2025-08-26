package com.mkirdev.unsplash.onboarding.impl

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.graphicsLayer
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


private const val TOP_FACTOR = 0.28f
private const val BOTTOM_FACTOR = 0.12f

private const val BEYOND_PAGE_COUNT = 1

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

            val currentPage = pagerState.currentPage
            val configuration = LocalConfiguration.current
            val screenHeight = configuration.screenHeightDp.dp

            val bgCamerasPainter = painterResource(R.drawable.background_cameras)
            val notificationPainter = painterResource(R.drawable.notification)

            val currentImageRes by remember(currentPage) {
                derivedStateOf {
                    when (uiState.pages[currentPage]) {
                        OnboardingPage.First, OnboardingPage.Second, OnboardingPage.Third -> bgCamerasPainter
                        OnboardingPage.Fourth -> notificationPainter
                    }
                }
            }

            val topBottomPadding = remember(configuration) {
                val top = screenHeight * TOP_FACTOR
                val bottom = screenHeight * BOTTOM_FACTOR
                PaddingValues(top = top, bottom = bottom)
            }

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

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val offsetFraction = pagerState.currentPageOffsetFraction
                    Image(
                        painter = currentImageRes,
                        contentDescription = null,
                        modifier = Modifier
                            .graphicsLayer {
                                if (uiState.pages[pagerState.currentPage] == OnboardingPage.Fourth) {
                                    translationX = -offsetFraction * size.width
                                }
                            }
                            .padding(top = padding_70),
                        contentScale = ContentScale.Fit
                    )

                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = if (uiState.pages[pagerState.currentPage] == OnboardingPage.Fourth) 1f else 0f
                                translationX = -offsetFraction * size.width
                            }
                    ) {
                        with(notificationFeatureApi) {
                            NotificationsFeature(text = stringResource(R.string.onboarding_notifications))
                        }
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize(),
                    beyondViewportPageCount = BEYOND_PAGE_COUNT,
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