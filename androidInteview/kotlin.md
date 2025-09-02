### Jetpack Compose Interview Questions and Answers

Jetpack Compose is a modern UI toolkit for Android that simplifies and accelerates UI development. It uses a declarative approach, which is a major shift from the traditional imperative XML-based view system. When preparing for an interview, it's essential to understand the core concepts and how they differ from the old way of doing things. Here are some of the most common Jetpack Compose interview questions and their answers.

***

### Core Concepts

#### Q1: What is Jetpack Compose, and how does it differ from the traditional Android View system?

**A:** Jetpack Compose is a modern, declarative UI toolkit for Android. Unlike the traditional XML-based system, which is imperative (meaning you manually find and update views), Compose is declarative. You describe what the UI should look like based on the app's current **state**. When the state changes, Compose automatically re-evaluates and updates the UI. This eliminates the need for manual UI updates and makes the code more concise and easier to reason about.

#### Q2: What is a Composable function?

**A:** A Composable function is a fundamental building block in Jetpack Compose. It's a regular Kotlin function annotated with `@Composable` that describes a part of the UI. Composable functions are responsible for emitting UI elements and can be composed together to build complex UIs. They don't return a UI component; instead, they "emit" the UI hierarchy.

#### Q3: Explain the concept of Recomposition.

**A:** **Recomposition** is the process by which Jetpack Compose automatically re-executes composable functions when the data or state they depend on changes. It's an efficient process because Compose is "smart" enough to only update the parts of the UI that have changed, rather than redrawing the entire screen. This is a key part of the declarative paradigm and what makes Compose so performant.

#### Q4: What is a `Modifier`?

**A:** A **`Modifier`** is an ordered list of elements that decorate or augment composable functions. You use it to change the appearance, behavior, or layout of a composable. Modifiers are chained together to apply multiple transformations, such as padding, size, background color, and click handlers. The order of the modifiers in the chain is important, as they are applied from left to right.

***

### State Management

#### Q5: How does state management work in Jetpack Compose?

**A:** State management is a core concept in Compose. The UI is a function of its state. When the state changes, the UI recomposes. Compose provides the `remember` and `mutableStateOf` functions to manage this state. `remember` is used to store an object in memory during recomposition, while `mutableStateOf` creates a mutable, observable state holder. When the value inside a `MutableState` changes, any composable observing it will be automatically recomposed.

#### Q6: What is the difference between `remember` and `rememberSaveable`?

**A:** Both `remember` and `rememberSaveable` are used to retain state across recompositions. However, their scope of persistence is different.
* `remember`: Retains state across recompositions. The state is lost if the composable leaves the composition (e.g., due to a configuration change like a screen rotation).
* `rememberSaveable`: Retains state across recompositions and also survives configuration changes, process death, and other system-initiated lifecycle events. It uses the `SavedStateHandle` under the hood to store and restore the state.

#### Q7: Explain **State Hoisting** and its importance.

**A:** **State hoisting** is a pattern in which the state is moved to a higher-level, stateless composable. This makes the child composable stateless and reusable. The child composable exposes events (e.g., a callback function) to notify the parent when a change is requested. The parent then updates the state, triggering a recomposition. State hoisting is a crucial practice for:
* **Single Source of Truth:** The state is owned and managed in a single location.
* **Encapsulation:** The state logic is isolated from the UI, making it easier to test.
* **Reusability:** The stateless composable is more flexible and can be reused in different parts of the application with different states.

***

### Lifecycle and Side Effects

#### Q8: What is a "side effect" in Jetpack Compose, and how are they handled?

**A:** A **side effect** is an action that happens outside the scope of a composable function. This includes tasks that don't directly relate to UI rendering, such as fetching data from a network, updating a database, or launching a coroutine. Side effects should not be performed directly in composable functions because they can be executed multiple times due to recomposition. Compose provides specific APIs for handling side effects safely, including:
* `LaunchedEffect`: Used to launch a coroutine when a composable enters the Composition.
* `DisposableEffect`: Used for effects that require cleanup (e.g., subscribing to a listener). The cleanup logic runs when the composable leaves the Composition.
* `rememberCoroutineScope`: Provides a `CoroutineScope` tied to the composable's lifecycle, allowing you to launch coroutines that can be canceled when the composable leaves the Composition.

#### Q9: What is the purpose of `LaunchedEffect`?

