package io.github.sphrak.flowkprefs

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import io.github.sphrak.flowkprefs.extension.flowkPrefs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class FlowKPreferenceTest {

    private companion object {
        const val PREF_KEY = "42"
        const val PREF_MODE = MODE_PRIVATE
        const val DEFAULT_VALUE = "asdf"
    }

    private var mockSharedPreferences = mock(SharedPreferences::class.java)
    private lateinit var sharedPreferences: SharedPreferences

    private val context: Context = mock<Context>(Context::class.java)
    private var editor: SharedPreferences.Editor =
        mock(SharedPreferences.Editor::class.java)

    private var listener = mock(SharedPreferences.OnSharedPreferenceChangeListener::class.java)

    private lateinit var flowkPrefs: IFlowKPreference

    @Before
    fun setup() {

        doReturn(mockSharedPreferences).`when`(context).getSharedPreferences(PREF_KEY, PREF_MODE)
        sharedPreferences = context.getSharedPreferences(PREF_KEY, PREF_MODE)

        doReturn(editor).`when`(sharedPreferences).edit()
        doNothing().`when`(sharedPreferences)
            .registerOnSharedPreferenceChangeListener(listener)

        flowkPrefs = flowkPrefs(sharedPreferences)
    }

    /*@Test
    fun `observe value change`() = runBlockingTest {

        val key = "secret_key"
        val observer = (flowkPrefs as FlowKPreference).onKeyChange

        assertThat(listener).isNotEqualTo(null)

        flowkPrefs.string(key, "asdfasdf")
        observer.collect {
            println(it)
        }

        //verify(sharedPreferences).registerOnSharedPreferenceChangeListener(listener)
    }*/
}