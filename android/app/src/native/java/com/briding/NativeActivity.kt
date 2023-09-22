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
            .addToBackStack(null)
            .commit()

        val openReactButton = findViewById<Button>(R.id.open_react_btn)
        openReactButton.setOnClickListener {
            val reactNativeFragment = ReactFragment.Builder()
                .setComponentName(mainComponentName)
                .setFabricEnabled(false)
                .build()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.native_activity, reactNativeFragment)
                .commit()
        }
    }
}

class NativePersonViewModel : PersonViewModel() {
    override var people: List<Person> = listOf(Person("I'm", "Native", 2333, listOf()))
    override var onPress: (person: Person) -> Unit = {
        Log.d("NativePersonViewModel", "onPress: ${it.age}")
    }
}