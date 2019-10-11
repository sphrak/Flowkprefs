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