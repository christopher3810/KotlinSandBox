package smy.mlong.coroutines.basic

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExplicitJob {
    suspend fun runWithExplicitJob(): List<String> = coroutineScope {
        val result = mutableListOf<String>()
        val job = launch {
            delay(1000L)
            result.add("World!")
        }
        result.add("Hello")
        job.join()
        result.add("Done")
        result
    }
}