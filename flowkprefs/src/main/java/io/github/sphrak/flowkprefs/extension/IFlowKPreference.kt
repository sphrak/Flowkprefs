package io.github.sphrak.flowkprefs.extension

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import io.github.sphrak.flowkprefs.FlowKPreference
import io.github.sphrak.flowkprefs.IFlowKPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Retrieves a new instance of the [IFlowKPreference] interface for a specific shared prefs set.
 */
@ExperimentalCoroutinesApi
fun flowkPrefs(
    context: Context,
    coroutineScope: CoroutineScope,
    key: String,
    mode: Int = Context.MODE_PRIVATE
): IFlowKPreference = FlowKPreference(context.getSharedPreferences(key, mode), coroutineScope)

/**
 * Retrieves a new instance of the [IFlowKPreference] interface for the default app shared prefs.
 */
@ExperimentalCoroutinesApi
fun flowkPrefs(
    context: Context,
    coroutineScope: CoroutineScope
): IFlowKPreference =
    FlowKPreference(PreferenceManager.getDefaultSharedPreferences(context), coroutineScope)

/**
 * Retrieves a new instance of the [IFlowKPreference] interface for a custom SharedPreferences instance.
 */
@ExperimentalCoroutinesApi
fun flowkPrefs(
    sharedPrefs: SharedPreferences,
    coroutineScope: CoroutineScope
): IFlowKPreference =
    FlowKPreference(sharedPrefs, coroutineScope)