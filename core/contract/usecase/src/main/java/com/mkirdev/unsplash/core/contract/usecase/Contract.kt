package com.mkirdev.unsplash.core.contract.usecase

interface UseCaseWithParam<P> {
    suspend fun execute(param: P)
}

interface UseCaseWithoutParamAndResult {
    suspend fun execute()
}

interface UseCaseWithResult<R> {
    suspend fun execute(): R
}