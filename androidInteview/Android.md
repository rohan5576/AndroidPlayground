##  “What are the main types of dispatchers in Kotlin coroutines, and when would you use each?”

Expert Answer: “There are four main dispatchers, each optimized for specific use cases:

**Dispatchers.Main** — Confined to the main UI thread. I use this for UI updates and lightweight operations that must happen on the main thread due to Android’s thread safety requirements.

**Dispatchers.IO** — Optimized for I/O-bound operations with a shared pool of threads (minimum 64). Perfect for network calls, file operations, and database queries that involve waiting for external resources.

**Dispatchers.Default** — Designed for CPU-intensive work using a thread pool sized to match CPU cores. I use this for heavy computations, data parsing, or complex algorithms.

**Dispatchers.Unconfined** — Starts in the caller thread but isn’t confined after suspension. Rarely used in production due to unpredictable behavior.”

```Kotlin
// Practical example showing all dispatchers
class DataProcessor {
    suspend fun processUserData(userId: String): ProcessedData {
        // IO for network call
        val rawData = withContext(Dispatchers.IO) {
            apiService.getUserData(userId)
        }
       // Default for heavy processing
        val processedData = withContext(Dispatchers.Default) {
            performComplexCalculations(rawData)
        }
        // Main for UI update
        withContext(Dispatchers.Main) {
            updateProgressIndicator(100)
        }
        return processedData
    }
}
```


## “What’s the difference between launch(Dispatchers.IO) and withContext(Dispatchers.IO)?”
Expert Answer: “This is a crucial distinction that affects both performance and code structure:

**launch(Dispatchers.IO)** creates a new coroutine that runs concurrently. It’s fire-and-forget — you start it and don’t necessarily wait for the result.

**withContext(Dispatchers.IO)** temporarily switches the current coroutine’s dispatcher and returns a result. It’s sequential — the calling coroutine suspends until withContext completes.”

```
// launch - creates new concurrent coroutine
fun fetchDataConcurrently() {
    launch(Dispatchers.IO) {
        val data1 = apiCall1() // Runs concurrently
    }
    launch(Dispatchers.IO) {
        val data2 = apiCall2() // Runs concurrently
    }
    // Both calls happen simultaneously
}
// withContext - sequential execution
suspend fun fetchDataSequentially(): CombinedData {
    val data1 = withContext(Dispatchers.IO) {
        apiCall1() // Completes first
    }
    val data2 = withContext(Dispatchers.IO) {
        apiCall2() // Then this runs
    }
    return CombinedData(data1, data2)
}
```

## “How would you handle a scenario where you need to perform multiple network calls and update the UI with progress?”
Expert Answer: “This is a common real-world scenario that tests understanding of dispatcher coordination and UI thread management. Here’s my approach:”

Key Points to Mention:

“I separate concerns by using appropriate dispatchers for each operation type”
“Progress updates must happen on Main to ensure UI responsiveness”
**“I use withContext to maintain sequential flow while switching execution contexts”**

```
class ProgressiveDataLoader {
    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> = _progress
    suspend fun loadDataWithProgress(): List<DataItem> {
        val totalSteps = 5
        val results = mutableListOf<DataItem>()
        repeat(totalSteps) { step ->
            // Network call on IO dispatcher
            val data = withContext(Dispatchers.IO) {
                apiService.fetchDataBatch(step)
            }
            // Update progress on Main dispatcher
            withContext(Dispatchers.Main) {
                _progress.value = ((step + 1) * 100) / totalSteps
            }
            // Heavy processing on Default dispatcher
            val processedData = withContext(Dispatchers.Default) {
                processDataBatch(data)
            }
            results.addAll(processedData)
        }
        return results
    }
}
```



##  “How do you test code that uses different dispatchers?”
Expert Answer: “Testing dispatcher-based code requires replacing dispatchers with test-friendly alternatives. Here’s my testing strategy:”

```
// Production code with dependency injection
class UserRepository(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun fetchUser(id: String): User {
        return withContext(dispatcher) {
            apiService.getUser(id)
        }
    }
}
// Test setup
@ExperimentalCoroutinesApi
class UserRepositoryTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    private val testDispatcher = StandardTestDispatcher()
    private val repository = UserRepository(testDispatcher)
    @Test
    fun `fetchUser should return user data`() = runTest {
        // Given
        val userId = "123"
        val expectedUser = User(userId, "John Doe")
        // When
        val result = repository.fetchUser(userId)
        // Then
        assertEquals(expectedUser, result)
    }
}
```

