# 📱 Day 3: Services & Broadcast Receivers  

## 🔹 Overview  
On **Day 3**, we explore two key Android components:  
1. **Services** → For background tasks (e.g., music, downloads).  
2. **Broadcast Receivers** → For system-wide or app-specific event handling.  

---

## 🚀 Services  

### What is a Service?  
- A **Service** is a component that runs in the background without a user interface.  
- Useful for long-running tasks such as:  
  - Playing music 🎵  
  - Fetching data from the internet 🌐  
  - Running periodic tasks ⏱️  

### Types of Services  
1. **Foreground Service**  
   - Runs with high priority.  
   - Shows a notification (e.g., music player).  

2. **Background Service**  
   - Runs silently in the background.  
   - Android imposes restrictions for battery optimization.  

3. **Bound Service**  
   - Allows components (Activities/Fragments) to bind and interact.  

### Example: Starting a Service  
```java
// Starting a Service
Intent intent = new Intent(this, MyService.class);
startService(intent);

// Stopping a Service
stopService(intent);
```

```
Example: Service Class
public class MyService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Task to run in background
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // For bound service, return IBinder
    }
}
```

-----


# 📡 Broadcast Receivers  

## 🔹 What is a Broadcast Receiver?  
A **Broadcast Receiver** listens for **system-wide** or **app-specific** broadcasted **Intents**.  

### ✅ Common Use Cases  
- Battery low warning 🔋  
- Network changes 📶  
- SMS received 📩  

---

## 🔧 Registering Broadcast Receivers  

### 1️⃣ Static Registration (Manifest-based)  
```xml
<receiver android:name=".MyReceiver">
    <intent-filter>
        <action android:name="android.net.conn.CONNECTIVITY_CHANGE"/>
    </intent-filter>
</receiver>
```

# 💡 Android Interview Q&A: Services & Broadcast Receivers  

## 🚀 Services  

### 🔹 Basic Questions  

**Q1. What is a Service in Android?**  
A Service is a component that runs in the background without a UI. It is used for long-running operations like playing music, downloading files, or syncing data.  

**Q2. How is a Service different from an Activity?**  
- **Activity** → Has a UI, interacts with the user.  
- **Service** → Runs in the background without UI.  

**Q3. What are the different types of Services in Android?**  
1. **Foreground Service** → Runs with a notification (e.g., music player).  
2. **Background Service** → Runs silently in the background (restricted in newer Android versions).  
3. **Bound Service** → Allows components to bind and interact with it.  

**Q4. What is the difference between a Foreground Service and a Background Service?**  
- **Foreground Service** → Higher priority, always shows a notification.  
- **Background Service** → Lower priority, may be killed by the system to save resources.  

**Q5. What is a Bound Service and when would you use it?**  
A Bound Service allows other components (e.g., Activities) to bind and directly interact with it using IPC (Inter-Process Communication). Example: A music app providing playback control to multiple activities.  

---

### 🔹 Intermediate Questions  

**Q6. How do you start and stop a Service in Android?**  

```java
// Starting a Service
Intent intent = new Intent(this, MyService.class);
startService(intent);

// Stopping a Service
stopService(intent);
```


**Q7. What is the difference between startService() and bindService()?** 

startService() → Runs until explicitly stopped.

bindService() → Runs as long as a component is bound.

**Q8. How do you run a Service on a separate thread?** 
By using a Worker Thread or extending IntentService (deprecated, replaced by JobIntentService or WorkManager).

**Q9. What is the role of onStartCommand() method in a Service?** 
It defines the behavior of the service when restarted after being killed.

**Q10. What are the possible return values of onStartCommand()?** 

START_STICKY → Restart service after it’s killed, but intent is null.

START_NOT_STICKY → Do not restart automatically.

START_REDELIVER_INTENT → Restart service and redeliver the last intent.

🔹 Advanced Questions
**Q11. How does Android handle Services under memory pressure?** 
Low-priority background services may be killed to free resources. Foreground services are less likely to be killed.

**Q12. What restrictions did Android 8.0 (Oreo) introduce on Services?** 
Background services cannot run indefinitely. Apps must use JobScheduler or WorkManager for background tasks.

**Q13. How do you create a Foreground Service with a Notification?** 
By calling startForeground() with a Notification object.

**Q14. How do Services interact with other application components?** 
Through Intents, Binder, or Messenger for IPC.

**Q15. What are some best practices to avoid memory leaks with Services?** 

Stop services when not needed.

Use foreground services for long tasks.

Prefer WorkManager for background jobs.

📡 Broadcast Receivers
🔹 Basic Questions
**Q1. What is a Broadcast Receiver in Android?** 
A Broadcast Receiver is a component that listens for system-wide or app-specific broadcasted Intents.

**Q2. What are some common use cases of Broadcast Receivers?** 

Battery low warning 🔋

Network changes 📶

SMS received 📩

**Q3. How is a Broadcast Receiver different from a Service?** 

Broadcast Receiver → Responds to system/app events quickly, short-lived.

Service → Runs long tasks in the background.

**Q4. How can you register a Broadcast Receiver?** 

Static Registration (in AndroidManifest.xml).

Dynamic Registration (in code using registerReceiver()).

**Q5. What is the lifecycle of a Broadcast Receiver?** 
It exists only while handling the onReceive() method, then destroyed.

🔹 Intermediate Questions
**Q6. What is the difference between ordered and normal broadcasts?** 

Ordered broadcast → Delivered one at a time, receivers can modify results.

Normal broadcast → Delivered to all receivers at the same time.

**Q7. What is a sticky broadcast, and why is it deprecated?** 
Sticky broadcasts remain after being sent, so apps can receive the latest value. Deprecated due to security and memory concerns.

**Q8. How do you unregister a dynamically registered Broadcast Receiver?** 
By calling unregisterReceiver(receiver) in onPause() or onStop().

**Q9. Can a Broadcast Receiver start an Activity or a Service?** 
Yes, but it should be quick. For long tasks, it should start a Service or WorkManager.

**Q10. What permissions are required for certain system broadcasts?** 
Example: RECEIVE_SMS permission is required for SMS-related broadcasts.

🔹 Advanced Questions
**Q11. How does Android handle Broadcast Receivers in Android 8.0+?** 
Implicit broadcasts are limited for background apps. Developers should use JobScheduler or WorkManager.

**Q12. What are implicit and explicit broadcasts?** 

Explicit broadcast → Sent to a specific receiver.

Implicit broadcast → Sent system-wide to all receivers that match.

**Q13. How can you secure a Broadcast Receiver so that only your app can send it Intents?** 
By using permissions or setting the exported="false" attribute.

**Q14. How do you handle long-running tasks inside a Broadcast Receiver?** 
Offload work to a Service, WorkManager, or JobIntentService.

**Q15. What are best practices for using Broadcast Receivers?** 

Use dynamic registration when possible.

Keep onReceive() lightweight.

Prefer WorkManager for background tasks.

