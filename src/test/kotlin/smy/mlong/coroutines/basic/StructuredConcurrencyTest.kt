package smy.mlong.coroutines.basic

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*

class StructuredConcurrencyTest : DescribeSpec({
    describe("StructuredConcurrency 는") {
        context("코루틴을 여러번 실행할 때") {
            it("결과를 올바른 순서로 반환해야 한다.") {
                runBlocking {
                    val result = StructuredCnoncurrency().runStructuredConcurrency()
                    result shouldBe listOf("Hello", "World 1", "World 2", "Done")
                }
            }
        }
    }
})