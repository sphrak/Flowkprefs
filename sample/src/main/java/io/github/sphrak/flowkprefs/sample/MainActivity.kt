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

package io.github.sphrak.flowkprefs.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.sphrak.flowkprefs.IFlowKPreference
import io.github.sphrak.flowkprefs.extension.flowkPrefs
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(R.layout.activity_main), CoroutineScope {

    private companion object {
        const val INT_KEY: String = "LAUNCHED_AT"
    }

    private val flowKPrefs: IFlowKPreference by lazy {
        flowkPrefs(this, coroutineScope)
    }

    private val coroutineScope by lazy {
        CoroutineScope(coroutineContext)
    }

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private val value: Int get()
        = System.currentTimeMillis().toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("ON CREATE")

        observe()

        launch {
            activityMainButton
                .setOnClickListener {
                    Timber.d("ON CLICK: $value")
                    write(INT_KEY, value)
                }
        }
    }

    override fun onResume() {
        super.onResume()
        observe()
    }

    override fun onStop() {
        super.onStop()
        cancel()
    }

    private fun observe() {
        launch {
            flowKPrefs.integer(INT_KEY)
                .observe()
                .collect { value: Int ->
                    Timber.d("OBSERVER #1: $value")
                    upperLeft.text = value.toString()
                }
        }
        launch {
            flowKPrefs.integer(INT_KEY)
                .observe()
                .collect { value: Int ->
                    Timber.d("OBSERVER #2: $value")
                    upperRight.text = value.toString()
                }
        }
        launch {
            flowKPrefs.integer(INT_KEY)
                .observe()
                .collect { value: Int ->
                    Timber.d("OBSERVER #3: $value")
                    lowerLeft.text = value.toString()
                }
        }
        launch {
            flowKPrefs.integer(INT_KEY)
                .observe()
                .collect { value: Int ->
                    Timber.d("OBSERVER #4: $value")
                    lowerRight.text = value.toString()
                }
        }
        launch {
            flowKPrefs.integer(INT_KEY)
                .observe()
                .collect { value: Int ->
                    Timber.d("OBSERVER #5: $value")
                    Toast.makeText(applicationContext, "$value changed!", Toast.LENGTH_LONG)
                        .show()
                }
        }
    }

    private fun read(key: String): String =
        when (key) {
            INT_KEY -> flowKPrefs
                .string(key = key)
                .get()
            else -> "unhandled key: $key"
        }

    private fun write(key: String, value: Int): Unit =
        when (key) {
            INT_KEY -> {
                Timber.d("WRITE value $value to $key key")
                flowKPrefs
                    .integer(key, 0)
                    .set(value = value)
            }
            else -> println("unhandled key: $key")
        }

}