## “Explain the internal workings of Dispatchers.IO thread pool”
Expert Answer: “Dispatchers.IO uses a sophisticated thread pool management system:

Thread Pool Size: Minimum of 64 threads, can grow as needed Sharing Mechanism: Threads are shared across all IO operations in the app Blocking Tolerance: Designed to handle blocking operations efficiently Thread Recycling: Idle threads are reused to minimize creation overhead”

```
// Understanding IO dispatcher behavior
suspend fun demonstrateIOConcurrency() {
    val startTime = System.currentTimeMillis()
// These 100 calls will run concurrently on IO thread pool
    val jobs = (1..100).map { id ->
        async(Dispatchers.IO) {
            delay(1000) // Simulates blocking IO operation
            "Result $id"
        }
    }
    val results = jobs.awaitAll()
    val totalTime = System.currentTimeMillis() - startTime
    println("Completed 100 operations in ${totalTime}ms")
    // Should complete in ~1000ms, not 100000ms due to parallelism
}
Advanced Insight: “The IO dispatcher’s thread pool can handle thousands of concurrent blocking operations, making it perfect for high-throughput scenarios like batch API calls.”
```



## “What happens if you perform a long-running operation on Dispatchers.Main?”
Expert Answer: “Performing long-running operations on Dispatchers.Main is one of the worst mistakes in Android development. It causes:

1. ANR (Application Not Responding) errors after 5 seconds
2. Frozen UI that can’t respond to user interactions
3. Poor user experience leading to app abandonment
4. Potential crashes in severe cases
5. 
Here’s a practical example of the problem and solution:”

```
// ❌ WRONG - This will freeze the UI
launch(Dispatchers.Main) {
    val result = performHeavyCalculation() // 10+ seconds
    updateUI(result)
}
// ✅ CORRECT - Proper dispatcher usage
launch(Dispatchers.Main) {
    showLoading()
    val result = withContext(Dispatchers.Default) {
        performHeavyCalculation() // Runs on background thread
    }
    updateUI(result)
    hideLoading()
}
```

## Design a system that downloads 1000 images efficiently”
Expert Answer: “This scenario tests understanding of concurrency control and resource management:”

```
class ImageDownloadManager {
    private val semaphore = Semaphore(50) // Limit concurrent downloads
    suspend fun downloadImages(imageUrls: List<String>): List<Image> {
        return imageUrls.map { url ->
            async(Dispatchers.IO) {
                semaphore.withPermit {
                    downloadSingleImage(url)
                }
            }
        }.awaitAll()
    }
    private suspend fun downloadSingleImage(url: String): Image {
        return withContext(Dispatchers.IO) {
            // Actual download logic
            httpClient.downloadImage(url)
        }
    }
}
```
Key Points:

“I use Semaphore to prevent overwhelming the server or device resources”
“Async with Dispatchers.IO provides optimal concurrency for I/O operations”
“AwaitAll ensures all downloads complete before returning results”




# Difference Between Synchronous and Asynchronous Programming in Android

## 📌 Synchronous Programming
- Tasks are executed **one after another**.
- Each task **waits for the previous one** to complete before starting.
- If a task takes longer (e.g., network call, file read), the **main thread is blocked**.
- This may cause the app to become **unresponsive** or even show **ANR (App Not Responding)** errors.

✅ Example:
```kotlin
fun fetchDataSync() {
    val data = apiCall() // Blocks until the response is received
    println(data)
}
```

# 📌 Asynchronous Programming in Android

## 🔹 What is Asynchronous Programming?
- Tasks are executed **independently** without blocking each other.  
- The **main thread** does not wait for the task to complete.  
- Long-running operations (like **API calls, database queries, file I/O**) are handled in a **background thread**.  
- Once the operation finishes, the **result is returned** via:
  - Callbacks  
  - Coroutines  
  - RxJava (Reactive Streams)  

✅ This approach keeps the app **smooth, fast, and responsive**.


## ✅ Example (using Kotlin Coroutines)

```kotlin
suspend fun fetchDataAsync() {
    val data = withContext(Dispatchers.IO) { apiCall() } // Runs in background thread
    println(data) // Executes on main thread without blocking UI
}
```

