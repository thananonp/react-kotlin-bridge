package com.briding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.briding.nativemodule.Person
import com.briding.nativemodule.PersonFragment
import com.briding.nativemodule.PersonViewModel

class NativeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native)
        val viewModel = NativePersonViewModel()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.native_activity, PersonFragment(viewModel))
            .addToBackStack(null)
            .commit()
    }
}
class NativePersonViewModel: PersonViewModel() {
    override var people: List<Person> = listOf(Person("I'm", "Native", 2333, listOf()))

    override fun onPress(person: Person) {
        Log.d("NativePersonViewModel", "onPress: ${person.age}")
    }
}