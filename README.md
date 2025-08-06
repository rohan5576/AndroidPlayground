# Android Playground 🚀

A comprehensive Android development playground featuring multiple modules for learning, practicing, and mastering Android development concepts, Data Structures & Algorithms, and System Design.

## 📁 Project Structure

This project is organized into multiple modules and learning resources:

### 🏗️ Core Modules

#### 1. **app** - Main Android Application
- **Purpose**: Primary Android application module with Jetpack Compose
- **Package**: `com.example.androidplayground`
- **Key Features**:
  - Modern Android development with Jetpack Compose
  - Material 3 design system
  - Edge-to-edge UI support
  - Compose-based UI components
- **Technology Stack**:
  - Kotlin
  - Jetpack Compose
  - Material 3
  - AndroidX libraries
- **Build Configuration**:
  - Compile SDK: 35
  - Min SDK: 24
  - Target SDK: 34
  - JVM Target: 1.8

#### 2. **dsa** - Data Structures & Algorithms Module
- **Purpose**: Dedicated module for practicing DSA problems and algorithms
- **Package**: `com.example.dsa`
- **Key Features**:
  - Array and Hashing problems
  - Basic programming algorithms
  - Interactive problem-solving environment
- **Contents**:
  - **Array_Hashing/** - Collection of array-based problems:
    - `AnagramString.kt` - Anagram detection algorithms
    - `ConcatenationOfArray.kt` - Array concatenation solutions
    - `DuplicateItemInArray.kt` - Duplicate detection in arrays
    - `TwoSumArray.kt` - Two Sum problem implementations
  - **BasicPrograms.kt** - Fundamental algorithms:
    - Palindrome detection
    - Fibonacci sequence generation
    - Anagram checking
    - Prime number validation
    - Factorial calculation
    - String reversal
    - Number swapping without third variable
    - Second largest element finding
    - Smallest word extraction


### 📚 Learning Resources

#### 3. **androidInteview/** - Android Interview Preparation
- **Purpose**: Comprehensive collection of Android interview resources and notes
- **Contents**:
  - **android-interview-questions.md** - Links to interview resources and experiences
  - **Android.md** - Advanced Android concepts and best practices:
    - Coroutines and Dispatchers
    - Main thread handling with `Dispatchers.Main.immediate`
    - CoroutineExceptionHandler and supervisorScope
    - withContext(NonCancellable) usage
    - ViewModel scope management
    - yield() function usage
  - **Java.md** - Java-specific interview topics
  - **kotlin.md** - Kotlin language concepts (placeholder)

#### 4. **SystemDesign/** - System Design Learning Path
- **Purpose**: Structured learning path for mobile system design
- **Contents**:
  - **SystemDesignRoadmap/** - 8-week learning curriculum:
    - **RoadMap.md** - Complete learning roadmap with weekly goals:
      - Week 1: Foundations of System Design
      - Week 2: Android App Architecture & Clean Code
      - Week 3: Networking & APIs
      - Week 4: Data Storage & Offline Sync
      - Week 5: Authentication & Security
      - Additional weeks covering advanced topics
    - **Concepts.md** - Core system design concepts and principles
    - **Week1.md** - Detailed Week 1 curriculum and exercises

### 🔧 Build Configuration

#### Gradle Configuration
- **Root**: Multi-module Android project with Gradle Kotlin DSL
- **Version Management**: Centralized dependency management via `gradle/libs.versions.toml`
- **Modules**: 
  - `:app` - Main application
  - `:dsa` - Data Structures & Algorithms module

#### Dependencies
Both modules include:
- AndroidX Core KTX
- Lifecycle Runtime
- Activity Compose
- Compose BOM
- Material 3
- Testing frameworks (JUnit, Espresso)

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- JDK 11 or higher
- Android SDK 35
- Kotlin 1.9+

### Setup Instructions
1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Build the project
5. Run either the `app` or `dsa` module

### Running the Applications
- **Main App**: Run the `app` module to see the basic Android Compose application
- **DSA Module**: Run the `dsa` module to access the algorithms playground

## 📖 Learning Path

### For Android Development
1. Start with the `app` module to understand basic Compose UI
2. Study the interview resources in `androidInteview/`
3. Practice with the DSA problems in the `dsa` module
4. Follow the system design roadmap in `SystemDesign/`

### For DSA Practice
1. Explore `BasicPrograms.kt` for fundamental algorithms
2. Work through the `Array_Hashing/` problems
3. Implement and test your solutions
4. Compare with existing implementations

### For System Design
1. Begin with `SystemDesign/SystemDesignRoadmap/RoadMap.md`
2. Follow the 8-week curriculum
3. Complete weekly deliverables
4. Practice with mock questions provided


## 📝 Contributing

1. Fork the repository
2. Create a feature branch
3. Add your implementations or improvements
4. Write tests for new functionality
5. Submit a pull request


## 🤝 Support

For questions or contributions, please open an issue or submit a pull request.

---

**Happy Coding! 🎉** 
