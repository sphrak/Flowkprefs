package io.github.sphrak.flowkprefs.adapter

import android.content.SharedPreferences

internal class LongAdapter : IPreferenceAdapter<Long> {

    companion object {
        val INSTANCE = LongAdapter()
    }

    override fun get(
        key: String,
        sharedPreferences: SharedPreferences
    ): Long = sharedPreferences.getLong(key, 0L)

    override fun set(
        key: String,
        value: Long,
        editor: SharedPreferences.Editor
    ) {
        editor.putLong(key, value)
    }
}