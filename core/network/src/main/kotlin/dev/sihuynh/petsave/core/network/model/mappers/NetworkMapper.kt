package dev.sihuynh.petsave.core.network.model.mappers

interface NetworkMapper<E, D> {
    fun mapToDomain(networkEntity: E): D
}