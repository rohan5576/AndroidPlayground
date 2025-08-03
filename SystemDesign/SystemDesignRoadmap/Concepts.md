## Articles

https://www.linkedin.com/posts/arunm-engineer_system-design-concepts-ugcPost-7352905061494149123-I3pJ?utm_source=share&utm_medium=member_desktop&rcm=ACoAABhA3WUBB7fDQLoHl0PnXSTTo7z5SuGIg-c



# 📦 What is Sharding in System Design?

**Sharding** is the process of breaking a large dataset or database into smaller, more manageable pieces called **shards**. Each shard contains a subset of the data and is stored on a separate database or server. This helps in improving **performance**, **scalability**, and **fault tolerance** in large-scale systems.

---

## ✅ Benefits of Sharding

- **Scalability**: Easily handle large amounts of data by adding more shards (horizontal scaling).
- **Performance**: Each shard contains less data, so queries run faster.
- **Isolation**: Failures in one shard don’t affect others.
- **Efficient Resource Usage**: Load is distributed across multiple machines or databases.

---

## 📱 Example: Sharding in an Android App

Imagine you're building an Android app that tracks user social media usage, stores quiz scores, and motivational quote interactions. As the number of users grows, storing all user data in a single database can slow things down.

### 🔹 How Sharding Can Help:
- **User Data Sharding**:
  - Split users across shards based on user ID.
  - Example:
    - Users with ID 1–1,000,000 → Database A
    - Users with ID 1,000,001–2,000,000 → Database B

- **Activity Logs Sharding**:
  - Store daily activity logs like quiz attempts, quote views, and time-unlocks across multiple databases based on date or region.

### 🔹 Benefits for the App:
- Faster analytics on user habits.
- Scalable backend for millions of users.
- Independent scaling for active vs. less active users.

---

## 📌 Summary

| Term | Meaning |
|------|---------|
| Shard | A smaller piece of a big database |
| Sharding | The act of splitting data into shards |
| Goal | To make systems faster and scalable |


# 🔧 Types of Sharding in System Design

Sharding can be done in multiple ways depending on how you want to divide the data. Below are the most common types of sharding:

---

## 1. 📊 Range-Based Sharding

### ➤ Description:
Data is split based on a specific **range of values** (like IDs, dates, or numbers).

### ➤ Example:
- User IDs 1–1,000,000 → Shard A
- User IDs 1,000,001–2,000,000 → Shard B

### ✅ Pros:
- Simple and easy to implement.
- Predictable data distribution.

### ❌ Cons:
- Can cause **data skew** (one shard may get overloaded if user distribution is uneven).

---

## 2. 🔢 Hash-Based Sharding

### ➤ Description:
A **hash function** is applied to a key (like user ID) to determine which shard the data should go to.

### ➤ Example:
- `shard = hash(user_id) % 4`
  - Output 0 → Shard A
  - Output 1 → Shard B
  - And so on...

### ✅ Pros:
- Good load balancing.
- Random distribution avoids data hotspots.

### ❌ Cons:
- Difficult to re-shard (adding a new shard changes the hash output).
- Cross-shard operations are hard.

---

## 3. 🧱 Vertical Sharding

### ➤ Description:
Data is split by **table or functionality** (not rows).

### ➤ Example:
- `UserProfile` table → Shard A
- `UserPosts` table → Shard B
- `UserQuizData` table → Shard C

### ✅ Pros:
- Keeps related data together.
- Optimizes for features or teams.

### ❌ Cons:
- Doesn't reduce size of any individual table.
- Risk of one vertical shard becoming too big.

---

## 4. 🌐 Geographic/Location-Based Sharding

### ➤ Description:
Data is divided based on **user region or geography**.

### ➤ Example:
- Users from Asia → Shard A
- Users from Europe → Shard B
- Users from US → Shard C

### ✅ Pros:
- Lower latency (data closer to users).
- Easier to comply with regional data laws (GDPR, etc.).

### ❌ Cons:
- Uneven distribution if one region has too many users.

---

## 📌 Summary Table

| Type | Based On | Best For | Downside |
|------|----------|----------|----------|
| Range-Based | ID or numeric range | Simplicity | Hotspot risk |
| Hash-Based | Hash of key | Load balancing | Hard to scale |
| Vertical | Tables/features | Modular apps | Table size not reduced |
| Geo-Based | User region | Global apps | Unbalanced usage |



# 🛑 What Are Bottlenecks in System Design?

A **bottleneck** is a component in a system that **slows down or limits the overall performance** of the system.

Just like water slows down at the neck of a bottle, a bottleneck in a system means **one part is overloaded**, while other parts are waiting or idle.

---

## 🚦 Common Types of Bottlenecks

### 1. **CPU Bottleneck**
- When the processor is working too hard and can't keep up.
- Example: Too many concurrent requests for computation-heavy tasks.

### 2. **Memory Bottleneck**
- Not enough RAM to handle in-memory operations.
- Leads to swapping or crashing.

### 3. **Disk I/O Bottleneck**
- Reading/writing from storage is too slow.
- Common in file-heavy or log-heavy systems.

### 4. **Network Bottleneck**
- Limited bandwidth or slow network connections.
- Example: Too many API calls or slow external service responses.

### 5. **Database Bottleneck**
- Database queries are slow or locked.
- High read/write load, poor indexing, or no caching.

### 6. **Code-Level Bottleneck**
- Inefficient loops, recursion, or blocking code.
- Single-threaded bottlenecks in multi-threaded environments.

---

## 📉 Symptoms of Bottlenecks

- Slow response time
- High CPU or memory usage
- Timeouts or failed requests
- Uneven load distribution
- Poor scalability

---

## ✅ How to Avoid or Fix Bottlenecks

| Strategy | Description |
|----------|-------------|
| **Load Balancing** | Distribute traffic evenly across servers |
| **Caching** | Store frequently accessed data in memory |
| **Database Optimization** | Use indexing, sharding, query tuning |
| **Asynchronous Processing** | Use queues for background tasks |
| **Scaling** | Add more servers (horizontal scaling) |
| **Monitoring** | Use tools (Prometheus, Grafana, etc.) to detect slow components |

---

## 📱 Example: Bottlenecks in Android App Backend

Let’s say your Android app sends user quiz data to the server:

- **Database bottleneck**: If every request writes directly to DB, it can get slow.
- **Network bottleneck**: If too many users are uploading at once, server bandwidth may be maxed.
- **Fix**: Queue writes using a message broker (like Kafka), and batch save to DB.

