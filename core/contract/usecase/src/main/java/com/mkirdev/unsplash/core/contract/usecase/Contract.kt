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

interface UseCaseWithParamAndResult<P, R> {
    suspend fun execute(param: P): R
}

interface UseCaseWithParams<P1, P2> {
    suspend fun execute(firstParam: P1, secondParam: P2)
}