# 📌 Synchronous Network Call on Main Thread in Android

## ⚠️ What Happens
- The **main thread (UI thread)** gets **blocked** until the network call finishes.  
- If the call takes too long (slow internet, server delay), the **UI will freeze**.  
- Android will throw an **`android.os.NetworkOnMainThreadException`** starting from **API Level 11 (Honeycomb)** to prevent this bad practice.  
- If blocking is too long, the app may show an **ANR (Application Not Responding)** dialog.

---------

# 📌 Handling Multiple Concurrent API Calls that Depend on Each Other in Android

## 🔹 Problem
Sometimes you need to make multiple API calls where:  
1. **Call B** depends on the result of **Call A**.  
2. **Call C** may depend on both **Call A** and **Call B**.  

If done incorrectly (synchronously), this can **block the main thread** and cause a poor user experience.  


## ✅ Solution: Coroutines (Sequential Execution with Dependencies)

Kotlin Coroutines allow us to make dependent API calls **without blocking** the main thread.  

### Example
```kotlin
suspend fun fetchUserData() {
    withContext(Dispatchers.IO) {
        // Step 1: Call A - Fetch user profile
        val user = apiService.getUser()

        // Step 2: Call B - Fetch posts using userId from Call A
        val posts = apiService.getPosts(user.id)

        // Step 3: Call C - Fetch comments using first postId from Call B
        val comments = apiService.getComments(posts.first().id)

        // Now update UI with combined data
        withContext(Dispatchers.Main) {
            println("User: $user")
            println("Posts: $posts")
            println("Comments: $comments")
        }
    }
}
```

⚡ Alternative: Using async for Parallel + Dependency Handling
If two calls are independent, you can run them in parallel, then use results together.


suspend fun fetchDashboardData() = coroutineScope {
    // Run Call A and Call B in parallel
    val userDeferred = async { apiService.getUser() }
    val postsDeferred = async { apiService.getPosts(userDeferred.await().id) }

    val user = userDeferred.await()
    val posts = postsDeferred.await()

    println("User: $user")
    println("Posts: $posts")
}

🚀 Summary
Use Coroutines with withContext for sequential dependent calls.

Use async + await when you can fetch things in parallel, then combine results.

Always run API calls on Dispatchers.IO to avoid blocking the main thread.

  ---------------

# 📌 Difference Between `launch` and `async` in Kotlin Coroutines

## 🔹 `launch`
- **Purpose:** Fire-and-forget operations  
- **Returns:** `Job` object  
- **Use case:** Operations where you **don’t need a return value**  

---

## 🔹 `async`
- **Purpose:** Concurrent operations that **return a value**  
- **Returns:** `Deferred<T>` object  
- **Use case:** Operations where you **need the result**  

---

## ✅ Practical Comparison

```kotlin
class DataManager {
    fun demonstrateLaunchVsAsync() {
        lifecycleScope.launch {
            // Using launch - fire and forget
            launch {
                logUserActivity() // No return value needed
            }
            launch {
                syncDataInBackground() // No return value needed
            }

            // Using async - need return values
            val userData = async { fetchUserData() }
            val userPosts = async { fetchUserPosts() }

            // Wait for results and use them
            updateUI(userData.await(), userPosts.await())
        }
    }

    // Another example showing the difference
    suspend fun processData(): ProcessedResult {
        return coroutineScope {
            // Launch for side effects (logging, analytics)
            launch {
                analyticsService.trackEvent("data_processing_started")
            }

            // Async for values we need
            val processedData = async { heavyDataProcessing() }
            val metadata = async { generateMetadata() }

            ProcessedResult(
                data = processedData.await(),
                metadata = metadata.await()
            )
        }
    }
}
```
------
### How do you handle errors in asynchronous operations?
**Answer:**

Error handling in asynchronous operations requires different strategies:

1. **Try-Catch with Coroutines:**
```kotlin
suspend fun loadUserData(userId: String): Result<UserData> {
    return try {
        val userData = withContext(Dispatchers.IO) {
            apiService.getUser(userId)
        }
        Result.Success(userData)
    } catch (networkException: IOException) {
        Result.Error("Network error: Please check your connection")
    } catch (httpException: HttpException) {
        when (httpException.code()) {
            404 -> Result.Error("User not found")
            500 -> Result.Error("Server error, please try again later")
            else -> Result.Error("Something went wrong")
        }
    } catch (exception: Exception) {
        Result.Error("Unexpected error: ${exception.message}")
    }
}
```
2. **Exception Handling with Multiple Concurrent Operations**

