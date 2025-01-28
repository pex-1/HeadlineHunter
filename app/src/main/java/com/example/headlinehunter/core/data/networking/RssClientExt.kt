package com.example.headlinehunter.core.data.networking

import com.example.headlinehunter.core.domain.util.DataError
import com.example.headlinehunter.core.domain.util.Result
import com.prof18.rssparser.RssParser
import com.prof18.rssparser.exception.RssParsingException
import com.prof18.rssparser.model.RssChannel
import kotlinx.coroutines.ensureActive
import java.net.UnknownHostException
import kotlin.coroutines.coroutineContext

suspend inline fun RssParser.get(
    link: String
): Result<RssChannel, DataError.Network> {
    return safeCall {
        getRssChannel(link)
    }
}

suspend inline fun safeCall(execute: () -> RssChannel): Result<RssChannel, DataError.Network> {
    val response = try {
        execute()
    } catch (e: UnknownHostException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: RssParsingException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.RSS_PARSING_EXCEPTION)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(DataError.Network.UNKNOWN)
    }

    return Result.Success(response)
}