**A:** `LaunchedEffect` is a composable function used to start a coroutine when the composable enters the composition. The coroutine will be automatically canceled when `LaunchedEffect` leaves the composition. It's useful for performing one-shot asynchronous operations that are tied to the lifecycle of the composable, such as fetching data, showing a `Snackbar`, or performing a navigation event.

***

### Interoperability and Architecture

#### Q10: How do you integrate Jetpack Compose with the existing Android View system?

**A:** Jetpack Compose and the traditional View system can coexist in the same project. You can embed a traditional View inside a composable using the **`AndroidView`** composable. Conversely, you can embed a composable inside an XML layout using a **`ComposeView`**. This allows for a gradual migration to Compose, where you can adopt it for new screens or components while keeping the existing ones.

#### Q11: How is navigation handled in Jetpack Compose?

**A:** Navigation in Compose is typically handled using the **Navigation Compose** library. It uses a navigation graph where each screen is a composable function. A **`NavHost`** composable is used to display the current screen, and a **`NavController`** manages the navigation state, allowing you to navigate between composables by calling functions like `navController.navigate()`. This approach provides a clean and type-safe way to handle screen transitions within your app.

#### Q12: How do you use `ViewModel` with Jetpack Compose?

**A:** The `ViewModel` is a lifecycle-aware component used to manage UI-related data and business logic. It's recommended to use the `ViewModel` with Compose to separate the UI from the data logic, following the Unidirectional Data Flow (UDF) pattern. You can observe `LiveData` or `Flow` from the `ViewModel` within your composable functions using helper functions like `collectAsState()` or `observeAsState()`. This ensures the UI is automatically updated when the data in the `ViewModel` changes and that the data survives configuration changes.









### Basics and Core Concepts (20 questions)

1.  **Q: What is the core principle of Jetpack Compose?**
    * **A:** It's a declarative UI framework. You describe the UI as a function of the application's state, and the framework takes care of updating it when the state changes.

2.  **Q: What is a `@Composable` function?**
    * **A:** A regular Kotlin function annotated with `@Composable` that describes a part of the UI. It can call other `@Composable` functions.

3.  **Q: Explain the role of `Modifier`.**
    * **A:** A `Modifier` is an ordered list of elements that decorate or augment a composable. It's used to change the composable's size, padding, alignment, and other visual properties.

4.  **Q: What is Recomposition?**
    * **A:** The process of re-executing composable functions when their inputs or state have changed. Compose is smart about it and only re-executes the affected functions.

5.  **Q: How does Compose achieve efficiency in Recomposition?**
    * **A:** It tracks which composables depend on which state and only recomposes those that are affected by a state change. It also uses a smart diffing algorithm to minimize changes to the UI tree.

6.  **Q: What is a "Slot API"?**
    * **A:** A design pattern in Compose where a composable accepts other `@Composable` lambdas as parameters. This allows for flexible and reusable layouts, like `Scaffold` or `Row`.

7.  **Q: What is the purpose of the `Preview` annotation?**
    * **A:** `@Preview` allows you to see how a composable will render in Android Studio without running the app on a device or emulator. It speeds up UI development.

8.  **Q: What is a `remember` block?**
    * **A:** `remember` is a composable function that stores a value in memory during recompositions. The value is lost if the composable leaves the composition.

9.  **Q: What is `rememberSaveable` and when would you use it?**
    * **A:** `rememberSaveable` is like `remember` but also saves the state to the `SavedStateHandle`, so it survives process death and configuration changes (e.g., screen rotation).

10. **Q: What is State Hoisting?**
    * **A:** A pattern where state is moved from a child composable to its parent. This makes the child composable stateless, more reusable, and easier to test.

11. **Q: How do you handle user input in Compose?**
    * **A:** By passing lambda functions (callbacks) from a parent composable to a child. The child calls the lambda when an event (like a click) occurs, and the parent updates the state.

12. **Q: Explain the difference between `MutableState` and `State`.**
    * **A:** `MutableState` is a mutable state holder. When its value changes, it triggers a recomposition. `State` is an immutable read-only version.

13. **Q: What is the role of `collectAsState`?**
    * **A:** A helper function that collects values from a Kotlin `Flow` and represents the latest value as a `State` object. This causes recomposition whenever a new value is emitted.

14. **Q: How does Compose handle layout and measurement?**
    * **A:** Compose uses a single-pass measurement and layout system. Each composable measures its children before placing them, which is more efficient than the multi-pass system in the traditional View system.

