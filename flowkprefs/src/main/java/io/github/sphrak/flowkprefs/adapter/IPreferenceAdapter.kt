package io.github.sphrak.flowkprefs.adapter

import android.content.SharedPreferences
import androidx.annotation.CheckResult

internal interface IPreferenceAdapter<T> {

    @CheckResult
    fun get(
        key: String,
        sharedPreferences: SharedPreferences
    ): T

    fun set(
        key: String,
        value: T,
        editor: SharedPreferences.Editor
    )
}