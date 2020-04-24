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
import androidx.annotation.CheckResult

interface FlowKPreference {

    /**
     * Retrieves a boolean preference.
     *
     * @return a [IKPreference] which gets and sets a boolean.
     */
    @CheckResult
    fun boolean(
        key: String,
        defaultValue: Boolean = false
    ): KPreference<Boolean>

    /**
     * Retrieves a float preference.
     *
     * @return a [KPreference] which gets and sets a floating-point decimal.
     */
    @CheckResult
    fun float(
        key: String,
        defaultValue: Float = 0f
    ): KPreference<Float>

    /**
     * Retrieves a integers preference.
     *
     * @return a [KPreference] which gets and sets a 32-bit integer.
     */
    @CheckResult
    fun integer(
        key: String,
        defaultValue: Int = 0
    ): KPreference<Int>

    /**
     * Retrieves a long preference.
     *
     * @return a [KPreference] which gets and set a 64-bit integer (long).
     */
    @CheckResult
    fun long(
        key: String,
        defaultValue: Long = 0L
    ): KPreference<Long>

    /**
     * Retrieves a string preference.
     *
     * @return a [KPreference] which gets and sets a string.
     */
    @CheckResult
    fun string(
        key: String,
        defaultValue: String = ""
    ): KPreference<String>

    /**
     * Retrieves a string set preference.
     *
     * @return a [KPreference] which gets and sets a string set.
     */
    @CheckResult
    fun stringSet(
        key: String,
        defaultValue: MutableSet<String> = mutableSetOf()
    ): KPreference<MutableSet<String>>

    /**
     * Retrieves an enum preference.
     *
     * @return a [KPreference] which gets and sets an enum,
     */
    @CheckResult
    fun <T : Enum<T>> enum(
        key: String,
        defaultValue: T,
        convertIn: (String) -> T,
        convertOut: (T) -> String
    ): KPreference<T>

    /** Clears all preferences in the current preferences collection. */
    fun clear()

    /** @return The underlying SharedPreferences instance. */
    fun getSharedPreferences(): SharedPreferences
}