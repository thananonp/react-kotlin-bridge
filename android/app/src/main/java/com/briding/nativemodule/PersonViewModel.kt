package com.briding.nativemodule

abstract class PersonViewModel {
    abstract var people: List<Person>
    abstract var onPress: (person: Person) -> Unit
}