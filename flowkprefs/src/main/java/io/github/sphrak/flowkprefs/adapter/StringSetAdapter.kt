package io.github.sphrak.flowkprefs.adapter

import android.content.SharedPreferences

internal class StringSetAdapter : IPreferenceAdapter<MutableSet<String>> {

    companion object {
        val INSTANCE = StringSetAdapter()
    }

    override fun get(
        key: String,
        sharedPreferences: SharedPreferences
    ): MutableSet<String> =
        sharedPreferences
            .getStringSet(key, emptySet()) ?: mutableSetOf()

    override fun set(
        key: String,
        value: MutableSet<String>,
        editor: SharedPreferences.Editor
    ) {
        editor.putStringSet(key, value)
    }
}