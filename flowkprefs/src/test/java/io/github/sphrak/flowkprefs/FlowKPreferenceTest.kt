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
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class FlowKPreferenceTest {

    private companion object {
        const val PREF_KEY = "42"
        const val PREF_MODE = MODE_PRIVATE
    }

    private val mockSharedPreferences = mockk<SharedPreferences>()
    private val mockContext = mockk<Context>()
    private val mockEditor = mockk<SharedPreferences.Editor>()

    private var listener: SharedPreferences.OnSharedPreferenceChangeListener? = null

    private lateinit var testFlowkPrefs: IFlowKPreference

    private val testCoroutineScope = TestCoroutineScope()

    @Before
    fun setup() {
        every { mockContext.getSharedPreferences(PREF_KEY, PREF_MODE) } returns mockSharedPreferences
        every { mockContext.packageName } returns "test_package_name"
        every { mockSharedPreferences.edit() } returns mockEditor
        every { mockSharedPreferences.registerOnSharedPreferenceChangeListener(any()) } answers {
            listener = arg(0)
        }

        testFlowkPrefs = flowkPrefs(mockContext, testCoroutineScope,
            PREF_KEY,
            PREF_MODE
        )
    }

    @After
    fun teardown() {
        testCoroutineScope.cleanupTestCoroutines()
    }

    @Test
    fun `observe value change`(): Unit =
        testCoroutineScope
            .runBlockingTest {
                var recvKey: String? = null

                val key = "secret_key"
                val observer = (testFlowkPrefs as FlowKPreference)
                    .onKeyChange

                testCoroutineScope
                    .launch {
                        observer
                            .collect {
                                recvKey = it
                            }
                    }

                assertThat(listener).isNotEqualTo(null)

                verify {
                    mockSharedPreferences.registerOnSharedPreferenceChangeListener(listener)
                }

                listener?.onSharedPreferenceChanged(mockSharedPreferences, key)

                assertThat(recvKey).isEqualTo(key)
            }

    @Test
    fun `test put boolean`() {
        val key = "bool_key"
        val pref = testFlowkPrefs.boolean(key, true)

        assertThat(pref.key()).isEqualTo(key)
        assertThat(pref.defaultValue()).isTrue()
    }

    @Test
    fun `test put float`() {
        val key = "float_key"
        val pref = testFlowkPrefs.float(key, 1f)

        assertThat(pref.key()).isEqualTo(key)
        assertThat(pref.defaultValue()).isEqualTo(1f)
    }

    @Test
    fun `test put integer`() {
        val key = "integer_key"
        val pref = testFlowkPrefs.integer(key, 62)

        assertThat(pref.key()).isEqualTo(key)
        assertThat(pref.defaultValue()).isEqualTo(62)
    }

    @Test
    fun `test put long`() {
        val key = "long_key"
        val pref = testFlowkPrefs.long(key, 12771L)

        assertThat(pref.key()).isEqualTo(key)
        assertThat(pref.defaultValue()).isEqualTo(12771L)
    }

    @Test
    fun `test put string`() {
        val key = "string_key"
        val pref = testFlowkPrefs.string(key, "correct horse battery staple")

        assertThat(pref.key()).isEqualTo(key)
        assertThat(pref.defaultValue()).isEqualTo("correct horse battery staple")
    }

    @Test
    fun `test put string set`() {
        val key = "stringset_key"

        val defaultValue = mutableSetOf("correct horse battery staple")
        val pref = testFlowkPrefs.stringSet(key, defaultValue = defaultValue)

        assertThat(pref.key()).isEqualTo(key)
        assertThat(pref.defaultValue()).isEqualTo(defaultValue)
    }

    @Test
    fun `test put enum`() {
        val key = "enum_key"

        val pref = testFlowkPrefs.enum(
            key,
            Character.A,
            Character.Companion::fromString,
            Character.Companion::toString
        )

        assertThat(pref.key()).isEqualTo(key)
        assertThat(pref.defaultValue()).isEqualTo(Character.A)

        every { mockSharedPreferences.contains(key) } returns true
        every { mockSharedPreferences.getString(eq(key), any()) } returns "a"

        assertThat(pref.get()).isEqualTo(Character.A)

        every { mockSharedPreferences.getString(eq(key), any()) } returns "c"

        assertThat(pref.get()).isEqualTo(Character.C)

        every { mockEditor.putString(eq(key), "d") } returns mockEditor
        every { mockEditor.apply() } returns Unit
        pref.set(Character.D)

        verify {
            mockEditor.putString(key, "d")
        }
    }

    @Test
    fun `test clear`() {
        every { mockEditor.clear() } returns mockEditor
        every { mockEditor.clear().apply() } returns Unit

        testFlowkPrefs.clear()
        verify {
            mockEditor
                .clear()
                .apply()
        }
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