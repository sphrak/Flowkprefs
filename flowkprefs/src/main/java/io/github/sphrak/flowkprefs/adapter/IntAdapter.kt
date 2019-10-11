package io.github.sphrak.flowkprefs.adapter

import android.content.SharedPreferences

internal class IntAdapter : IPreferenceAdapter<Int> {
    companion object {
        val INSTANCE = IntAdapter()
    }

    override fun get(
        key: String,
        sharedPreferences: SharedPreferences
    ): Int = sharedPreferences.getInt(key, 0)

    override fun set(
        key: String,
        value: Int,
        editor: SharedPreferences.Editor
    ) {
        editor.putInt(key, value)
    }
}