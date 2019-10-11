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
import timber.log.Timber

@ExperimentalCoroutinesApi
internal class FlowKPreference(
    private val sharedPrefs: SharedPreferences
) : IFlowKPreference {

    @VisibleForTesting
    internal val onKeyChange: Flow<String> = callbackFlow {
        Timber.d("ON KEY CHANGE INIT")
        val changeListener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                launch {
                    Timber.d("KEY '$key' CHANGED")
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