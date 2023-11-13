package com.briding.nativemodule

import kotlinx.coroutines.flow.MutableStateFlow

interface PersonBlueprint {
    var people: MutableStateFlow<MutableList<Person>>
    var onPress: (person: Person) -> Unit
    var onAddNewPerson: () -> Unit
    var onDeleteAllPerson: () -> Unit

    var isRefreshing: MutableStateFlow<Boolean>
    var refresh: () -> Unit
}