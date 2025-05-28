package com.mkirdev.unsplash.onboarding.impl

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.mkirdev.unsplash.content_creation.api.ContentCreationFeatureApi
import com.mkirdev.unsplash.core.ui.R
import com.mkirdev.unsplash.core.ui.theme.bodyLargeMedium
import com.mkirdev.unsplash.core.ui.theme.padding_190
import com.mkirdev.unsplash.core.ui.theme.padding_250
import com.mkirdev.unsplash.core.ui.theme.padding_30
import com.mkirdev.unsplash.core.ui.theme.padding_70
import com.mkirdev.unsplash.core.ui.widgets.ClosableErrorField
import com.mkirdev.unsplash.core.ui.widgets.OnboardingBoxWithArc
import com.mkirdev.unsplash.onboarding.widgets.FinishButton
import com.mkirdev.unsplash.social_collections.api.SocialCollectionsFeatureApi
import com.mkirdev.unsplash.upload_and_track.api.UploadAndTrackFeatureApi

@Composable
internal fun OnboardingScreen(
    uiState: OnboardingContract.State,
    contentCreationFeatureApi: ContentCreationFeatureApi,
    socialCollectionsFeatureApi: SocialCollectionsFeatureApi,
    uploadAndTrackFeatureApi: UploadAndTrackFeatureApi,
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
                Image(
                    painter = painterResource(R.drawable.background_cameras),
                    contentDescription = stringResource(R.string.background_cameras),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = padding_70),
                    contentScale = ContentScale.Fit
                )
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(top = padding_250, bottom = padding_190),
                    verticalAlignment = Alignment.Bottom
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
                    onClick = onAuthClick
                )
            }
            Box(modifier = Modifier.fillMaxSize()) {
                when (uiState.isError) {
                    false -> {}

                    true -> {
                        ClosableErrorField(
                            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
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