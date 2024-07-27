package smy.mlong.coroutines.basic

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*

class LightweightCoroutinesTest : DescribeSpec({
    describe("LightweightCoroutines 는") {
        context("5만개의 코루틴이 생성되고 실행되어 질 때") {
            it("결과를 올바른 순서로 반환해야 한다.") {
                runBlocking {
                    val count = 1000 // 테스트를 위해 더 작은 수로 설정
                    val result = Lightweightcoroutines().launchManyCoroutines(count)
                    result shouldBe count
                }
            }
        }
    }
})