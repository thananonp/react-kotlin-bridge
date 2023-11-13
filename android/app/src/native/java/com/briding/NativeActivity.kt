package com.briding

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.briding.nativemodule.Person
import com.briding.nativemodule.PersonFragment
import com.briding.nativemodule.PersonBlueprint
import com.facebook.react.ReactActivity
import com.facebook.react.ReactFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NativeActivity : ReactActivity() {

    override fun getMainComponentName(): String {
        return "bridingNative"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native)
        val viewModel = NativePersonBlueprint()
        supportFragmentManager.beginTransaction()
            .add(R.id.native_activity, PersonFragment(viewModel)).addToBackStack("Native").commit()

        val openReactButton = findViewById<Button>(R.id.open_react_btn)
        openReactButton.setOnClickListener {
            val reactNativeFragment =
                ReactFragment.Builder().setComponentName(mainComponentName).setFabricEnabled(false)
                    .build()

            supportFragmentManager.beginTransaction()
                .replace(R.id.native_activity, reactNativeFragment).addToBackStack("React").commit()
        }
    }
}

class NativePersonBlueprint : PersonBlueprint, ViewModel() {
    private val defaultPerson = Person("Default", "Native", 2333, listOf())

    override var people: MutableStateFlow<MutableList<Person>> =
        MutableStateFlow(mutableListOf(defaultPerson))
    override var onPress: (person: Person) -> Unit = {
        Log.d("NativePersonBlueprint", "onPress: ${it.age}")
    }
    override var onAddNewPerson: () -> Unit = {
        people.value = (people.value + Person(
            getRandomString(5), getRandomString(6), 40, listOf()
        )).toMutableList()
    }
    override var onDeleteAllPerson: () -> Unit = {
        people.value = mutableListOf()
    }
    override var isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override var refresh: () -> Unit = {
        viewModelScope.launch {
            isRefreshing.value = true
            delay(2000)
            people.value = mutableListOf(defaultPerson)
            isRefreshing.value = false
        }
    }

    private fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length).map { allowedChars.random() }.joinToString("")
    }
}