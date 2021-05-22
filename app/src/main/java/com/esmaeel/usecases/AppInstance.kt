package com.esmaeel.usecases

import com.akexorcist.localizationactivity.ui.LocalizationApplication
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class AppInstance : LocalizationApplication() {
    override fun getDefaultLanguage(): Locale {
        return Locale.getDefault()
    }
}