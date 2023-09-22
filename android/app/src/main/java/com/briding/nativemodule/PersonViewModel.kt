package com.briding.nativemodule

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class PersonViewModel: ViewModel() {
    abstract var people: MutableStateFlow<MutableList<Person>>
    abstract var onPress: (person: Person) -> Unit
    abstract var onAddNewPerson: () -> Unit
}