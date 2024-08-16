# Coroutine 기본 구조와 개념

코틀린 코루틴은 비동기 프로그래밍을 위한 강력한 도구.

## 1. 코루틴의 기본 구조

코루틴은 기본적으로 경량 스레드라고 볼 수 있음. 

하지만 실제 OS 스레드와는 다르게 작동. 

코루틴은 suspend와 resume이 가능한 계산 단위. 

이는 코루틴이 실행 중 특정 지점에서 일시 중단되었다가 나중에 해당 지점부터 다시 실행될 수 있음을 의미.

### 1.1 코루틴의 시작점: 코루틴 빌더

모든 코루틴은 코루틴 빌더로부터 시작. 

주요 코루틴 빌더

- `launch`: 별도의 코루틴을 시작하며, 결과를 반환하지 않음
- `async`: `launch`와 유사하지만, 결과를 `Deferred<T>` 형태로 반환.
- `runBlocking`: 새 코루틴을 시작하고, 완료될 때까지 현재 스레드를 차단.

다음과 같이 `launch`를 사용 가능:

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello")
}
```

이 코드는 "Hello"를 즉시 출력한 후, 1초 후에 "World!"를 출력.

### 1.2 suspend 함수

`suspend` 키워드는 함수가 코루틴 내에서 실행될 수 있고, 일시 중단될 수 있음을 나타냄. 

example

```kotlin
suspend fun doSomethingLong(): String {
    delay(1000L) // 비차단(non-blocking) 방식으로 1초 대기
    return "Done"
}
```

`suspend` 함수는 다른 `suspend` 함수 내에서만 호출될 수 있으며, 코루틴 빌더 내에서 사용될 수 있음.

## 2. 코루틴 컨텍스트와 디스패처

---

### 2.1 코루틴 컨텍스트 (CoroutineContext)

`CoroutineContext`는 코루틴의 다양한 설정과 정보를 포함하는 집합체.

- `Job`: 코루틴의 생명주기 관리
- `CoroutineDispatcher`: 코루틴이 실행될 스레드 결정
- `CoroutineName`: 디버깅을 위한 코루틴 이름
- `CoroutineExceptionHandler`: 예외 처리 방식 정의

### 2.2 코루틴 디스패처 (CoroutineDispatcher)

디스패처는 코루틴이 어떤 스레드 또는 스레드 풀에서 실행될지 결정. 

주요 디스패처

- `Dispatchers.Default`: CPU 집약적 작업용
- `Dispatchers.IO`: I/O 작업용
- `Dispatchers.Main`: UI 관련 작업용 (Android 등)
- `Dispatchers.Unconfined`: 특정 스레드에 국한되지 않음

example

```kotlin
launch(Dispatchers.Default) {
    // CPU 집약적인 작업 수행
}
```