3. **Partial Failure Handling**

4. **Using supervisorScope for Independent Error Handling**

-----

## What are the lifecycle-aware coroutine scopes in Android and when should you use each?

**Answer:**

Android provides several lifecycle-aware coroutine scopes:

1. **`lifecycleScope` (Activities & Fragments)**  
   Lifecycle tied to component’s lifecycle, cancelled when destroyed.  
   Use for UI-related operations.

2. **`viewModelScope` (ViewModels)**  
   Lifecycle tied to ViewModel, cancelled when ViewModel is cleared.  
   Use for business logic, survives configuration changes.

3. **`GlobalScope` (Rarely Used)**  
   Tied to application lifetime, never cancelled automatically.  
   Use rarely, only for global operations.

4. **Custom Scopes with `SupervisorJob`**  
   Useful for background repetitive tasks and periodic syncs.

**Best Practices:**  
- Use `lifecycleScope` for UI  
- Use `viewModelScope` for business logic  
- Avoid `GlobalScope` unless necessary  
- Create custom scopes for background work

-----

## How do you test asynchronous code in Android?

**Answer:**

Testing asynchronous code requires special handling:

1. **Using `runTest` for suspending functions**  
2. **Testing with `TestDispatcher`**  
3. **Testing `Flow` emissions with turbine/test collectors**  
4. **Mocking asynchronous dependencies**  
5. **Integration testing with database or real async operations**

-----

## What’s the difference between cold and hot flows, and how do they relate to async operations?

**Answer:**

- **Cold Flows:** Start producing values only when collected. Each collector gets its own data stream.  
- **Hot Flows:** Continuously emit values, regardless of collectors. Shared among all collectors.

**Examples:**  
- `Flow {}` → Cold Flow (fresh API call each time).  
- `SharedFlow`, `StateFlow` → Hot flows (real-time updates, UI states).

**Converting Cold to Hot:** Use `shareIn` or `stateIn`.

-----

## How would you implement a retry mechanism for failed asynchronous operations?

**Answer:**

Retry strategies include:

1. **Simple exponential backoff**
2. **Conditional retry (based on specific errors)**
3. **Flow-based retry with state tracking**
4. **Advanced retry (jitter, circuit breaker)**
5. **Repository pattern with retry built in**

Always combine retries with proper logging/monitoring.

-----

## How do you prevent memory leaks in asynchronous Android operations?

**Answer:**

1. Use **lifecycle-aware coroutine scopes** (`lifecycleScope`, `viewModelScope`)  
2. Proper **ViewModel usage**  
3. Use **WeakReferences** for callbacks in legacy code  
4. Manage **Job cancellation** properly  
5. Respect **Activity/Fragment lifecycle** using `viewLifecycleOwner.lifecycleScope`

-----

##  Explain the concept of backpressure in asynchronous operations and how to handle it.

**Answer:**

Backpressure occurs when data is produced faster than it can be consumed.

**Solutions:**  
- `.buffer(capacity)` → store items temporarily.  
- `.conflate()` → keep only latest value.  
- `.collectLatest()` → cancel old work.  
- `.sample(interval)` → down-sample data.  
- **Custom queue management** with `Channel`.

-----

##  How would you implement cancellation in long-running asynchronous operations?

**Answer:**

1. Cooperative cancellation with `isActive` and `yield()`  
2. Cancellation with cleanup (delete partial files)  
3. User-initiated cancellation (track jobs in a map & cancel)  
4. Timeout with `withTimeout`  
5. Structured cancellation: failing one child cancels siblings.

-----

## What are some common threading mistakes in Android and how do you avoid them?

**Answer:**

1. **Doing network ops on main thread** → use `Dispatchers.IO`  
2. **Updating UI from background threads** → always return to `Dispatchers.Main`  
3. **Blocking main thread** → avoid `runBlocking` in UI  
4. **Memory leaks with threads** → use lifecycle-aware scopes  
5. **Race conditions with shared state** → use `Mutex` or `Atomic`  
6. **Unhandled exceptions** → wrap with try-catch

