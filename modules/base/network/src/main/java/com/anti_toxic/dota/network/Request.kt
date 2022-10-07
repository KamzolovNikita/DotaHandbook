package com.anti_toxic.dota.network

import com.anti_toxic.dota.core_api.Resource.Error
import com.anti_toxic.dota.core_api.Resource.Loading
import com.anti_toxic.dota.core_api.Resource.Success
import com.anti_toxic.dota.core_api.rethrowOnCancellation
import com.anti_toxic.dota.core_api.retryOnNetworkAndServerErrors
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import timber.log.Timber

suspend inline fun <Dto, Entity, Domain> request(
    crossinline fetchCache: suspend () -> Entity?,
    crossinline fetchRemote: suspend () -> Dto?,
    crossinline updateCache: suspend (Entity) -> Unit,
    mapper: Mapper<Dto, Entity, Domain>,
    forceUpdate: Boolean
) = flow {
    if (!forceUpdate) {
        emit(Loading())
        val cacheResponse = try {
            fetchCache()
        } catch (e: Exception) {
            e.rethrowOnCancellation()
            Timber.e(e, "Error fetching cache")
            null
        }
        cacheResponse?.let {
            emit(Success(mapper.entityToDomain(it)))
        }
    }

    try {
        retryOnNetworkAndServerErrors {
            val remoteResponse = fetchRemote()
            if (remoteResponse != null) {
                emit(Success(mapper.dtoToDomain(remoteResponse)))
                updateCache(mapper.dtoToEntity(remoteResponse))
            }
        }
    } catch (e: Exception) {
        e.rethrowOnCancellation()
        Timber.e(e, "Error fetching remote")
        emit(Error(e))
    }
}.distinctUntilChanged()