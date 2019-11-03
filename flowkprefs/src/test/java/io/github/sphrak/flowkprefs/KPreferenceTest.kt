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

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import io.github.sphrak.flowkprefs.adapter.StringAdapter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@FlowPreview
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class KPreferenceTest {

    private companion object {
        const val PREF_KEY = "THIS_IS_A_KEY"
        const val PREF_MODE = MODE_PRIVATE
        const val DEFAULT_VALUE = "asdf"
    }

    private val keyChange = flowOf(PREF_KEY)
    private val adapter = mockk<StringAdapter>()
    private val sharedPreferences = mockk<SharedPreferences>()
    private val context = mockk<Context>()
    private var editor = mockk<SharedPreferences.Editor>()

    private lateinit var kPreferenceString: KPreference<String>

    @Before
    fun setup() {

        every { context.getSharedPreferences(PREF_KEY, PREF_MODE) } returns sharedPreferences
        every { sharedPreferences.edit() } returns editor
        every { sharedPreferences.contains(PREF_KEY) } answers {
            val key: String = args[0] as String
            sharedPreferences.getString(key, "") != null
        }

        kPreferenceString = KPreference(
            sharedPreferences = sharedPreferences,
            key = PREF_KEY,
            defaultValue = DEFAULT_VALUE,
            onKeyChange = keyChange,
            adapter = adapter
        )
    }

    @Test
    fun `isSet is false`() {
        every { sharedPreferences.contains(PREF_KEY) } returns false
        assertThat(kPreferenceString.isSet())
            .isFalse()
    }

    @Test
    fun `isSet is true`() {
        every { sharedPreferences.getString(eq(PREF_KEY), any()) } returns "asdf"
        assertThat(kPreferenceString.isSet())
            .isTrue()
    }

    @Test
    fun `do get value`() {
        val expectedValue = "lagom aer baest"

        every { adapter.get(eq(PREF_KEY), sharedPreferences) } returns expectedValue
        every { sharedPreferences.getString(eq(PREF_KEY), any()) } returns expectedValue

        val result = kPreferenceString.get()
        assertThat(result).isEqualTo(expectedValue)
    }

    @Test
    fun `do delete value`() {
        every { editor.remove(PREF_KEY) } returns editor
        every { editor.remove(PREF_KEY).apply() } returns Unit

        kPreferenceString.delete()

        verify {
            editor
                .remove(PREF_KEY)
                .apply()
        }
    }

    @Test
    fun `do single observe value`() = runBlockingTest {

        val nextValue = "no"
        var captured: String? = null

        every { editor.putString(PREF_KEY, nextValue) } returns editor
        every { editor.apply() } returns Unit
        every { sharedPreferences.contains(PREF_KEY) } returns true
        every { adapter.set(eq(PREF_KEY), any(), editor) } returns Unit
        every { adapter.get(PREF_KEY, sharedPreferences) } returns nextValue

        assertThat(kPreferenceString.isSet()).isTrue()

        kPreferenceString
            .observe()
            .collect {
                captured = it
            }

        kPreferenceString.set(nextValue)

        assertThat(captured).isEqualTo(nextValue)
    }

    @Test
    fun `do single observe value multiple observers`() = runBlockingTest {

        val nextValue = "no"
        var observed1: String? = null
        var observed2: String? = null
        var observed3: String? = null

        every { editor.putString(PREF_KEY, nextValue) } returns editor
        every { editor.apply() } returns Unit
        every { sharedPreferences.contains(PREF_KEY) } returns true
        every { adapter.set(eq(PREF_KEY), any(), editor) } returns Unit
        every { adapter.get(PREF_KEY, sharedPreferences) } returns nextValue

        assertThat(kPreferenceString.isSet()).isTrue()

        kPreferenceString
            .observe()
            .collect {
                observed1 = it
            }

        kPreferenceString
            .observe()
            .collect {
                observed2 = it
            }

        kPreferenceString
            .observe()
            .collect {
                observed3 = it
            }

        kPreferenceString.set(nextValue)

        assertThat(observed1).isEqualTo(nextValue)
        assertThat(observed2).isEqualTo(nextValue)
        assertThat(observed3).isEqualTo(nextValue)
    }
}