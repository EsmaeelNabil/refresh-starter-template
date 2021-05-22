package com.esmaeel.usecases.domain.usecases

interface UseCase<ReturnType> {
    fun invoke(): ReturnType
}