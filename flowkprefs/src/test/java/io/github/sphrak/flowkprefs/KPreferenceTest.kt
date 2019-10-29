package io.github.sphrak.flowkprefs

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import io.github.sphrak.flowkprefs.adapter.StringAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
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

    companion object {
        const val PREF_KEY = "KEY"
        const val DEFAULT_VALUE = "asdf"
    }

    private val testScope = TestCoroutineScope()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val keyChange = flowOf<String>()
    private val adapter = StringAdapter()

    private lateinit var sharedPreferences: SharedPreferences

    private var context: Context = mock(Context::class.java)
    private var editor: SharedPreferences.Editor = mock(SharedPreferences.Editor::class.java)
    private var sharedPreferencesInstance: SharedPreferences = mock(SharedPreferences::class.java)

    private lateinit var kPreferenceString: KPreference<String>

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineDispatcher)
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

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
        testCoroutineDispatcher.cleanupTestCoroutines()
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
        `when`(sharedPreferences.getString(eq(PREF_KEY), any()))
            .thenReturn(expectedValue)

        val result = kPreferenceString.get()
        assertThat(result).isEqualTo(expectedValue)
    }

    @Test
    fun `do delete value`() {
        `when`(editor.remove(PREF_KEY)).thenReturn(editor)
        kPreferenceString.delete()
        verify(editor.remove(PREF_KEY)).apply()
    }

    @Test
    fun `do observe value`() = runBlockingTest {

        kPreferenceString
            .observe()

        val nextValue = "no"

        doReturn(editor).`when`(editor).putString(PREF_KEY, nextValue)

        kPreferenceString.set(nextValue)

        testScope.launch {
            kPreferenceString
                .observe()
                .collect {
                    println("AAAAAAAAAAAAAAAAAAAAAAAAAAA")
                }
        }
    }

    /*@Test
    fun `do consume value`() = runBlockingTest {

        `when`(editor.putString(any(), any()))
            .thenReturn(editor)

        preference
            .observe()
            .collect(

            )

        verify(editor.putString(PREF_KEY, value)).apply()
    }*/

    /**
     * @Test fun consumer() {
    whenever(prefsEditor.putString(any(), any()))
    .doReturn(prefsEditor)

    val emitter = PublishSubject.create<String>()
    emitter.subscribe(pref)

    val value = "wakanda forever"
    emitter.onNext(value)

    verify(prefsEditor.putString(PREF_KEY, value)).apply()
    }
     */
}