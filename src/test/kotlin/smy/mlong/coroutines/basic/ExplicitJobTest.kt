package smy.mlong.coroutines.basic

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*

class ExplicitJobTest : DescribeSpec({
    describe("ExplicitJob 는") {
        context("코루틴을 실행하고 job을 join할 때") {
            it("결과를 올바른 순서로 반환해야 한다.") {
                runBlocking {
                    val result = ExplicitJob().runWithExplicitJob()
                    result shouldBe listOf("Hello", "World!", "Done")
                }
            }
        }
    }
})