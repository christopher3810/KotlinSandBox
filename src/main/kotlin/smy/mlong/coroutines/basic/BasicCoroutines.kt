package smy.mlong.coroutines.basic

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BasicCoroutines {

    suspend fun doWorld(): String {
        delay(1000L)
        return "World!"
    }

    suspend fun runBasicCoroutine(): List<String> = coroutineScope {
        val result = mutableListOf<String>()
        launch {
            result.add(doWorld())
        }
        result.add("Hello")
        delay(1100L) // 모든 코루틴이 완료될 때까지 대기
        result
    }
}