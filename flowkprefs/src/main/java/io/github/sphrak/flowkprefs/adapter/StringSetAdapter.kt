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

package io.github.sphrak.flowkprefs.adapter

import android.content.SharedPreferences

internal class StringSetAdapter : IPreferenceAdapter<MutableSet<String>> {

    companion object {
        val INSTANCE = StringSetAdapter()
    }

    override fun get(
        key: String,
        sharedPreferences: SharedPreferences
    ): MutableSet<String> =
        sharedPreferences
            .getStringSet(key, emptySet()) ?: mutableSetOf()

    override fun set(
        key: String,
        value: MutableSet<String>,
        editor: SharedPreferences.Editor
    ) {
        editor.putStringSet(key, value)
    }
}