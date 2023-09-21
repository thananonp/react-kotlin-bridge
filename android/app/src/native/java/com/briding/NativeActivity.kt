package com.briding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.briding.nativemodule.Person
import com.briding.nativemodule.PersonFragment

class NativeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native)
        val persons = listOf(Person("a", "b", 2, listOf()))
        supportFragmentManager
            .beginTransaction()
            .add(R.id.native_activity, PersonFragment(persons, {}))
            .addToBackStack(null)
            .commit()
    }
}