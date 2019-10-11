package io.github.sphrak.flowkprefs.adapter

import android.content.SharedPreferences

internal class StringAdapter : IPreferenceAdapter<String> {

    companion object {
        val INSTANCE = StringAdapter()
    }

    override fun get(
        key: String,
        sharedPreferences: SharedPreferences
    ): String = sharedPreferences.getString(key, "") ?: ""

    override fun set(
        key: String,
        value: String,
        editor: SharedPreferences.Editor
    ) {
        editor.putString(key, value)
    }
}