# KMP Project - SWE Challnege

<div>
  <a href="https://github.com/botaoap/swe-challenge">
  <img src="https://img.shields.io/github/repo-size/botaoap/swe-challenge">
  </a>
</div>

This project is a Kotlin Multiplatform (KMP) application that targets multiple platforms including Android and iOS. It demonstrates the use of shared code across different platforms using MVVM architecture.

## Project Structure

- **commonMain**: Contains the shared Kotlin code that is common across all platforms.
- **commonTest**: Contains the shared Kotlin unite test code that is common across all platforms.
- **androidMain**: Contains the Android-specific code and configurations.
- **androidUnitTest**: Contains the Android-specific unit test code and configurations.
- **iosMain**: Contains the iOS-specific code and configurations.
- **nativeMain**: Contains the iOS-specific code and configurations too.
Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)

## Project Structure inside commonMain

- **data**: Contains data sources, repositories, and data models.
- **domain**: Contains use cases and business logic.
- **ui**: Contains UI components and view models.
- **modules**: Contains feature modules.
- **di**: Contains dependency injection setup.
- **components**: Contains reusable UI components.
- **constants**: Contains constant values used across the project.
- **util**: Contains utility classes and functions.
- **navigation**: Contains navigation setup and routes.

## Prerequisites

- **Kotlin**: Ensure you have Kotlin installed. You can download it from [here](https://kotlinlang.org/).
- **Android Studio**: Recommended for Android development. Download it from [here](https://developer.android.com/studio?gad_source=1&gclid=EAIaIQobChMIi-WUkYTYiQMViSBECB1UHymHEAAYASAAEgJV4fD_BwE&gclsrc=aw.ds).
- **Xcode**: Required for iOS development. Download it from the Mac App Store [here](https://developer.apple.com/xcode/).
- **Gradle**: Ensure you have Gradle installed. You can download it from [here](https://gradle.org/install/).
- **KDoctor** Ensure you have KDoctor installed. You can follow steps [here](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-setup.html#check-your-environment)

## Getting Started

### 1. Create the Project

To create the initial project, use the Kotlin Multiplatform wizard available [here](https://kmp.jetbrains.com/?_gl=1*1swua1s*_gcl_au*MTY2MTI3Mjc2MC4xNzI2NzUxOTU5*_ga*MTU5MjM3Njc3Mi4xNzI2NzUxOTU3*_ga_9J976DJZ68*MTczMTQ1NjQwNC42LjEuMTczMTQ1NzMzOS41NS4wLjA.).
Or follow step by step [here](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-create-first-app.html)

### 2. Clone the Repository

```sh
git clone https://github.com/botaoap/swe-challenge.git
cd swe-challenge
```
### 3. Open the Project

Android Studio: Open the project by selecting the build.gradle.kts file in the root directory.
Xcode: Open the iosApp/iosApp.xcworkspace file.

### 4. Build the Project

Android: Use Android Studio to build the project. Select the androidApp module and click on the “Run” button.
iOS: Use Xcode to build the project. Select the iosApp target and click on the “Run” button.

### 5. Run the Project

Android: Use an Android emulator or a physical device to run the androidApp.
iOS: Use an iOS simulator or a physical device to run the iosApp.

### Technologies Used
 
Koin: For dependency injection.
Coil: To load images from URLs.
DataStore: To persist login and data inside the app.
Compose Multiplatform: To create a single codebase in Jetpack Compose that transforms into Android and iOS apps.
DummyJson: Consuming the free API from dummyJson.
Ktor: To consuming the API's

### Screens

Splash Screen: Initial screen displayed when the app launches.
Login Screen: Screen for user login.
Main Screen: Contains bottom navigation to show three other screens:
  - Home Screen: Main content screen.
  - Platform Screen: Displays platform-specific information.
  - Profile Screen: User profile screen.

### Features

Persistent Login: The application maintains the login state even if the app is killed. Users will be logged in automatically unless they log out.

### Unit Tests

Unit tests are created for the following layers:
- Repositories
- Use Cases
- Web Service

These tests are implemented for Android (androidUnitTest).

### SSL Certificate Workaround

To fetch data from dummyJson, a workaround for the SSL certificate is implemented. This is not recommended for production environments.

### Resources and Documentation

The following resources were used to create this project and gather information:

- [Kotlin Multiplatform Setup](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-setup.html)
- [Getting Started with Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform Resources Usage](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-resources-usage.html)
- [Create Your First Compose Multiplatform App](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-create-first-app.html)
- [Guide to Kotlin Multiplatform Package Structure](https://medium.com/@kerry.bisset/unifying-code-across-platforms-a-guide-to-kotlin-multiplatform-package-structure-1ad9fb630ddf)
- [Deep Dive into KMP Architecture](https://medium.com/@rudradave/kotlin-multiplatform-mini-course-deep-dive-into-kmp-architecture-part-2-e078fd7b5c37)
- [Building Cross-Platform Apps with Clean Architecture and KMM](https://medium.com/@hassenmabrouk/building-cross-platform-apps-with-clean-architecture-and-kmm-3950444660f8)
- [Ktor Client Setup](https://ktor.io/docs/client-create-new-application.html)
- [Kotlin Multiplatform Discover Project](https://kotlinlang.org/docs/multiplatform-discover-project.html)
- [Jetpack Compose Navigation](https://developer.android.com/develop/ui/compose/navigation#kts)
- [Persistent Data Storage in Jetpack Compose](https://medium.com/@rowaido.game/persistent-data-storage-using-datastore-preferences-in-jetpack-compose-90c481bfed12)
- [Navigation in Jetpack Compose](https://developer.android.com/guide/navigation/use-graph/navigate)
- [RecyclerView in Compose](https://developer.android.com/develop/ui/compose/migrate/migration-scenarios/recycler-view)
- [Loading Images in Compose](https://developer.android.com/develop/ui/compose/graphics/images/loading)
- [Glide for Android](https://bumptech.github.io/glide/int/compose.html)
- [Coil for Jetpack Compose](https://github.com/coil-kt/coil#jetpack-compose)
- [Text Input in Compose](https://developer.android.com/develop/ui/compose/text/user-input)
- [Saving Objects in DataStore](https://medium.com/supercharges-mobile-product-guide/new-way-of-storing-data-in-android-jetpack-datastore-a1073d09393d)
- [Mocking with MockK](https://mockk.io/)

### License

This project is licensed under the MIT License. See the LICENSE file for details.

### Contact

For any questions or suggestions, please contact gabrielbotao@gmail.com