-----

## How do you handle configuration changes with ongoing asynchronous operations?

**Answer:**

1. Use **ViewModels** (recommended) – they survive config changes.  
2. Use **retained fragments** (legacy).  
3. Use **WorkManager** for long tasks.  
4. Use **SavedStateHandle** to restore state after recreation.  
5. Handle via **lifecycle-aware repository patterns**.

**Best practices:**  
- Use `viewModelScope` for UI operations across rotations.  
- Use `WorkManager` for persistent background work.  
- Save/restore state with `SavedStateHandle`.

-----




  

# 📱 Android Activity Launch Modes

Think of your app as a series of **conversations**.  
Each conversation is a **task**, and every screen (**Activity**) is a part of that conversation.  

**Launch modes** are the rules that decide if a new screen joins the current conversation or starts a completely new one.  


## 🚀 Launch Modes

### 1. `standard` (The Default)
- **Behavior:**  
  Every time you start an activity, a **new instance** is created and pushed onto the current task’s back stack.  

- **Use Case:**  
  Most activities in an app use this mode. It’s predictable and works well for linear flows.  

- **Example Stack:**  
Current: A -> B
Launch B (standard) → A -> B -> B


### 2. `singleTop` (The "Are We Already Here?")
- **Behavior:**  
- If the activity is already at the **top** of the back stack → no new instance is created.  
- Instead, the existing instance receives the intent in `onNewIntent()`.  
- If it’s not on top → behaves like `standard`.  

- **Use Case:**  
Search results page. Running another search should update the same page, not create another one.  

- **Example Stack:**
Current: A -> B
Launch B (singleTop) → A -> B
(onNewIntent() is called on existing B)


### 3. `singleTask` (The Task Dominator)
- **Behavior:**  
- If a task with the activity’s `taskAffinity` exists → the system brings that whole task to the foreground, calls `onNewIntent()` on the activity, and destroys everything above it.  
- If no such task exists → a new task is created, and this activity becomes its **root**.  

- **Use Case:**  
The main entry point of your app. Example: deep links from a notification should land the user on a clean, predictable screen.  

- **Example Stack:**
Current: A -> B -> C -> D
Launch B (singleTask) → A -> B
(C and D destroyed)

### 4. `singleInstance` (The Lone Wolf)
- **Behavior:**  
- Similar to `singleTask`, but the activity lives in its **own task**.  
- No other activities can be launched into that task.  
- If this activity starts another, the new one launches in a **different task**.  

- **Use Case:**  
Rare. Example: a **music player’s Now Playing** screen or a **dialer** app.  

- **Example Stack:**

Task 1: A -> B
From B, launch C (singleInstance) →

Task 1: A -> B -> D
Task 2: C


## 🎯 Interview Questions for Senior/Lead Engineers

### Q1: What is the practical difference between `singleTask` and `singleInstance`? When would you absolutely need `singleInstance`?
- **Answer:**  
- Both ensure a single instance exists.  
- `singleTask` allows other activities on top of it in the same task.  
- `singleInstance` is completely isolated — no other activities join its task.  
- Use `singleInstance` when you want a **truly independent experience** (e.g., dialer, music player).  



### Q2: Explain how `taskAffinity` works with `singleTask`. Can an activity from App A be launched into a task of App B?
- **Answer:**  
- `taskAffinity` is a string that defines which task an activity prefers.  
- By default, all activities in an app share the same affinity (the app’s package name).  
- When a `singleTask` activity is launched, the system looks for a matching `taskAffinity`.  
- Yes, activities from App A can join App B’s task if they share the same affinity **and** flags like `FLAG_ACTIVITY_NEW_TASK` are used.  
- ⚠️ This can lead to **confusing navigation** if misused.  



### Q3: Describe a scenario where using `FLAG_ACTIVITY_CLEAR_TOP` on an activity with `singleTop` would produce a different result than just using `singleTask`.
**Scenario:**  
Stack = A -> B -> C -> D

1. **Launch B with singleTask:**  
Result: A -> B
(C and D destroyed)
Existing B reused (onNewIntent() called)

2. **Launch B with FLAG_ACTIVITY_CLEAR_TOP + singleTop:**  
Result: A -> B
(C and D destroyed)

If B is singleTop → existing B reused (onNewIntent() called)

