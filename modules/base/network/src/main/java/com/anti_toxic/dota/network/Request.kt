package com.anti_toxic.dota.network

import com.anti_toxic.dota.core_api.Resource
import com.anti_toxic.dota.core_api.Resource.Error
import com.anti_toxic.dota.core_api.Resource.Loading
import com.anti_toxic.dota.core_api.Resource.Success
import com.anti_toxic.dota.core_api.retryOnNetworkAndServerErrors
import com.anti_toxic.dota.core_api.safeTry
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import timber.log.Timber

suspend inline fun <Domain, Entity> request(
    crossinline fetchCache: suspend () -> Entity?,
    crossinline fetchRemote: suspend () -> Domain,
    crossinline updateCache: suspend (Entity) -> Unit,
    mapper: Mapper<Domain, Entity>,
    forceUpdate: Boolean
) = flow<Resource<Domain>> {
    if (!forceUpdate) {
        emit(Loading())
        val cacheResponse = safeTry(
            run = {
                fetchCache()
            },
            catch = {
                Timber.e(it, "Error fetching cache")
                null
            }
        )
        cacheResponse?.let { emit(Success(mapper.toDomain(it))) }
    }

    safeTry(
        run = {
            retryOnNetworkAndServerErrors {
                val remoteResponse = fetchRemote()
                emit(Success(remoteResponse))
                updateCache(mapper.toEntity(remoteResponse))
            }
        },
        catch = {
            Timber.e(it, "Error fetching remote")
            emit(Error(it))
        }
    )
}.distinctUntilChanged()