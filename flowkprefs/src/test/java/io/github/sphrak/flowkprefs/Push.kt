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
import android.content.SharedPreferences
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import io.github.sphrak.flowkprefs.extension.flowkPrefs
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class Push {

    private companion object {
        const val PREF_KEY = "42"
        const val PREF_MODE = Context.MODE_PRIVATE
    }

    private val mockSharedPreferences = mockk<SharedPreferences>()
    private val mockEditor = mockk<SharedPreferences.Editor>()

    private val mockContext = mockk<Context>()
    private val testCoroutineScope = TestCoroutineScope()

    private lateinit var testFlowkPrefs: IFlowKPreference
    private var listener: SharedPreferences.OnSharedPreferenceChangeListener? = null

    @Before
    fun setup() {

        every {
            mockContext.getSharedPreferences(
                PREF_KEY,
                PREF_MODE
            )
        } returns mockSharedPreferences
        every { mockSharedPreferences.edit() } returns mockEditor

        every { mockSharedPreferences.registerOnSharedPreferenceChangeListener(any()) } answers {
            listener = arg(0)
        }

        testFlowkPrefs = flowkPrefs(mockContext, testCoroutineScope, PREF_KEY, PREF_MODE)
    }

    @After
    fun teardown() {
        testCoroutineScope.cleanupTestCoroutines()
    }

    @Test
    fun `observe value change`() =
        testCoroutineScope
            .runBlockingTest {
                var recvKey: String? = null

                val key = "secret_key"

                // when evaluated should register a click listener
                // in [FlowKPreference].
                val observer = (testFlowkPrefs as FlowKPreference)
                    .onKeyChange

                testCoroutineScope
                    .launch {
                        observer
                            .collect {
                                println("asdf")
                                recvKey = it
                            }
                    }

                // it should thus not be null here because we mock and
                // set listener to it.getArguments(0) whenever invoked
                assertThat(listener).isNotEqualTo(null)

                observer.collect()
                // and a call to .register should have been invoked
                verify {
                    mockSharedPreferences.registerOnSharedPreferenceChangeListener(listener)
                }

                listener?.onSharedPreferenceChanged(mockSharedPreferences, key)

                assertThat(recvKey).isEqualTo(key)
            }
}