15. **Q: What are `Column`, `Row`, and `Box`?**
    * **A:** Basic layout composables. `Column` arranges children vertically, `Row` arranges them horizontally, and `Box` stacks children on top of each other.

16. **Q: How do you add a click listener to a composable?**
    * **A:** You use the `Modifier.clickable` modifier. For example: `Text(text = "Click me", modifier = Modifier.clickable { ... })`.

17. **Q: What is the role of `Surface`?**
    * **A:** A composable that provides a visual surface, typically with a background color and elevation, that you can use to build your UI on top of.

18. **Q: What are `MaterialTheme` and `Scaffold`?**
    * **A:** `MaterialTheme` is a composable that provides a central place to define colors, typography, and shapes. `Scaffold` provides a standard layout structure for a screen, including a `TopAppBar`, `BottomAppBar`, and `SnackbarHost`.

19. **Q: How do you add padding in Compose?**
    * **A:** Using `Modifier.padding()`. You can specify a single value or different values for horizontal, vertical, top, bottom, etc.

20. **Q: What is the difference between a declarative and imperative UI?**
    * **A:** Declarative (Compose) means you describe the UI for a given state, and the framework updates it. Imperative (Views) means you manually find a view and update its properties.

### State and Lifecycle (15 questions)

21. **Q: What is `SnapshotStateList` and when is it used?**
    * **A:** A mutable list that triggers a recomposition when items are added, removed, or changed. It's used for managing lists of data where changes need to be reflected in the UI.

22. **Q: How do you handle state in a list of items?**
    * **A:** You can use `SnapshotStateList` or a regular `MutableList` wrapped in a `mutableStateOf`. The key is to manage the state outside the composable and pass it down.

23. **Q: Explain the concept of `key` in `LazyColumn` or `LazyRow`.**
    * **A:** The `key` parameter is used to provide a stable, unique identifier for each item in the list. This helps Compose optimize recompositions by moving and reusing composables instead of recreating them when the list changes.

24. **Q: What is a `StateFlow` and how does it relate to Compose?**
    * **A:** A `StateFlow` is a `Flow` that holds a single, up-to-date value. It's often used in `ViewModel`s to expose the UI state. You collect it in Compose using `collectAsState`.

25. **Q: How do you observe a `LiveData` from a `ViewModel` in Compose?**
    * **A:** You use the `observeAsState()` extension function from the `androidx.compose.runtime.livedata` library.

26. **Q: What is the purpose of `LaunchedEffect`?**
    * **A:** To launch a coroutine that should run when the composable enters the composition and be canceled when it leaves. It's used for one-shot side effects.

27. **Q: How does `rememberCoroutineScope` work?**
    * **A:** It provides a `CoroutineScope` that is tied to the composable's lifecycle. You can use it to launch coroutines that can be canceled when the composable is disposed.

28. **Q: What is `DisposableEffect`? Give an example.**
    * **A:** Used for side effects that require cleanup. It takes `onDispose` block, which runs when the composable leaves the composition. Example: registering and unregistering a listener.

29. **Q: When would you use `SideEffect`?**
    * **A:** `SideEffect` is used for side effects that need to be run after a successful recomposition. It's typically used for sharing state with non-Compose code, like updating an analytics library.

30. **Q: What is a `DerivedState` and why is it useful?**
    * **A:** A state that is computed from other states. It's useful to avoid unnecessary recompositions by deriving a new state only when the underlying state changes. Use `derivedStateOf`.

31. **Q: How do you handle configuration changes in Compose?**
    * **A:** By using `ViewModel` and `rememberSaveable`. The `ViewModel` survives configuration changes, and `rememberSaveable` saves and restores state across them.

32. **Q: What is `CompositionLocal`?**
    * **A:** A mechanism to pass data down the composition tree implicitly, without passing it through every single function parameter. It's used for things like `MaterialTheme` and `LocalContext`.

33. **Q: How do you create a `CompositionLocal`?**
    * **A:** You use `compositionLocalOf` or `staticCompositionLocalOf`. You then provide a value using `CompositionLocalProvider`.

34. **Q: What is a `coroutineScope` and where would you use it in Compose?**
    * **A:** `coroutineScope` is a builder function that creates a new scope. In Compose, you often get a scope using `rememberCoroutineScope` to launch coroutines.