If B is standard → old B destroyed, new B created

- **Key Difference:**  
  - `singleTask` always reuses the existing instance.  
  - `FLAG_ACTIVITY_CLEAR_TOP` depends on the target’s launch mode.  



## ✅ Summary

- `standard` → Always creates a **new instance**.  
- `singleTop` → Reuses instance if already on **top of stack**.  
- `singleTask` → One instance per **task**, clears activities above it.  
- `singleInstance` → One instance in its **own isolated task**.  


----------------------------






1. Dispatchers(.)Main(.)immediate ≠ Dispatchers(.)Main

➜ Dispatchers(.)Main always posts a task to the main thread's message queue. Even if you're already on the main thread, your code will wait for the next event loop cycle.

➜ Dispatchers(.)Main(.)immediate checks first. If you're already on the main thread, it runs the code immediately without re-posting, which can prevent a UI flicker or a one-frame delay.

2. CoroutineExceptionHandler & supervisorScope

➜ This is a key concept in structured concurrency. An exception in a child coroutine within a supervisorScope will not be caught by a CoroutineExceptionHandler attached to that supervisorScope. The exception propagates up to the parent of the supervisorScope. The standard and correct way to handle these exceptions is to install the handler in the context of the parent scope, often using CoroutineScope(SupervisorJob() + CoroutineExceptionHandler).

3. withContext(NonCancellable) only protects its own code.

➜ This protects the code directly inside its block from being cancelled. But if you launch a new coroutine from inside that block, the new coroutine can still be cancelled. For cleanup code that must finish, run it directly inside NonCancellable.

4. You don't need to manually cancel viewModelScope.

➜ When a ViewModel is destroyed, viewModelScope is cancelled automatically. The real danger is collecting a flow from the UI and having it run in the background. Use stateIn(viewModelScope) or repeatOnLifecycle in your UI to collect flows safely.

5. yield() is for checking, not for pausing.

➜ yield() doesn't pause your code. It just quickly checks if other coroutines need to run or if the job was cancelled. For any heavy processing work, you should always move it to a background thread using withContext(Dispatchers(.)Default).


----

## Jetpack Compose

- How to launch a coroutine from a composable function? - LaunchedEffect
- How to launch a coroutine from a non-composable function, but tied to composition? - rememberCoroutineScope()
- What is recomposition? Recomposition
- What is remember in compose?
- A composable function to remember the value produced by a calculation only at the time of composition. It will not calculate again in recomposition.
- Recomposition will always return the value produced by composition.
- Whole Compose is based on the concept of Positional Memoization
- At the time of recomposition, remember internally calls a function called rememberedValue() whose work is to look into the slotTable and compare if the - previous value and the new value have any difference, if not return, else update the value
- Why and when to use remember {}?
- Difference between LazyColumn and RecyclerView?
- What is AndroidView in compose?
- What is the lifecycle of composeables? Lifecycle
- How to avoid recomposition of any composable, if the state is not changed? Smart Recomposition
- What are stable types that can skip recomposition?
- What is State?
- What is MutableState and how does recomposition happen?
vHow to retain State across recomposition and configuration changes?
- Difference between Stateless and Stateful composeables?
- What are your thoughts on flat hierarchy, constraint Layout in compose vs. the older view hierarchy in xml
- Difference b/w remember and LaunchedEffect
- Does re-composition of ComposeItem1 bring any effect on ComposeItem2? If yes, then how?
ComposeParent() { ComposeItem1 {} ComposeItem2() {...} } 
- What is CompositionLocal?
- Custom views in compose
- Canvas in Compose
- What are the benefits of Jetpack Compose?
- How does Jetpack Compose integrate with existing Android frameworks and libraries?
- What are the best practices for performance optimization in Jetpack Compose?
- How is navigation handled in Jetpack Compose?
- What is Strong Skipping Mode? Answer

----
# 🟢 Beginner Level
# 🧠 Kotlin Basics
- What is Kotlin? How is it different from Java?
- What are the main features of Kotlin?
- How does Kotlin handle null safety?
- What is a val vs var?
- What are nullable and non-nullable types?
- What is the Elvis operator (?:)?
- Explain the safe call operator (?.).
- What is the use of !! in Kotlin?
- What are extension functions?
- How to write a simple class in Kotlin?
- What is a data class?
- How to define properties in Kotlin?
- What is string interpolation in Kotlin?
- What are default and named parameters?
- How does Kotlin support functional programming?

  
# 🔁 Control Flow
- Explain if, when, and for loops in Kotlin.
- How is when better than switch in Java?
- How does while and do-while loop work in Kotlin?
  
