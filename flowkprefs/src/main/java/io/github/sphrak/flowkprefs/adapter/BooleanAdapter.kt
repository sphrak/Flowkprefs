package io.github.sphrak.flowkprefs.adapter

import android.content.SharedPreferences

internal class BooleanAdapter : IPreferenceAdapter<Boolean> {

    companion object {
        val INSTANCE = BooleanAdapter()
    }

    override fun get(key: String, sharedPreferences: SharedPreferences): Boolean =
        sharedPreferences.getBoolean(key, false)

    override fun set(key: String, value: Boolean, editor: SharedPreferences.Editor) {
        editor.putBoolean(key, value)
    }
}