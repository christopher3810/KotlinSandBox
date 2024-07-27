package smy.mlong.coroutines.basic

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

class ConcurrentCounter {
    private val atomicCounter = AtomicInteger(0)
    private val mutex = Any()  // Kotlin의 synchronized 블록을 위한 락 객체
    private val counters = mutableMapOf<Int, Int>()

    suspend fun countConcurrently(jobCount: Int): Pair<Int, Map<Int, Int>> = coroutineScope {
        val jobs = List(jobCount) {
            async {
                processJob(it)
            }
        }
        jobs.awaitAll()
        Pair(atomicCounter.get(), synchronized(mutex) { counters.toMap() })
    }

    private suspend fun processJob(id: Int) {
        val processingTime = Random.nextLong(100, 500)
        delay(processingTime)

        atomicCounter.incrementAndGet()

        synchronized(mutex) {
            counters[id] = counters.getOrDefault(id, 0) + 1
        }
    }
}