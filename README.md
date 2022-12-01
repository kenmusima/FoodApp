# FoodApp Demo
An android application that uses a section of Android Jetpack libraries and has a pre populated database showcasing different types of food to order.

### Prerequisites
To setup the project add the following secret and keys in `local.properties` file:
Google SignIn ID
```yaml
serverClientID=""
```

Firebase Setup tutorial
[Firebase Setup](https://firebase.google.com/docs/android/setup)
[Firebase Authentication](https://firebase.google.com/docs/auth/android/password-auth)

To run the Project build using Android Studio or IntelliJ and all the required dependencies will be downloaded and installed.

## Architecture

The project uses MVVM architecture pattern.

## Libraries Used

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel/) - Store and manage UI-related data in a lifecycle conscious way.
* [ViewBinding](https://developer.android.com/topic/libraries/data-binding) - Library that helps write code that interacts with views more easily.
* [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) - Android Jetpack's Navigation component helps in implementing navigation between fragments.
* [Dagger Hilt](https://developer.android.com/jetpack/androidx/releases/hilt) - For Dependency Injection.
* [Datastore](https://developer.android.com/topic/libraries/architecture/datastore) - To store profile image user uri.
* [Room](https://developer.android.com/training/data-storage/room) - Used as local database to store booked orders.

## UI