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
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

@ObsoleteCoroutinesApi
@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(R.layout.activity_main), CoroutineScope {

    private val channel: ConflatedBroadcastChannel<MainActivityView.Event> = ConflatedBroadcastChannel()

    private val viewModel: MainActivityViewModel = MainActivityViewModel(channel = channel)

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private val coroutineScope by lazy {
        CoroutineScope(coroutineContext)
    }

    private val mainActivityAdapter = MainActivityAdapter(channel, coroutineScope)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("ON CREATE")

        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mainActivityAdapter
        }


        channel.offer(MainActivityView.Event.OnCreate)

        launch(coroutineContext) {
            viewModel()
                .flowOn(
                    Dispatchers.IO
                ).collect(::render)
        }

    }

    suspend fun render(state: MainActivityView.State) {
        when (state.renderEvent) {
            MainActivityView.RenderEvent.Idle -> Unit
            is MainActivityView.RenderEvent.ItemClicked -> {
                Timber.d("ASDF RECEIVED: ${state.renderEvent.value}")
                val parentLayout: View = findViewById(android.R.id.content)
                Snackbar.make(parentLayout, state.renderEvent.value, Snackbar.LENGTH_SHORT)
                    .show()
            }
            is MainActivityView.RenderEvent.DisplayStringList -> displayText(state.renderEvent.listOfString)
        }
    }

    private fun displayText(list: List<String>) {
        mainActivityAdapter.updateList(list)
    }

}
