package com.esmaeel.usecases.utils.ktx

fun String.isUrl(): Boolean {
    if (this.isNullOrEmpty()) return false
    return this.startsWith("http") || this.startsWith("https")
}

fun String.trimLang(): String {
    return if (this.contains("en", ignoreCase = true)) "en" else "ar"
}
