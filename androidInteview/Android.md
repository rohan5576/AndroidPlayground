

# 📱 Android Activity Launch Modes

Think of your app as a series of **conversations**.  
Each conversation is a **task**, and every screen (**Activity**) is a part of that conversation.  

**Launch modes** are the rules that decide if a new screen joins the current conversation or starts a completely new one.  

---

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

---

### Q2: Explain how `taskAffinity` works with `singleTask`. Can an activity from App A be launched into a task of App B?
- **Answer:**  
- `taskAffinity` is a string that defines which task an activity prefers.  
- By default, all activities in an app share the same affinity (the app’s package name).  
- When a `singleTask` activity is launched, the system looks for a matching `taskAffinity`.  
- Yes, activities from App A can join App B’s task if they share the same affinity **and** flags like `FLAG_ACTIVITY_NEW_TASK` are used.  
- ⚠️ This can lead to **confusing navigation** if misused.  

---

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

---

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
