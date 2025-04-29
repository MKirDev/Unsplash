package com.mkirdev.unsplash.core.utils

import java.util.Locale

fun formatNumber(number: Int) = if (number > 1000) String.format(Locale.US, "%.1fk", number / 1000)
else number.toString()