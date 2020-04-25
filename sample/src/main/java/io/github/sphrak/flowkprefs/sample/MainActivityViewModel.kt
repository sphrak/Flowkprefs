/*
 * Copyright 2020 Niclas Kron
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

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityViewModel constructor(
    private val channel: ConflatedBroadcastChannel<MainActivityView.Event>,
    private var state: MainActivityView.State = MainActivityView.State()
) {

    suspend operator fun invoke(): Flow<MainActivityView.State> = channel
        .openSubscription()
        .receive()
        .let {
            event: MainActivityView.Event ->
            Timber.d("ASDF INVOKE: $event")
            reduce(event)
        }

    private fun reduce(e: MainActivityView.Event): Flow<MainActivityView.State> = when (e) {
        MainActivityView.Event.OnCreate -> onCreateEvent()
        MainActivityView.Event.OnViewCreated -> onResumeEvent()
        is MainActivityView.Event.OnItemClicked ->onItemClickedEvent(e.value)
    }

    private fun onItemClickedEvent(value: String): Flow<MainActivityView.State> = flow {
        val renderEvent = MainActivityView.RenderEvent.ItemClicked(value)
        state = state.copy(renderEvent = renderEvent)
        emit(state)
    }

    private fun onCreateEvent(): Flow<MainActivityView.State> = flow {
        val renderEvent = MainActivityView.RenderEvent.DisplayStringList(getRandomStringList())
        state = state.copy(renderEvent = renderEvent)
        emit(state)
    }

    private fun onResumeEvent(): Flow<MainActivityView.State> = flow {
        // no-op
    }

    private fun getRandomStringList(): List<String> {
        val list: MutableList<String> = mutableListOf()
        repeat((1..32).count()) {
            list.add(getRandomString())
        }
        return list
    }

    private fun getRandomString() : String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        return (1..32)
            .map { allowedChars.random() }
            .joinToString("")
    }

}