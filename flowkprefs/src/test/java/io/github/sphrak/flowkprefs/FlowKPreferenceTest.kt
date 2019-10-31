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
import assertk.assertions.isNotEqualTo
import assertk.assertions.isTrue
import io.github.sphrak.flowkprefs.extension.flowkPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class FlowKPreferenceTest {

    private companion object {
        const val PREF_KEY = "42"
        const val PREF_MODE = MODE_PRIVATE
    }

    private var mockSharedPreferences = mock(SharedPreferences::class.java)
    private lateinit var sharedPreferences: SharedPreferences

    private val context: Context = mock<Context>(Context::class.java)
    private var editor: SharedPreferences.Editor =
        mock(SharedPreferences.Editor::class.java)

    private var listener = mock(SharedPreferences.OnSharedPreferenceChangeListener::class.java)

    private lateinit var flowkPrefs: IFlowKPreference

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineDispatcher)
        doReturn(mockSharedPreferences).`when`(context).getSharedPreferences(PREF_KEY, PREF_MODE)
        sharedPreferences = context.getSharedPreferences(PREF_KEY, PREF_MODE)

        doReturn(editor).`when`(sharedPreferences).edit()
        doNothing().`when`(sharedPreferences)
            .registerOnSharedPreferenceChangeListener(listener)

        flowkPrefs = flowkPrefs(sharedPreferences)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    /*@Test
    fun `observe value change`() = runBlockingTest {

        val key = "secret_key"
        val observer = (flowkPrefs as FlowKPreference).onKeyChange
        var observedValue: String? = null

        assertThat(listener).isNotEqualTo(null)

        flowkPrefs.string(key, "asdfasdf")

        withContext(Dispatchers.Main) {
            observer
                .collect { observedValue = it }
        }

        verify(sharedPreferences).registerOnSharedPreferenceChangeListener(listener)
        assertThat(observedValue).isEqualTo("asdfasdf")
    }*/

    @Test
    fun `test boolean`() {
        val key = "bool_key"
        val pref = flowkPrefs.boolean(key, true)

        assertThat(pref.key()).isEqualTo(key)
        assertThat(pref.defaultValue()).isTrue()
    }

    @Test
    fun `test float`() {
        val key = "float_key"
        val pref = flowkPrefs.float(key, 1f)

        assertThat(pref.key()).isEqualTo(key)
        assertThat(pref.defaultValue()).isEqualTo(1f)
    }

    @Test
    fun `test integer`() {
        val key = "integer_key"
        val pref = flowkPrefs.integer(key, 62)

        assertThat(pref.key()).isEqualTo(key)
        assertThat(pref.defaultValue()).isEqualTo(62)
    }

    @Test
    fun `test long`() {
        val key = "long_key"
        val pref = flowkPrefs.long(key, 12771L)

        assertThat(pref.key()).isEqualTo(key)
        assertThat(pref.defaultValue()).isEqualTo(12771L)
    }

    @Test
    fun `test string`() {
        val key = "string_key"
        val pref = flowkPrefs.string(key, "correct horse battery staple")

        assertThat(pref.key()).isEqualTo(key)
        assertThat(pref.defaultValue()).isEqualTo("correct horse battery staple")
    }

    @Test
    fun `test string set`() {
        val key = "stringset_key"

        val defaultValue = mutableSetOf("correct horse battery staple")
        val pref = flowkPrefs.stringSet(key, defaultValue = defaultValue)

        assertThat(pref.key()).isEqualTo(key)
        assertThat(pref.defaultValue()).isEqualTo(defaultValue)
    }

    @Test
    fun `test enum`() {
        val key = "enum_key"

        val pref = flowkPrefs.enum(
            key,
            Character.A,
            Character.Companion::fromString,
            Character.Companion::toString
        )

        assertThat(pref.key()).isEqualTo(key)
        assertThat(pref.defaultValue()).isEqualTo(Character.A)

        doReturn(true).`when`(sharedPreferences).contains(key)
        doReturn("a").`when`(sharedPreferences).getString(eq(key), any())
        assertThat(pref.get()).isEqualTo(Character.A)

        doReturn("c").`when`(sharedPreferences).getString(eq(key), any())
        assertThat(pref.get()).isEqualTo(Character.C)

        pref.set(Character.D)
        verify(editor).putString(key, "d")
    }

    @Test
    fun `test clear`() {
        doReturn(editor).`when`(editor).clear()
        flowkPrefs.clear()
        verify(editor.clear()).apply()
    }

    enum class Character {

        A,
        B,
        C,
        D;

        companion object {
            fun fromString(rawValue: String): Character =
                values()
                    .single { it.name.toLowerCase() == rawValue }

            fun toString(value: Character): String =
                value.name.toLowerCase()
        }
    }
}