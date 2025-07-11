package com.mkirdev.unsplash.details.mappers

import com.mkirdev.unsplash.domain.models.Links

internal fun Links.toDownload(): String = download

internal fun Links.toHtml(): String = html