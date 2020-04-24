/*
 * Copyright 2019 Niclas Kron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.sphrak.flowkprefs

import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import io.github.sphrak.flowkprefs.adapter.IPreferenceAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
internal class KPreferenceImpl<T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val defaultValue: T,
    onKeyChange: Flow<String>,
    private val adapter: IPreferenceAdapter<T>
) : KPreference<T> {

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

    override fun asConsumer(): KPreferenceImpl<T> = this
}