# 🧩 Functions
- What is a lambda function in Kotlin?
- What is a higher-order function?
- What are default and named arguments?
- What is an inline function?
- What is a local function?
- How to define a vararg parameter?
- How to use infix notation in Kotlin?
  

# 📚 Collections

- Difference between List, Set, and Map.
- Mutable vs Immutable collections in Kotlin.
- How to filter a list using Kotlin?
- What are map, filter, and reduce?
- How to sort a list in Kotlin?
- What is the difference between flatMap and map?
- What is the use of associateBy and groupBy?
  
# 📱 Android Basics (in Kotlin)
- How to set up a basic Android project using Kotlin?
- What is an Activity in Kotlin?
- What is the role of XML in Android UI?
- How to start a new Activity using Intent?
- What is findViewById and how to use ViewBinding in Kotlin?
- What is a RecyclerView?
- What is the AndroidManifest.xml file for?
- What are Fragments and their lifecycle?
- How to pass data between activities using Kotlin?
- How to handle runtime permissions in Kotlin?

  
# 🟡 Intermediate Level
# 🧱 Object-Oriented Concepts in Kotlin
- Difference between class and data class?
- What are primary and secondary constructors?
- Explain init block in Kotlin.
- What is the difference between open, final, and abstract?
- What is a sealed class?
- What is an enum class?
- What is an object declaration?
- What is a companion object?
- What is an interface in Kotlin?
- Can an interface have default implementations?

  
 # 🔍 Advanced Kotlin Functions & Scoping
- Difference between let, apply, also, run, and with.
- What is scope function chaining?
- What is the use of the this keyword?
- What is it in Kotlin?
- What are labeled returns?
  
# 🔤 Generics & Type System
- How do you declare a generic class or function?
- What is in and out in generics?
- Explain Kotlin’s type inference.
- What is a reified type in Kotlin?
- What is typealias in Kotlin?
  
# 🌐 Coroutines (Basics)
-  What are coroutines in Kotlin?
- How to launch a coroutine?
- What is suspend function?
- What is GlobalScope, viewModelScope, and lifecycleScope?
- Difference between launch and async.
- What is withContext?
- What are Dispatchers in Kotlin?
- How to cancel coroutines?
- How to handle exceptions in coroutines?
  
# 🧩 Android + Kotlin (MVVM, Lifecycle)
- What is the MVVM architecture in Android?
- What is LiveData and how is it used?
- What is a ViewModel and why is it important?
- How to observe LiveData in Kotlin?
- What are lifecycle-aware components?
- How to use Hilt for dependency injection in Android?
- How does Room Database work in Kotlin?
- What is a DAO in Room?
- What is the suspend function in DAO?

# 🔵 Advanced Level
# 🔁 Coroutines & Concurrency
- What is structured concurrency?
- How does CoroutineExceptionHandler work?
- What is SupervisorJob?
- What is cold vs hot flow?
- What is Kotlin Flow?
- Difference between Flow, StateFlow, and SharedFlow.
- What is collectLatest?
- How to handle backpressure in flows?
- What is debounce and throttle in flow?
- What are operators in Flow?
  
# 🛠️ DSL (Domain Specific Language)
- What is a Kotlin DSL?
- How is Kotlin DSL used in Gradle?
- Explain the builder pattern using Kotlin DSL.
- How to create your own DSL in Kotlin?

  
# 🌍 Kotlin Multiplatform
- What is Kotlin Multiplatform (KMP)?
- What is expect and actual in KMP?
- What platforms does KMP support?
- What are some limitations of Kotlin Multiplatform?
- How is code shared in Kotlin Multiplatform?
  
# 🏷️ Annotations & Reflection
- How do you create a custom annotation?
- How does Kotlin support reflection?
- What is @JvmStatic, @JvmOverloads, and @JvmName?
- What is kclass in Kotlin?
- How to use Kotlin reflection?

  
# ⚙️ Performance & Memory
- How does Kotlin manage memory?
- What are inline classes?
- What is value class in Kotlin 1.5+?
- How does Kotlin handle boxing/unboxing?
- How to optimize coroutine performance?
  
