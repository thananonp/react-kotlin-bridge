package com.briding.nativemodule

import kotlinx.coroutines.flow.MutableStateFlow

abstract class PersonViewModel {
    abstract var people: MutableStateFlow<MutableList<Person>>
    abstract var onPress: (person: Person) -> Unit
    abstract var onAddNewPerson: () -> Unit
}