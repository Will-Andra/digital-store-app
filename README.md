🛒 DigitalStore App
+ DigitalStore is a native Android application built with Kotlin that simulates a modern e-commerce ecosystem. The project follows industry best practices and Android architecture guidelines, leveraging a Modular Layered Architecture, Jetpack Compose for declarative UI, Dependency Injection with Hilt, and Reactive State Management.

<img width="250" height="532" alt="image" src="https://github.com/user-attachments/assets/3dd3d319-1a04-404d-9f4f-2935e4347d89" />
<img width="250" height="532" alt="image" src="https://github.com/user-attachments/assets/d77d468e-7ec6-44ee-b402-e5037fa1d84c" />
<img width="250" height="532" alt="image" src="https://github.com/user-attachments/assets/190cabc2-b6f1-4f81-a695-f5cdb0316d7d" />

🛠️ Tech Stack & Libraries
+ Language: Kotlin
+ Data Source & Mock API: DummyJSON API (RESTful consumption of simulated products, users, and carts)
+ UI & Layouts: Jetpack Compose (Declarative UI)
+ Architecture & State: Clean Architecture + MVVM with StateFlow and reactive UiState (Unidirectional Data Flow - UDF)
+ Dependency Injection: Hilt / Dagger (@HiltAndroidApp, @HiltViewModel)
+ Navigation: Navigation Compose (NavHost, navController)
+ Image Loading: Coil Compose
+ Error & Network Handling: safeCall wrapper for safe response encapsulations and custom exceptions (AppError)
+ Version Control: Git & GitHub (Branch-based workflow and standardized commit conventions)

⚡ State Management & Data Flow (UDF)
+ The application strictly enforces the Unidirectional Data Flow (UDF) pattern to ensure the UI is a direct, predictable function of the underlying application state:
+ Reactive UiState: Each screen exposes a dedicated sealed interface or data class representing exact UI states (Loading, Success, Error).
+ StateFlow & Coroutines: ViewModels expose state via immutable StateFlow, react to user actions, and encapsulate core business logic.
+ Lifecycle-Aware UI Consumption: In Jetpack Compose, states are observed using collectAsStateWithLifecycle(), preventing memory leaks and avoiding unnecessary recompositions.

📱 Feature Modules & Screen Structure
+ The app is organized inside the features package and includes the following navigation flow and screens:

1. 🚀 Splash Screen (features/splash/)
+ Purpose: Entry screen presenting the app's visual identity.
+ Behavior: Displays the logo over a solid #072985 background with timed transitions via coroutines. Includes lifecycle safeguards to prevent app freezes.

2. 🏠 Home Screen (features/home/)
+ Purpose: Main catalog and store dashboard.
+ Behavior: Integrates with the system BackHandler using moveTaskToBack(true) to gracefully minimize the app without destroying state.

3. 🛍️ Product Catalog (features/products/)
+ Purpose: Dynamic listing of available store products.
+ Features: Image rendering with Coil, async state handling (UiState), and product detail navigation.

4. 🛒 Shopping Cart (features/cart/)
+ Purpose: Management of user-selected items for checkout.
+ Features: Item listing, quantity adjustments, and total price calculation.

5. 👤 Users & Profile (features/users/)
+ Purpose: User account and profile management.

6. 📝 Posts & News (features/posts/)
+ Purpose: Module for platform communications and news feed.

📂 Package Architecture
+ Plaintext
  com.example.digitalstore/
  ├── DigitalStoreApplication.kt  # Base Application class (@HiltAndroidApp)
  ├── MainActivity.kt             # Entry Point Activity for Compose
  │
  ├── core/                       # Shared modules and base configurations
  │   ├── di/                     # Hilt Modules (Network, Database, Repositories)
  │   ├── domain/                 # Business Entities, UseCases, and Domain Logic
  │   ├── navigation/             # AppNavigation, routes, and NavHost graph
  │   └── util/                   # Extensions, safeCall wrappers, and utilities
  │
  ├── features/                   # Presentation Layer grouped by feature
  │   ├── cart/                   # Shopping Cart Module
  │   ├── home/                   # Home Dashboard Module
  │   ├── posts/                  # Posts/News Feed Module
  │   ├── products/               # Product Catalog Module
  │   ├── splash/                 # Splash Screen Module
  │   └── users/                  # User Profile Module
  │
  └── ui/                         # Global visual styles
      └── theme/                  # Colors, Typography, Shapes, and Compose Themes
      
🚀 Getting Started
+ Clone the repository:
  Bash
  git clone https://github.com/your-username/digital-store-app.git
  Open the project in Android Studio.
  Wait for Gradle sync to complete.
  Run the app on an Emulator or physical device (Android 8.0 / API 26+).