# 🎨 Jetpack Compose (Basics to Intermediate)
- What is Jetpack Compose?
- How is Compose different from XML?
- How to create a Composable function?
- What is @Composable annotation?
- What is remember in Compose?
- What is mutableStateOf?
- How do recompositions work?
- How to create a list using LazyColumn?
- What is Modifier in Compose?
- How to manage click events in Compose?
- How to implement navigation in Compose?
- What is rememberSaveable vs remember?
- How to use LaunchedEffect?
- How does SideEffect, DisposableEffect, and DerivedStateOf work?
- How to integrate ViewModel with Compose?
- How to observe LiveData or StateFlow in Compose?
- How to manage themes and styling in Compose?
- How to preview composables?
- How to use Scaffold, TopAppBar, BottomNavigation?
- How to create custom composables?
- How to use ConstraintLayout in Compose?
- What is Compose’s Slot API?

  
# 🔁 Common Kotlin + Android Integration Questions
- How is Kotlin used in Android development?
- Difference between Activity and Fragment in Kotlin?
- How do you handle lifecycle in Kotlin?
- How to implement ViewModel with Kotlin?
- What are LiveData and StateFlow?
- What is Jetpack Compose in Kotlin?
- How does Kotlin work with Retrofit and Room?
- How do you write Unit tests in Kotlin?
- How to use Kotlin with Dagger/Hilt?
- What are common design patterns used in Kotlin?
- How to implement clean architecture in Android?
- What are best practices for Android development with Kotlin?
- How to test Compose UI?
- How to use Firebase with Kotlin?
- How to secure API keys in Kotlin Android app?
- How to use WorkManager in Kotlin?
- How to schedule background tasks with coroutines?
- How to optimize app startup time?
- How to implement dark theme using Jetpack Compose?

  
# 🧠 Expert-Level & Real-World Android Questions (Added)
# Jetpack Compose: Advanced
- How does recomposition work internally in Jetpack Compose?
- What are key strategies to prevent unnecessary recomposition?
- How to manage complex form UI state in Compose?
- How to optimize LazyColumn performance?
- What are keys in LazyColumn, and why are they important?
- How do you apply animations in Jetpack Compose?
- What is AnimatedVisibility in Compose?
- How to use AnimatedContent for transitions?
- How to detect gestures in Compose?
- What are pointer input modifiers?
- Clean Architecture + Architecture Patterns
- What is Clean Architecture in Android?
- What are the layers in Clean Architecture?
- How does domain layer communicate with data layer?
- Difference between Repository pattern and UseCase pattern?
- What is separation of concerns in Android architecture?
- How to implement interface-driven development in Android?
- What is SOLID principle? How does it apply in Android?
- How do you structure packages in a large Android project?
- What is the benefit of using a shared result wrapper (sealed class)?
- How to handle single source of truth in Clean Architecture?

# Dependency Injection (DI)
- What is dependency injection?
- What is the difference between constructor injection and field injection?
- What is Hilt and how is it different from Dagger?
- How to inject a ViewModel using Hilt?
- How to scope dependencies in Hilt (ActivityScoped, Singleton)?
- How to inject interfaces using Hilt?
- How to use Qualifiers in Hilt?
- What is EntryPoint in Hilt?
  
# Testing
- How to write unit tests for ViewModel?
- What is mocking and how to use it in Kotlin?
- How to test LiveData?
- How to use CoroutineTestRule?
- What is Robolectric and how is it used?
- How to test Room Database?
- How to test Compose UI?
- How to test Flow or StateFlow?
- Performance, Security & Misc
- How do you improve app startup performance?
- How to analyze memory leaks in Android?
- What are common performance bottlenecks in Compose?
- How to secure sensitive user data in Android?
- How to detect ANRs (Application Not Responding)?
- What is StrictMode in Android?
- Bonus: Kotlin Gotchas & Patterns
- What are some Kotlin pitfalls to watch out for?
- How to use the Result class in Kotlin?
- What is the difference between inline, noinline, and crossinline?
- Explain the use of sealed interface.
- How to use the Delegation pattern in Kotlin?
- What are coroutine channels?
- What is the difference between Job and Deferred?
- How to handle multiple flows in parallel?

