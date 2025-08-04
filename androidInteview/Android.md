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