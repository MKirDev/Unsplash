package com.mkirdev.unsplash.domain.usecases.photos

import com.mkirdev.unsplash.core.contract.usecase.UseCaseWithParam
import com.mkirdev.unsplash.domain.repository.PhotosRepository

class AddDownloadLinkUseCase(
    private val repository: PhotosRepository
) : UseCaseWithParam<String> {
    override suspend fun execute(link: String) {
        return repository.addDownloadLink(link)
    }
}