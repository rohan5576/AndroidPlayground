# 📘 Android Intents & Navigation

This guide explains **Intents in Android** with detailed examples, practice tasks, and interview preparation.

---

## 🔹 What are Intents?

An **Intent** in Android is a messaging object used to request an action from another app component.  

They are commonly used to:
- Start a new **Activity**
- Launch a **Service**
- Deliver a **Broadcast**

---

## 🔹 Types of Intents

### 1. Explicit Intent
Directly specifies the target component (Activity/Service).  
Mostly used **within your app**.

```kotlin
// Open DetailsActivity from MainActivity
val intent = Intent(this, DetailsActivity::class.java)
intent.putExtra("username", "Alice") // passing data
startActivity(intent)

// In DetailsActivity
val username = intent.getStringExtra("username")
```

### 2. Implicit Intent

Does not specify the target component.
Declares a general action, and Android decides which app can handle it.

```kotlin
// Open webpage
val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com"))
startActivity(browserIntent)

// Share text
val shareIntent = Intent(Intent.ACTION_SEND).apply {
    type = "text/plain"
    putExtra(Intent.EXTRA_TEXT, "Check out this app!")
}
startActivity(Intent.createChooser(shareIntent, "Share via"))
```


### Passing Data Between Activities

Use extras (putExtra / getExtra).
```kotlin
// Sender Activity
val intent = Intent(this, ProfileActivity::class.java)
intent.putExtra("age", 25)
startActivity(intent)

// Receiver Activity
val age = intent.getIntExtra("age", 0)
```

You can pass:

Primitives (Int, String, Boolean, etc.)
Bundles
Parcelable / Serializable objects


### Android Navigation Component

The Navigation Component (Jetpack) helps manage app navigation.

Key features:
Navigation Graph → Defines all app screens (Fragments/Activities)
NavController → Handles navigation
Safe Args → Type-safe arguments between screens
Back Stack → Managed automatically

Example:

// Navigate between fragments
findNavController().navigate(R.id.action_homeFragment_to_detailFragment)

// With Safe Args
val action = HomeFragmentDirections.actionHomeToDetail(userId = 42)
findNavController().navigate(action)



