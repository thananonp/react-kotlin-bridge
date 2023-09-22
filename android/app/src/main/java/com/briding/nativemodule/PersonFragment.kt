package com.briding.nativemodule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment

@OptIn(ExperimentalMaterialApi::class)
class PersonFragment(
    var viewModel: PersonViewModel
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
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        Button(onClick = viewModel.onAddNewPerson) {
                            Text("Add +")
                        }
                        viewModel.people.collectAsState().value.forEach { person ->
                            Card(onClick = { viewModel.onPress(person) }) {
                                Text(text = "${person.name} ${person.surname} \nage ${person.age}")
                            }
                        }
                    }
                }
            }
        }

    }
}