# 📱 Android Activity & Fragment Lifecycle 

This guide explains the **Activity vs Fragment lifecycle** in Android, along with **interview questions & answers**.  
Great for quick study notes or interview prep.

---

## 🔄 Activity Lifecycle

1. **onCreate()** → Called when activity is created. Initialize UI, variables.  
2. **onStart()** → Activity becomes visible.  
3. **onResume()** → Activity is visible and interactive.  
4. **onPause()** → Another activity partially covers this one. Save lightweight data.  
5. **onStop()** → Activity is no longer visible. Release heavy resources.  
6. **onRestart()** → Called before onStart() when coming back after stop.  
7. **onDestroy()** → Activity is destroyed (finish() or system cleanup).  

---

## 📦 Fragment Lifecycle

1. **onAttach()** → Fragment attached to Activity.  
2. **onCreate()** → Initialize fragment state.  
3. **onCreateView()** → Inflate layout XML.  
4. **onViewCreated()** → View created, bind UI elements.  
5. **onStart()** → Fragment visible.  
6. **onResume()** → Fragment interactive.  
7. **onPause()** → Fragment partially hidden.  
8. **onStop()** → Fragment not visible.  
9. **onDestroyView()** → Cleanup view hierarchy.  
10. **onDestroy()** → Cleanup fragment resources.  
11. **onDetach()** → Fragment detached from Activity.  

---

## 🎯 Key Notes

- Activity lifecycle is simpler (screen-level).  
- Fragment lifecycle is more complex (depends on Activity).  
- Clean **view bindings in onDestroyView()** to avoid memory leaks.  
- Use **ViewModel + Lifecycle components** for state handling.  

---

## ❓ Common Interview Questions & Answers

### Q1: Difference between `onCreate()`, `onStart()`, and `onResume()`?  
✅ **Answer:**  
- **onCreate()**: Initialize once (UI, data binding).  
- **onStart()**: Activity becomes visible.  
- **onResume()**: Activity in foreground, interactive.  

---

### Q2: What happens on screen rotation?  
✅ **Answer:**  
- Activity is destroyed & recreated by default.  
- Lifecycle: `onPause() → onStop() → onDestroy() → onCreate() → onStart() → onResume()`.  
- Handle with **ViewModel** or **onSaveInstanceState()**.  

---

### Q3: Why should you clear bindings in `onDestroyView()` for Fragments?  
✅ **Answer:**  
- Because fragment view is destroyed before fragment itself.  
- Keeping reference = **memory leaks**.  

---

### Q4: Can `onPause()` be followed directly by `onDestroy()`?  
✅ **Answer:** Yes, if system kills the activity for memory.  

---

### Q5: How do Lifecycle components help?  
✅ **Answer:**  
- **ViewModel** survives config changes.  
- **LifecycleOwner & Observer** auto-manage lifecycle events without boilerplate.  

---
### Q6: Difference between `onSaveInstanceState()` and `ViewModel`?  
✅ **Answer:**  
- `onSaveInstanceState()` → Small UI state, survives process death.  
- **ViewModel** → Keeps data during rotation but not process death.  

---

### Q7: What happens if an Activity is killed in background?  
✅ **Answer:**  
- System may recreate it later with saved instance state passed to `onCreate()` or `onRestoreInstanceState()`.  

---

### Q8: Difference between `onCreate()` (Activity) vs `onCreateView()` (Fragment)?  
✅ **Answer:**  
- **onCreate()**: Set up non-UI logic.  
- **onCreateView()**: Inflate Fragment’s layout.  

---

### Q9: Difference between `onDestroyView()` and `onDestroy()` in Fragment?  
✅ **Answer:**  
- **onDestroyView()** → Clean up UI (avoid leaks).  
- **onDestroy()** → Clean up non-UI resources.  

---

### Q10: Why is `onAttach()` important in Fragment?  
✅ **Answer:**  
- First callback with a valid context. Useful for dependency injection.  

---

### Q11: `commit()` vs `commitNow()` vs `commitAllowingStateLoss()`?  
✅ **Answer:**  
- `commit()` → Async transaction.  
- `commitNow()` → Immediate transaction.  
- `commitAllowingStateLoss()` → Runs even after `onSaveInstanceState()`. Use carefully.  

---

### Q12: What is `setRetainInstance(true)`?  
✅ **Answer:**  
- Keeps Fragment instance across config changes.  
- Deprecated → Use **ViewModel** instead.  

---

### Q13: Difference between `add()` vs `replace()` in Fragment transactions?  
✅ **Answer:**  
- `add()` → Adds fragment without removing existing ones.  
- `replace()` → Removes current and adds new fragment.  

---

### Q14: Can a Fragment exist without Activity?  
✅ **Answer:**  
- No, it always needs a host Activity (or another Fragment when nested).  

---

### Q15: `DialogFragment.show()` vs `add()`?  
✅ **Answer:**  
- `show()` → Handles dialog transaction automatically.  
- `add()` → Adds as normal fragment (you must manage layout).  

---

### Q16: Best place to start/stop camera preview in Fragment?  
✅ **Answer:**  
- Start in `onResume()`.  
- Stop in `onPause()`.  

---

### Q17: Explain Fragment back stack.  
✅ **Answer:**  
- `.addToBackStack()` saves transaction.  
- Back button pops fragment stack instead of closing Activity.  

---

### Q18: What happens if you call `finish()` inside an Activity?  
✅ **Answer:**  
- Activity goes: `onPause() → onStop() → onDestroy()`.  
- If last activity, process may be killed.  

---

### Q19: Difference between `onStart()` vs `onResume()`?  
✅ **Answer:**  
- **onStart()** → Visible but not interactive.  
- **onResume()** → Foreground and interactive.  

---

### Q20: Why avoid heavy tasks in `onCreate()`?  
✅ **Answer:**  
- Slows down startup → risk of **ANR (App Not Responding)**.  
- Use background threads or ViewModel for heavy work.  
