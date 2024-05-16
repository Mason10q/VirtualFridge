package ru.virtual.core_android

interface Mapper<E, T> {

    fun map(item: T): E

    fun map(items: List<T>): List<E> = items.map(::map)

}