35. **Q: Explain the `key` parameter in `remember`.**
    * **A:** The `key` parameter allows you to reset the `remember`'s state. When the `key` changes, the `remember` block is re-executed, and a new value is stored.

### Interoperability and Architecture (15 questions)

36. **Q: How do you embed a traditional View inside a composable?**
    * **A:** You use the `AndroidView` composable. You provide a factory lambda to create the view and an update lambda to update its properties.

37. **Q: How do you embed a composable inside an XML layout?**
    * **A:** You use `ComposeView` in your XML layout file. You can then set the composable content using `composeView.setContent { ... }`.

38. **Q: What is a `ViewModel` in the context of Compose?**
    * **A:** A lifecycle-aware component that holds and manages UI-related data. It's the recommended way to handle business logic and state that survives configuration changes.

39. **Q: How do you get a `ViewModel` in a composable function?**
    * **A:** You can use the `viewModel()` composable function from the `androidx.lifecycle.viewmodel.compose` library.

40. **Q: What is the recommended way to handle navigation in Compose?**
    * **A:** Using the **Navigation Compose** library. It provides a `NavHost` and `NavController` to manage navigation between composable screens.

41. **Q: How do you pass data between composable screens using Navigation Compose?**
    * **A:** You can pass primitive types in the route string or use `SavedStateHandle` in the `ViewModel` to share data.

42. **Q: Explain the concept of `Unidirectional Data Flow` (UDF) in Compose. How is it implemented?**
    * **A:** The UI is a function of the state. The state flows down from a higher level (e.g., `ViewModel`), and events flow up from the UI to update the state. This is implemented by passing state down and callbacks up.

43. **Q: What are the benefits of UDF?**
    * **A:** Predictability, testability, and a single source of truth for the application state.

44. **Q: How do you handle dependency injection with Compose?**
    * **A:** You can use popular DI frameworks like Hilt. You can inject the `ViewModel` using the `@HiltViewModel` annotation and retrieve it using `hiltViewModel()` in your composable.

45. **Q: What is a stateless composable? What is a stateful composable?**
    * **A:** A stateless composable doesn't hold its own state. It receives state and callbacks as parameters. A stateful composable holds its own state and manages it internally.

46. **Q: Why is it a good practice to favor stateless composables?**
    * **A:** They are more reusable, easier to test, and their state is managed by a single source of truth, making the app's behavior more predictable.

47. **Q: How do you handle permissions in a Compose app?**
    * **A:** You can use the **Accompanist Permissions** library, which provides composables for requesting and managing permissions.

48. **Q: How do you use Room or another database with Compose?**
    * **A:** You use a `ViewModel` to hold the database repository. The repository returns a `Flow` of data, which you then collect in your composable using `collectAsState`.

49. **Q: How can you use Compose for a large-scale application with a clean architecture (e.g., MVI)?**
    * **A:** Compose naturally fits the MVI (Model-View-Intent) pattern. The `ViewModel` acts as the `Model` and `Intent` handler, and the composable functions are the `View` that react to state changes.

50. **Q: How do you perform unit tests on a composable?**
    * **A:** You use the `compose-test-rule` library. You can set the content of the test rule and then use various `onNode` and `performClick` matchers and actions to test the composable's behavior.

### Advanced Topics and Best Practices (20 questions)

51. **Q: What is `layoutId`?**
    * **A:** An identifier used in custom layouts (e.g., `ConstraintLayout`) to reference and constrain composables.

52. **Q: How do you use `remember` with a custom `key`?**
    * **A:** You can pass a key to `remember` as an argument. When the key changes, `remember` will re-execute its calculation block and store the new value.

53. **Q: What is the purpose of `Layout` composable?**
    * **A:** To create a custom layout for a composable. It's used when `Column`, `Row`, or `Box` are not sufficient.

54. **Q: Explain the difference between `mutableStateOf` and a `data class` with `mutableStateOf` properties.**
    * **A:** When a `data class` wrapped in `mutableStateOf` changes, the entire object is considered a new state. This triggers a full recomposition. When a property inside `mutableStateOf` is changed, only the composables that use that specific property are recomposed.

55. **Q: What is a `slot-based` API?**
    * **A:** A design pattern that allows a parent composable to define "slots" for child composables to be placed in. This provides great flexibility and reusability (e.g., `Scaffold`).

56. **Q: How can you create a custom `Modifier`?**
    * **A:** By implementing the `Modifier.Element` interface or by using factory functions to create a custom modifier chain.

