package com.mkirdev.unsplash.onboarding.impl

sealed class OnboardingPage {
    data object First : OnboardingPage()

    data object Second : OnboardingPage()

    data object Third : OnboardingPage()
}
