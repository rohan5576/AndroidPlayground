✅ Week 1: Foundations of System Design
Goal: Understand how modern systems work.

Topics:
What is system design?
Types of system design:
High-level (HLD) – system architecture, communication
Low-level (LLD) – class design, component interactions
Client-server architecture
APIs and protocols (REST, gRPC, WebSockets)
Client-server model
RESTful APIs, HTTP basics
Latency, throughput, scalability, availability
API rate limiting & retries

What is system design?
 What is System Design (Android Context)?
System Design refers to the process of planning and architecting the structure, components, data flow, and
interactions involved in building scalable, efficient, and maintainable systems —
with a strong focus on how the mobile app fits into the bigger picture.

It's not just how you design your app code (that’s app architecture), but how your mobile client communicates,
stores, processes, and syncs data with external systems (servers, cloud, etc.) at scale.

 | Area                    | Description                                                                                  |
 | ----------------------- | -------------------------------------------------------------------------------------------- |
 | **Client Architecture** | How your app is structured (MVVM, Clean Architecture, layers, components).                   |
 | **Networking Design**   | How your app talks to backend systems – API structure, retries, error handling.              |
 | **Data Flow & Sync**    | Managing local storage (Room, SQLite), syncing with the cloud (WorkManager, Firestore).      |
 | **Performance**         | Handling large-scale data, lazy loading, caching, pagination, memory and battery efficiency. |
 | **Security**            | Secure storage, encrypted communication, token auth, biometric login.                        |
 | **Scalability**         | Making sure your app and backend can support millions of users/devices.                      |
 | **Reliability**         | Designing for failures – offline support, retries, fallbacks.                                |
 | **Observability**       | Crash reporting, performance monitoring, logging.                                            |

Example System Design Challenges
Instagram Feed: Scalable image + data loading with pagination
WhatsApp: Offline chat with sync and delivery status
Uber: Real-time GPS tracking, efficient battery usage
YouTube: Video upload, streaming, content caching
Banking App: Secure login, token rotation, audit trails

 What is HLD (High-Level Design)?
HLD (High-Level Design) is the architectural blueprint of a system. It gives a bird's-eye view of all the components, how they interact, and how the system works as a whole—without diving into code-level details.
HLD is how your app fits into the big system: from screen tap to server response, to database, and back — including performance, scalability, and reliability.

| Element             | Description                                              |
| ------------------- | -------------------------------------------------------- |
| ✅ Components        | Mobile app, backend APIs, database, storage, cache, etc. |
| ✅ Data Flow         | How data moves (e.g., login request → backend → DB)      |
| ✅ Communication     | REST, gRPC, WebSockets, etc.                             |
| ✅ Scaling Strategy  | Caching, load balancing, replicas                        |
| ✅ Failure Handling  | Retries, fallbacks, monitoring                           |
| ✅ Security Measures | Auth, rate limiting, data encryption                     |

Android-Specific Angle
In Android, HLD would include:

Where in your app does the network call happen?
What library handles API (e.g., Retrofit)?
What layers are involved (Repository → ViewModel)?
What backend does it talk to?
What happens if the network fails?
Where is data cached locally?