57. **Q: How do you handle animations in Compose?**
    * **A:** Compose provides a rich set of animation APIs. You can use `animate*AsState` for simple value animations, `Transition` for multi-state animations, and `AnimatedVisibility` for showing/hiding composables.

58. **Q: What is the purpose of `animateContentSize`?**
    * **A:** A modifier that automatically animates the size change of a composable when its content changes.

59. **Q: What is a `Snapshot` and what is its role in Compose's state management?**
    * **A:** A `Snapshot` is a mechanism for tracking state changes. It's an internal part of Compose that allows for efficient, concurrent reads and writes to state, ensuring all observers get a consistent view of the data.

60. **Q: How do you create an `Indication` for a clickable composable?**
    * **A:** You use the `indication` parameter in `Modifier.clickable` and provide an `Indication` object, for example, `rememberRipple()`.

61. **Q: What is a `LazyColumn` and when should you use it?**
    * **A:** A composable that lays out its children on demand. You should use it for long lists or lists of unknown size to optimize performance by only creating and laying out visible items.

62. **Q: How is `LazyColumn` different from a simple `Column` with a `for` loop?**
    * **A:** A `Column` with a `for` loop creates all children at once, which is inefficient for long lists. A `LazyColumn` recycles and reuses composables for better performance.

63. **Q: How do you handle keyboard input in a `TextField`?**
    * **A:** A `TextField` takes a `value` and an `onValueChange` lambda. You pass a state variable to `value` and update it in the `onValueChange` lambda.

64. **Q: What is `rememberUpdatedState`?**
    * **A:** A composable function that creates a `State` object that is automatically updated with the latest value. It's useful in `LaunchedEffect` to prevent the effect from re-running when a value changes.

65. **Q: How do you handle accessibility in Compose?**
    * **A:** Compose provides `Modifier.semantics` and other attributes to make your UI accessible. You can provide content descriptions, specify screen reader actions, and more.

66. **Q: What is the benefit of using `ImageVector` over a simple `Painter`?**
    * **A:** An `ImageVector` is a vector-based graphic that is a lightweight alternative to a `Painter`. It's defined in Kotlin and can be easily animated or tinted, making it more flexible.

67. **Q: How do you measure the performance of a composable?**
    * **A:** You can use the `Compose Metrics` tool in Android Studio to identify recomposition counts and skip counts. You can also use the `Layout Inspector` and `Profiler`.

68. **Q: What is a `Composition`?**
    * **A:** A `Composition` is the internal data structure that represents the UI tree of your composable functions. It's the "blueprint" of your UI.

69. **Q: How do you manage focus in a composable?**
    * **A:** You can use `Modifier.focusable` and `Modifier.focusRequester`. A `FocusRequester` can be used to manually request focus to a specific composable.

70. **Q: How can you create a custom `Shape` in Compose?**
    * **A:** By implementing the `Shape` interface and overriding the `createOutline` function to define a custom path or shape.

### Miscellaneous and Practical Questions (30 questions)

71. **Q: How do you add a `TopAppBar` and `BottomAppBar` to a screen?**
    * **A:** By using the `Scaffold` composable, which has dedicated parameters for `topBar` and `bottomBar`.

72. **Q: What are `Density` and `Dp`?**
    * **A:** `Density` is an interface that provides the screen's density for unit conversion. `Dp` (Density-independent pixel) is a unit of measurement that scales with the screen density.

73. **Q: How do you set a background color on a composable?**
    * **A:** Using `Modifier.background()`. For example, `Modifier.background(Color.Red)`.

74. **Q: What is a `BoxWithConstraints`?**
    * **A:** A composable that provides a `Box` layout and also a `Constraints` object, allowing you to create different layouts based on the available space.

75. **Q: How do you create a custom `AlertDialog`?**
    * **A:** You can use the built-in `AlertDialog` composable or create a custom one using `Dialog` composable.

76. **Q: How do you handle different screen sizes and orientations?**
    * **A:** By creating responsive layouts using `Column`, `Row`, `BoxWithConstraints`, or by using dedicated libraries like **Accompanist Window**.

77. **Q: How do you change the font family of a `Text` composable?**
    * **A:** You can use the `fontFamily` parameter or, for a more global approach, define a `Typography` in your `MaterialTheme`.

