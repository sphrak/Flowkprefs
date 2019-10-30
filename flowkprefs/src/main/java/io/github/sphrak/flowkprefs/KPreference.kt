package io.github.sphrak.flowkprefs

import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import io.github.sphrak.flowkprefs.adapter.IPreferenceAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
internal class KPreference<T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val defaultValue: T,
    onKeyChange: Flow<String>,
    private val adapter: IPreferenceAdapter<T>
) : IKPreference<T> {

    @VisibleForTesting
    private val values: Flow<T> = onKeyChange
            .filter {
                it == key
            }.map {
                get()
            }

    override fun key(): String = key

    override fun defaultValue(): T = defaultValue

    @Synchronized
    override fun get(): T = if (!isSet()) {
        defaultValue
    } else {
        adapter.get(key, sharedPreferences)
    }

    @Synchronized
    override fun set(value: T) {
        val editor = sharedPreferences.edit()
        adapter.set(key, value, editor)
        editor.apply()
    }

    override fun isSet(): Boolean =
        sharedPreferences
            .contains(key)

    override fun delete(): Unit =
        sharedPreferences
            .edit()
            .remove(key)
            .apply()

    override fun observe(): Flow<T> = values

    override suspend fun emit(value: T): Unit =
        set(value)

    override fun asObservable(): Flow<T> = values

    override fun asConsumer(): KPreference<T> = this
}