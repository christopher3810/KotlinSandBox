# Coroutine Lifecycle

## 3. 코루틴의 생명주기

코루틴의 생명주기는 `Job` 인터페이스를 통해 관리. 

모든 코루틴은 자신만의 `Job`을 가지며, 이를 통해 상태를 확인하고 제어할 수 있음.

### 3.1 Job의 상태

Job은 다음과 같은 상태를 가질 수 있음.

1. `New`: 생성되었지만 아직 시작되지 않은 상태
2. `Active`: 실행 중인 상태
3. `Completing`: 실행은 완료되었지만 자식 작업이 완료되기를 기다리는 상태
4. `Completed`: 모든 작업이 완료된 상태
5. `Cancelling`: 취소 중인 상태
6. `Cancelled`: 취소된 상태

각 상태는 `isActive`, `isCompleted`, `isCancelled` 속성으로 확인할 수 있음.

```kotlin
val job = launch {
    // 코루틴 작업
}

println("isActive: ${job.isActive}")
println("isCompleted: ${job.isCompleted}")
println("isCancelled: ${job.isCancelled}")
```

### 3.2 부모-자식 관계

코루틴은 계층 구조를 가짐. 

한 코루틴 내에서 시작된 다른 코루틴은 자식 코루틴이 됨.

이 구조는 다음과 같은 특성을 가짐.

- 부모 코루틴이 취소되면 모든 자식 코루틴도 재귀적으로 취소됨.
- 부모 코루틴은 모든 자식 코루틴이 완료될 때까지 완료되지 않음.

```kotlin
val parentJob = launch {
    val childJob = launch {
        delay(1000L)
        println("Child completed")
    }
    println("Parent waiting")
    childJob.join() // 자식 작업이 완료될 때까지 대기
    println("Parent completed")
}
```

## 4. 코루틴 취소

코루틴의 취소는 cooperative 함. 

즉, 코루틴이 취소 요청을 확인하고 스스로 종료해야 함.

### 4.1 취소 방법

코루틴을 취소하는 주요 방법은 다음과 같습니다:

1. `Job.cancel()` 호출
2. 타임아웃 설정
3. 부모 코루틴 취소

`example`

```kotlin
val job = launch {
    repeat(1000) { i ->
        println("job: I'm sleeping $i ...")
        delay(500L)
    }
}
delay(1300L) // 1.3초 대기
println("main: I'm tired of waiting!")
job.cancel() // 작업 취소
job.join() // 취소가 완료될 때까지 대기
println("main: Now I can quit.")
```

### 4.2 취소 가능한 계산 작업

계산 작업의 경우, `isActive` 플래그를 주기적으로 확인하여 취소에 협조적으로 대응할 수 있음.

```kotlin
val startTime = System.currentTimeMillis()
val job = launch(Dispatchers.Default) {
    var nextPrintTime = startTime
    var i = 0
    while (isActive) { // 취소 가능한 계산 루프
        if (System.currentTimeMillis() >= nextPrintTime) {
            println("job: I'm sleeping ${i++} ...")
            nextPrintTime += 500L
        }
    }
}
delay(1300L)
println("main: I'm tired of waiting!")
job.cancelAndJoin()
println("main: Now I can quit.")
```

### 4.3 취소 불가능한 코드 블록

`withContext(NonCancellable) { ... }` 를 사용하여 취소할 수 없는 코드 블록을 정의할 수 있음. 

이는 주로 리소스를 정리하는 데 사용.

```kotlin
val job = launch {
    try {
        repeat(1000) { i ->
            println("job: I'm sleeping $i ...")
            delay(500L)
        }
    } finally {
        withContext(NonCancellable) {
            println("job: I'm running finally")
            delay(1000L)
            println("job: And I've just delayed for 1 sec because I'm non-cancellable")
        }
    }
}
delay(1300L)
println("main: I'm tired of waiting!")
job.cancelAndJoin()
println("main: Now I can quit.")
```