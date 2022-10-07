package com.anti_toxic.dota.network

interface Mapper<Dto, Entity, Domain> {

    fun entityToDomain(entity: Entity): Domain

    fun dtoToDomain(dto: Dto): Domain

    fun dtoToEntity(dto: Dto): Entity
}