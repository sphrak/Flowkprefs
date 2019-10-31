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

package io.github.sphrak.flowkprefs.extension

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import io.github.sphrak.flowkprefs.FlowKPreference
import io.github.sphrak.flowkprefs.IFlowKPreference
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Retrieves a new instance of the [IFlowKPreference] interface for a specific shared prefs set.
 */
@ExperimentalCoroutinesApi
fun flowkPrefs(
    context: Context,
    key: String,
    mode: Int = Context.MODE_PRIVATE
): IFlowKPreference = FlowKPreference(context.getSharedPreferences(key, mode))

/**
 * Retrieves a new instance of the [IFlowKPreference] interface for the default app shared prefs.
 */
@ExperimentalCoroutinesApi
fun flowkPrefs(
    context: Context
): IFlowKPreference =
    FlowKPreference(PreferenceManager.getDefaultSharedPreferences(context))

/**
 * Retrieves a new instance of the [IFlowKPreference] interface for a custom SharedPreferences instance.
 */
@ExperimentalCoroutinesApi
fun flowkPrefs(
    sharedPrefs: SharedPreferences
): IFlowKPreference =
    FlowKPreference(sharedPrefs)