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

object MainActivityView {

    sealed class Event {
        object OnCreate : Event()
        object OnViewCreated : Event()
        data class OnItemClicked(val value: String) : Event()
    }

    data class State(
        val currentTime: Long = 0L,
        val renderEvent: RenderEvent = RenderEvent.Idle
    )

    sealed class RenderEvent {
        object Idle : RenderEvent()
        data class ItemClicked(val value: String) : RenderEvent()
        data class DisplayStringList(val listOfString: List<String>): RenderEvent()
    }
}