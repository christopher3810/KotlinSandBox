package smy.mlong.coroutines.basic

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*

class ConcurrentCounterTest : DescribeSpec({
    describe("ConcurrentCounter 는") {
        val counter = ConcurrentCounter()

        context("concurrently 하게 counting을 하게 될 때") {
            it("job 의 전체 갯수를 카운트 할 수 있다..") {
                runBlocking {
                    val jobCount = 1000
                    val (totalCount, _) = counter.countConcurrently(jobCount)
                    totalCount shouldBe jobCount
                }
            }

            it("job의 각각의 process를 카운트 할 수 있다.") {
                runBlocking {
                    val jobCount = 1000
                    val (_, individualCounts) = counter.countConcurrently(jobCount)
                    individualCounts.values.sum() shouldBe jobCount
                    individualCounts.size shouldBe jobCount
                    individualCounts.all { it.value == 1 } shouldBe true
                }
            }

        }
    }
})