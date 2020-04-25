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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class MainActivityAdapter(
    private val channel: ConflatedBroadcastChannel<MainActivityView.Event>,
    private val viewModel: MainActivityViewModel,
    private val coroutineScope: CoroutineScope
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listOfString: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder =
        MainActivityViewHolder(
            LayoutInflater.from(p0.context)
                .inflate(R.layout.rv_item, p0, false)
        )

    override fun getItemCount(): Int = listOfString.size

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) =
        (p0 as MainActivityViewHolder)
            .bind(listOfString[p1])

    fun updateList(newListOfString: List<String>) {
        listOfString.clear()
        listOfString.addAll(newListOfString)
        notifyDataSetChanged()
    }

    inner class MainActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView
                .rv_item_text
                .setOnClickListener {
                    coroutineScope.launch(coroutineScope.coroutineContext) {
                        Timber.d("ASDF CLICK EVENT: ${it.rv_item_text.text}")
                        val event = MainActivityView.Event.OnItemClicked(it.rv_item_text.text.toString())
                        channel.send(event)
                    }
                }
        }

        fun bind(value: String) {
            itemView.rv_item_text.text = value
        }

    }

}