package com.anti_toxic.dota.core_api

interface DtoEntityMapper<DTO, ENTITY> {

    fun DTO.mapToEntity(): ENTITY
}