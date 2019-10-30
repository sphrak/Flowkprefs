package io.github.sphrak.flowkprefs

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import io.github.sphrak.flowkprefs.adapter.StringAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@FlowPreview
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class KPreferenceTest {

    private companion object {
        const val PREF_KEY = "KEY"
        const val DEFAULT_VALUE = "asdf"
    }

    private val keyChange = flowOf(PREF_KEY)
    private val adapter = mock(StringAdapter::class.java)

    private lateinit var sharedPreferences: SharedPreferences

    private val context: Context = mock(Context::class.java)
    private var editor: SharedPreferences.Editor = mock(SharedPreferences.Editor::class.java)
    private var sharedPreferencesInstance: SharedPreferences = mock(SharedPreferences::class.java)

    private lateinit var kPreferenceString: KPreference<String>

    @Before
    fun setup() {
        doReturn(sharedPreferencesInstance)
            .`when`(context)
            .getSharedPreferences(PREF_KEY, MODE_PRIVATE)

        doReturn(editor).`when`(sharedPreferencesInstance).edit()

        sharedPreferences = context.getSharedPreferences(PREF_KEY, MODE_PRIVATE)
        editor = sharedPreferences.edit()

        `when`(sharedPreferences.contains(any())).thenAnswer {
            val key: String = it.getArgument<String>(0)
            return@thenAnswer sharedPreferences.getString(key, "") != null
        }

        kPreferenceString = KPreference(
            sharedPreferences = sharedPreferences,
            key = PREF_KEY,
            defaultValue = DEFAULT_VALUE,
            onKeyChange = keyChange,
            adapter = adapter
        )
    }

    @Test
    fun `isSet is false`() {
        assertThat(kPreferenceString.isSet())
            .isFalse()
    }

    @Test
    fun `isSet is true`() {
        doReturn("asdf")
            .`when`(sharedPreferences)
            .getString(eq(PREF_KEY), any())

        assertThat(kPreferenceString.isSet())
            .isTrue()
    }

    @Test
    fun `do get value`() {
        val expectedValue = "lagom är bäst"

        doReturn(expectedValue).`when`(adapter).get(PREF_KEY, sharedPreferences)
        doReturn(expectedValue).`when`(sharedPreferences).getString(eq(PREF_KEY), any())

        val result = kPreferenceString.get()
        assertThat(result).isEqualTo(expectedValue)
    }

    @Test
    fun `do delete value`() {
        doReturn(editor).`when`(editor).remove(PREF_KEY)
        kPreferenceString.delete()
        verify(editor.remove(PREF_KEY)).apply()
    }

    @Test
    fun `do single observe value`() = runBlockingTest {

        val nextValue = "no"
        var captured: String? = null

        doReturn(editor).`when`(editor).putString(PREF_KEY, nextValue)
        doReturn(true).`when`(sharedPreferences).contains(PREF_KEY)
        doReturn(nextValue).`when`(adapter).get(PREF_KEY, sharedPreferences)

        assertThat(kPreferenceString.isSet()).isTrue()

        kPreferenceString
            .observe()
            .collect {
                captured = it
            }

        kPreferenceString.set(nextValue)

        assertThat(captured).isEqualTo(nextValue)
    }

    @Test
    fun `do single observe value multiple observers`() = runBlockingTest {

        val nextValue = "no"
        var observed1: String? = null
        var observed2: String? = null
        var observed3: String? = null

        doReturn(editor).`when`(editor).putString(PREF_KEY, nextValue)
        doReturn(true).`when`(sharedPreferences).contains(PREF_KEY)
        doReturn(nextValue).`when`(adapter).get(PREF_KEY, sharedPreferences)

        assertThat(kPreferenceString.isSet()).isTrue()

        kPreferenceString
            .observe()
            .collect {
                observed1 = it
            }

        kPreferenceString
            .observe()
            .collect {
                observed2 = it
            }

        kPreferenceString
            .observe()
            .collect {
                observed3 = it
            }

        kPreferenceString.set(nextValue)

        assertThat(observed1).isEqualTo(nextValue)
        assertThat(observed2).isEqualTo(nextValue)
        assertThat(observed3).isEqualTo(nextValue)
    }
}