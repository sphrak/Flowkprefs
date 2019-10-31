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
import io.github.sphrak.flowkprefs.adapter.BooleanAdapter
import io.github.sphrak.flowkprefs.adapter.EnumAdapter
import io.github.sphrak.flowkprefs.adapter.FloatAdapter
import io.github.sphrak.flowkprefs.adapter.IntAdapter
import io.github.sphrak.flowkprefs.adapter.LongAdapter
import io.github.sphrak.flowkprefs.adapter.StringAdapter
import io.github.sphrak.flowkprefs.adapter.StringSetAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
internal class FlowKPreference(
    private val sharedPrefs: SharedPreferences
) : IFlowKPreference {

    @VisibleForTesting
    internal val onKeyChange: Flow<String> = callbackFlow {
        val changeListener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                launch {
                    send(key)
                }
            }

        sharedPrefs.registerOnSharedPreferenceChangeListener(changeListener)

        awaitClose {
            sharedPrefs.unregisterOnSharedPreferenceChangeListener(changeListener)
        }
    }

    override fun boolean(key: String, defaultValue: Boolean): IKPreference<Boolean> =
        KPreference(
            sharedPreferences = sharedPrefs,
            key = key,
            defaultValue = defaultValue,
            onKeyChange = onKeyChange,
            adapter = BooleanAdapter.INSTANCE
        )

    override fun float(key: String, defaultValue: Float): IKPreference<Float> =
        KPreference(
            sharedPreferences = sharedPrefs,
            key = key,
            defaultValue = defaultValue,
            onKeyChange = onKeyChange,
            adapter = FloatAdapter.INSTANCE
        )

    override fun integer(key: String, defaultValue: Int): IKPreference<Int> =
        KPreference(
            sharedPreferences = sharedPrefs,
            key = key,
            defaultValue = defaultValue,
            onKeyChange = onKeyChange,
            adapter = IntAdapter.INSTANCE
        )

    override fun long(key: String, defaultValue: Long): IKPreference<Long> =
        KPreference(
            sharedPreferences = sharedPrefs,
            key = key,
            defaultValue = defaultValue,
            onKeyChange = onKeyChange,
            adapter = LongAdapter.INSTANCE
        )

    override fun string(key: String, defaultValue: String): IKPreference<String> =
        KPreference(
            sharedPreferences = sharedPrefs,
            key = key,
            defaultValue = defaultValue,
            onKeyChange = onKeyChange,
            adapter = StringAdapter.INSTANCE
        )

    override fun stringSet(
        key: String,
        defaultValue: MutableSet<String>
    ): IKPreference<MutableSet<String>> =
        KPreference(
            sharedPreferences = sharedPrefs,
            key = key,
            defaultValue = defaultValue,
            onKeyChange = onKeyChange,
            adapter = StringSetAdapter.INSTANCE
        )

    override fun <T : Enum<T>> enum(
        key: String,
        defaultValue: T,
        convertIn: (String) -> T,
        convertOut: (T) -> String
    ): IKPreference<T> =
        KPreference(
            sharedPreferences = sharedPrefs,
            key = key,
            defaultValue = defaultValue,
            onKeyChange = onKeyChange,
            adapter = EnumAdapter(
                defaultValue = defaultValue,
                convertIn = convertIn,
                convertOut = convertOut
            )
        )

    override fun clear() {
        sharedPrefs
            .edit()
            .clear()
            .apply()
    }

    override fun getSharedPreferences(): SharedPreferences =
        sharedPrefs
}