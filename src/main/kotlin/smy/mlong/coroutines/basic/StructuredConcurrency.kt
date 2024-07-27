package smy.mlong.coroutines.basic

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StructuredCnoncurrency {

    suspend fun doWorld(): List<String> = coroutineScope {
        val result = mutableListOf<String>()
        launch {
            delay(2000L)
            result.add("World 2")
        }
        launch {
            delay(1000L)
            result.add("World 1")
        }
        result.add("Hello")
        delay(2100L) // 모든 코루틴이 완료될 때까지 대기
        result.add("Done")
        result
    }

    suspend fun runStructuredConcurrency() = doWorld()
}