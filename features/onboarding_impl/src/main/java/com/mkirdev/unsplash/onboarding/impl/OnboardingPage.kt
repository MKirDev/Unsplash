package com.mkirdev.unsplash.onboarding.impl

internal sealed class OnboardingPage {
    data object First : OnboardingPage()

    data object Second : OnboardingPage()

    data object Third : OnboardingPage()
}
