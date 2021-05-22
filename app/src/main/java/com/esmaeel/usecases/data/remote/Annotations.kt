package com.esmaeel.usecases.data.remote

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ADMIN_TOKEN_REQUIRED

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class USER_TOKEN_REQUIRED

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class GUEST_TOKEN_REQUIRED