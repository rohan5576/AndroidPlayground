# Android Interview Preparation

## Common Questions
1. What is the difference between Activity and Fragment?
2. Explain the Android Activity Lifecycle.
3. How do you handle configuration changes in Android?
4. What is the purpose of the AndroidManifest.xml file?
5. Describe the Parcelable interface and how it is used.

## Technical Deep Dives
- **Android Architecture Components**: Overview of ViewModel, LiveData, and Room.
- **Dependency Injection**: Explanation of Dagger and Hilt usage in Android apps.
- **Networking**: Discussion on Retrofit vs. Volley.
- **Concurrency**: Handling background tasks using AsyncTask, Kotlin Coroutines, etc.

## Problem Solutions
### Problem 1: Reverse a String
```kotlin
fun reverseString(input: String): String {
    return input.reversed()
}
```

### Problem 2: Find the Maximum Element in a List
```kotlin
fun findMax(nums: List<Int>): Int? {
    return nums.maxOrNull()
}
```

### Problem 3: Check for Palindrome
```kotlin
fun isPalindrome(input: String): Boolean {
    return input == input.reversed()
}
```
