package io.github.sphrak.flowkprefs.adapter

import android.content.SharedPreferences

internal class EnumAdapter<T : Enum<T>>(
    private val defaultValue: T,
    private val convertIn: (String) -> T,
    private val convertOut: (T) -> String
) : IPreferenceAdapter<T> {

    override fun get(
        key: String,
        sharedPreferences: SharedPreferences
    ): T {
        val rawValue: String = sharedPreferences.getString(key, "") ?: ""
        return if (rawValue.isEmpty()) {
            defaultValue
        } else {
            convertIn(rawValue)
        }
    }

    override fun set(key: String, value: T, editor: SharedPreferences.Editor) {
        editor.putString(key, convertOut(value))
    }
}