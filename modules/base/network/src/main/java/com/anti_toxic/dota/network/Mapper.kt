package com.anti_toxic.dota.network

interface Mapper<Domain, Entity> {

    fun toEntity(value: Domain): Entity

    fun toDomain(value: Entity): Domain
}