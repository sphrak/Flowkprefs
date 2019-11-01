[![travis-ci](https://travis-ci.org/sphrak/flowkprefs.svg?branch=master)](https://travis-ci.org/sphrak/flowkprefs)
[![Bintray](https://img.shields.io/bintray/v/sphrak/flowkprefs/flowkprefs)](https://bintray.com/sphrak/flowkprefs/flowkprefs)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/sphrak/flowkprefs/blob/master/LICENSE)

# 🛸 Flowkprefs
This is a Kotlin Coroutine/Flow based reimplementation of Aidan Follestad's [rxkprefs](https://github.com/afollestad/rxkprefs) library.
The only difference is that this library uses the Kotlin [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) and [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html#asynchronous-flow) APIs.

<img src="https://raw.githubusercontent.com/sphrak/Flowkprefs/master/showcase.png" width="500" />

### Gradle Dependency 
Make sure that you have either `jcenter()` or `mavenCentral()` in the list of repositories.
```groovy
repositories {
    jcenter()
}
```

**build.gradle.kts:**

```kotlin
implementation("io.github.sphrak:flowkprefs:1.0.0")
```

**build.gradle:**

```groovy
implementation "io.github.sphrak:flowkprefs:1.0.0"
```

### Getting started
Retrieve an instance of `flowkprefs`.

```kotlin
// Parameter is your Context, like an Activity, uses PreferenceManager#getDefaultSharedPreferences
val myPrefs = flowkPrefs(this)

// First parameter is your Context, like an Activity, the second is a key.
val myPrefs = flowkPrefs(this, "my_prefs")

// The optional third parameter is a mode, it defaults to MODE_PRIVATE above.
// This is like using Context.getSharedPreferences("my_prefs", MODE_PRIVATE)
val myPrefs = flowkPrefs(this, "my_prefs", MODE_PRIVATE)
```

### Retrieving a KPreference
With a `FlowKPreference` instance you can retrieve `IKPreference` preference which packs additional functionality.

```kotlin
val myPrefs: FlowKPreference = // ...

// Getting a string preference is as simple as this:
val myString: KPreference<String> = myPrefs.string("my_string", "default_value")

// You could omit the second parameter to use the default, default value (empty string)
val myString: KPreference<String> = myPrefs.string("my_string")
```

### Interacting with a KPreference


```kotlin
val myPref: KPreference<Int> = // ...

// The key of the preference - first parameter passed in prefs.integer(...) or any other pref getter
// This is always a String.
val key: String = myPref.key()

// The default value of the preference - second parameter passed in prefs.integer(...) or any other pref getter...
// Or the primitive default, such as an empty string, 0, or false.
val defaultValue: Int = myPref.defaultValue()

// The current value of the preference, or the default value if none.
val currentValue: Int = myPref.get()

// Changes the value of the preference.
myPref.set(1024)

// True if a value has been set, otherwise false.
val isSet: Boolean = myPref.isSet()

// Deletes any existing value for the preference.
myPref.delete()

val observable: Flow<Int> = myPref.observe()
```

### KPreference observable
To be notified when a preference changes simply observe the key.

```kotlin
val myPref: KPreference<Long> = // ...
val observable = myPref.observe()

observable
    .collect {
        // use the value $it
    }
```

### KPreference Consumer
`KPreference` acts as a Kotlin Flow `FlowCollector<T>`. You can use this to save preference values from the emissions of a `Flow`.

For example wiht [corbind](https://github.com/LDRAlighieri/Corbind) which provides Kotlin Flow bindings for views. 

```kotlin
val myPref: KPreference<Boolean> = // ...

exampleButton
    .clicks()
    .collect(myPref)
```

Whenever the checkbox is checked or unchecked, the underlying boolean shared preference is set to `true` or `false` automatically.

### License

	Copyright 2019 Niclas Kron

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
