package com.briding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.briding.nativemodule.Person
import com.briding.nativemodule.PersonFragment
import com.briding.nativemodule.PersonViewModel
import com.facebook.react.ReactActivity
import com.facebook.react.ReactFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class NativeActivity : ReactActivity() {

    override fun getMainComponentName(): String {
        return "bridingNative"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native)
        val viewModel = NativePersonViewModel()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.native_activity, PersonFragment(viewModel))
            .addToBackStack("Native")
            .commit()

        val openReactButton = findViewById<Button>(R.id.open_react_btn)
        openReactButton.setOnClickListener {
            val reactNativeFragment = ReactFragment.Builder()
                .setComponentName(mainComponentName)
                .setFabricEnabled(false)
                .build()

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.native_activity, reactNativeFragment)
                .addToBackStack("React")
                .commit()
        }
    }
}

class NativePersonViewModel : PersonViewModel() {
    override var people: MutableStateFlow<MutableList<Person>> =
        MutableStateFlow(mutableListOf(Person("I'm", "Native", 2333, listOf())))
    override var onPress: (person: Person) -> Unit = {
        Log.d("NativePersonViewModel", "onPress: ${it.age}")
    }
    override var onAddNewPerson: () -> Unit = {
        people.value = (people.value + Person(getRandomString(5), getRandomString(6), 40, listOf())).toMutableList()
    }

    private fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}