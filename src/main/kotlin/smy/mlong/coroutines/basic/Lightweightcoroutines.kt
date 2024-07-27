package smy.mlong.coroutines.basic

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Lightweightcoroutines {
    suspend fun launchManyCoroutines(count: Int = 50_000): Int = coroutineScope {
        val jobs = List(count) {
            launch {
                delay(5000L)
            }
        }
        jobs.forEach { it.join() }
        count
    }
}