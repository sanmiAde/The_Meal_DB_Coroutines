package com.sanmidev.themealdbcoroutines.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

suspend fun runOnMain( block : () -> Unit){
    withContext(Dispatchers.Main){
        block.invoke()
    }
}

/**
 * [oa_6-01-2021] Kotlin currently does not allow you to return a result class.
 * you can enable adding a compiler option.
 * https://stackoverflow.com/questions/52631827/why-cant-kotlin-result-be-used-as-a-return-type
 */
suspend inline fun <R> runSuspendCatching(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch(c: CancellationException) {
        throw c
    } catch (e: Throwable) {
        Result.failure(e)
    }
}