78. **Q: What is `LocalContext`?**
    * **A:** A `CompositionLocal` that provides the current `Context`. You can get it using `LocalContext.current`.

79. **Q: How do you show a `Snackbar` in Compose?**
    * **A:** You use `SnackbarHostState` from `Scaffold`. You launch a coroutine to show the snackbar using `snackbarHostState.showSnackbar()`.

80. **Q: What is `state.value`?**
    * **A:** The way to access the current value of a `MutableState`. Accessing it triggers a recomposition when the value changes.

81. **Q: How do you create a custom theme for your app?**
    * **A:** You create a custom `MaterialTheme` composable with your own `colors`, `typography`, and `shapes` defined.

82. **Q: What is `FlowRow` and `FlowColumn`?**
    * **A:** Layout composables (from Accompanist) that wrap their content to the next line when they run out of space, similar to CSS flexbox.

83. **Q: How do you use the `Canvas` composable?**
    * **A:** A composable that provides a drawing surface for custom graphics. You can use it to draw lines, shapes, text, and more.

84. **Q: How can you use a `WebView` in Compose?**
    * **A:** You can use the `AndroidView` composable to embed a `WebView` from the traditional View system.

85. **Q: What are `CompositionLocal` and `CompositionLocalProvider`?**
    * **A:** `CompositionLocal` is an implicit way to pass data down the tree. `CompositionLocalProvider` is used to provide a value for a `CompositionLocal` within a sub-tree.

86. **Q: What is a `Snapshot` and what is its role?**
    * **A:** `Snapshot` is an internal mechanism that Compose uses to efficiently track state changes. It allows for concurrent state changes and consistent UI updates.

87. **Q: What is a `Box` layout?**
    * **A:** A layout composable that stacks children on top of each other. You can use `contentAlignment` to align the children within the `Box`.

88. **Q: How do you handle focus for a group of composables?**
    * **A:** You can use `Modifier.focusable` and `Modifier.onFocusChanged` to manage focus state.

89. **Q: What is the purpose of `remember` in a `LazyColumn`?**
    * **A:** `remember` is often used inside the `items` block of a `LazyColumn` to retain the state of each item across recompositions and when the item scrolls out of view and back in.

90. **Q: How do you use `remember` with a `data class`?**
    * **A:** `remember { mutableStateOf(MyDataClass(...)) }`. You can then update the properties of the data class directly, but this won't trigger a recomposition. You need to assign a new instance `state.value = state.value.copy(...)` to trigger a recomposition.

91. **Q: How do you use `LaunchedEffect` with a `key`?**
    * **A:** `LaunchedEffect(key1 = someValue)` will re-run the coroutine block whenever `someValue` changes. If you pass `Unit`, it will run only once.

92. **Q: What is the `contentPadding` parameter in `Scaffold`?**
    * **A:** It's a `PaddingValues` object that you can apply to the main content of the screen to avoid it being clipped by the `TopAppBar` or `BottomAppBar`.

93. **Q: How do you handle gestures in Compose?**
    * **A:** You can use modifiers like `Modifier.pointerInput` to handle various touch events, including drag, swipe, and zoom.

94. **Q: What is a `BoxWithConstraints` and when is it useful?**
    * **A:** A composable that allows you to get the incoming `Constraints` (min/max width and height) and use them to build a responsive layout.

95. **Q: How do you create a custom `Card` with a custom shape?**
    * **A:** You can use the `Card` composable and provide a custom `shape` parameter.

96. **Q: What is the difference between `Padding` and `Spacer`?**
    * **A:** `Modifier.padding` adds space around a composable within its existing layout. `Spacer` is an empty composable that takes up a specified amount of space.

97. **Q: How do you use `Flow` from a `ViewModel` in Compose?**
    * **A:** You use `viewModel.myFlow.collectAsState()`, which returns a `State` object that automatically recomposes the UI when the flow emits a new value.

98. **Q: What is `Composition.current`?**
    * **A:** It's a `CompositionLocal` that provides the current `Composition` instance. It's used by the framework for internal purposes, and you typically won't need to use it directly.

99. **Q: How do you show a custom `Toast` message?**
    * **A:** You can use `LocalContext.current` to get the context and then show a `Toast` from the traditional Android API.

100. **Q: What are the main benefits of Jetpack Compose?**
    * **A:** Faster development time, less code, a single codebase (Kotlin), a reactive and declarative approach, and powerful tools for previewing and testing.
