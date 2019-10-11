package io.github.sphrak.flowkprefs.adapter

import android.content.SharedPreferences

internal class FloatAdapter : IPreferenceAdapter<Float> {

    companion object {
        val INSTANCE = FloatAdapter()
    }

    override fun get(key: String, sharedPreferences: SharedPreferences): Float =
        sharedPreferences.getFloat(key, 0f)

    override fun set(key: String, value: Float, sharedPreferences: SharedPreferences.Editor) {
        sharedPreferences.putFloat(key, value)
    }
}