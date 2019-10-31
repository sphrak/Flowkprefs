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

import androidx.annotation.CheckResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

interface IKPreference<T> : FlowCollector<T> {

    @CheckResult
    fun key(): String

    @CheckResult
    fun defaultValue(): T

    fun get(): T

    fun set(value: T)

    @CheckResult
    fun isSet(): Boolean

    fun delete()

    @CheckResult
    fun observe(): Flow<T>

    fun asObservable(): Flow<T>

    fun asConsumer(): FlowCollector<T>
}