package ru.virtual.core_android

interface Mapper<E, DTO> {

    fun map(item: DTO): E

    fun map(items: List<DTO>): List<E> = items.map(::map)

}