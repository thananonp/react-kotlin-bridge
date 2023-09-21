package com.briding.nativemodule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment

@OptIn(ExperimentalMaterialApi::class)
class PersonFragment(
    private val persons: List<Person>, private val onClick: (person: Person) -> Unit
) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    Column {
                        persons.forEach { person ->
                            Card(onClick = { onClick(person) }) {
                                Text(text = "${person.name} ${person.surname} \nage ${person.age}"  )
                            }
                        }
                    }
                }
            }
        }

    }
}