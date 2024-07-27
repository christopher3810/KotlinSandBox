package smy.mlong.coroutines.basic

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking

class BasicCoroutinesTest : DescribeSpec({
    describe("BasicCoroutines 는") {
        context("코루틴을 실행할 때") {
            it("결과를 올바른 순서로 반환해야 한다.") {
                runBlocking {
                    val result = BasicCoroutines().runBasicCoroutine()
                    result shouldBe listOf("Hello", "World!")
                }
            }
        }
    }
})