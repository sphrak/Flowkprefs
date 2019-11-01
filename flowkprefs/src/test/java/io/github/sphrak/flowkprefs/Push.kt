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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class Push {

    private companion object {
        const val PREF_KEY = "42"
        const val PREF_MODE = Context.MODE_PRIVATE
    }

    private val mockSharedPreferences = mock(SharedPreferences::class.java)
    private val mockEditor = mock(SharedPreferences.Editor::class.java)

    private val mockContext = mock(Context::class.java)
    private val testCoroutineScope = TestCoroutineScope()

    private lateinit var testFlowkPrefs: IFlowKPreference
    private var listener: SharedPreferences.OnSharedPreferenceChangeListener? = null

    @Before
    fun setup() {

        doReturn(mockSharedPreferences)
            .`when`(mockContext)
            .getSharedPreferences(PREF_KEY, PREF_MODE)

        doReturn(mockEditor)
            .`when`(mockSharedPreferences).edit()

        doAnswer {
            listener = it.getArgument(1)
            Unit
        }.`when`(
            mockSharedPreferences
        ).registerOnSharedPreferenceChangeListener(
            any(
                SharedPreferences.OnSharedPreferenceChangeListener::class.java
            )
        )

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

                // it should thus not be null here because we mock and
                // set listener to it.getArguments(0) whenever invoked
                assertThat(listener).isNotEqualTo(null)

                // and a call to .register should have been invoked
                verify(mockSharedPreferences).registerOnSharedPreferenceChangeListener(listener)

                listener?.onSharedPreferenceChanged(mockSharedPreferences, key)

                testCoroutineScope
                    .launch {
                        observer
                            .collect {
                                println("asdf")
                                recvKey = it
                            }
                    }

                assertThat(recvKey).isEqualTo(key)
            }

}