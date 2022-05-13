## Project

This project represents an app that displays a list of users. For each user we show the name and email.
If you long press on a user item, we will show an AlertDialog which will ask the user if he/she wants to delete the user.
On the home screen floating button click we navigate to a new fragment where we can create a new user.

## Libraries

- [Navigation Component][navigation]
- [Retrofit][retrofit]
- [OkHttp][okhttp]
- [Dagger Hilt][hilt]
- [Gson][gson]
- [Glide][glide]
- [RxJava][rxjava]
- [Mockk][mockk]

[navigation]: https://developer.android.com/guide/navigation
[retrofit]: https://square.github.io/retrofit/
[okhttp]: https://square.github.io/okhttp/
[hilt]: https://developer.android.com/training/dependency-injection/hilt-android
[gson]: https://github.com/google/gson
[glide]: https://github.com/bumptech/glide
[RxJava]: https://github.com/ReactiveX/RxAndroid
[mockk]: https://mockk.io/

## Architecture
This project uses Model-View-ViewModel with Clean Architecture.

## Project Structure
**di** - dependency injection package where we created:

**domain** - this package is split into several other packages and is the "middle-man" between the presentation layer and the data layer.

* repository ( interface ) : middle class in our domain layer that it is responsible to pass data to the right data source. A mediator in our code.
* usecase : is injected inside our presentation layer and it is responsible to pass and receive data from the repository to the  viewModel.
Usually, it s the class where we should do most of our business logic.
* model : entities that are sent to our presentation layer.

**data**
* datasource : holds classes related to remote and local work. For example, remote data sources have our api client injected and if we have a local data sources we can inject a local storage
* repository ( implementation ) : middle class in our domain layer that it is responsible to pass data between presentation and data. A mediator in our code.

**networking** - contains implementation details about our http client and interceptors.
**presentation** - contains a single activity with a navigation component. each screen is represented by a fragment, a viewModel and a layout. We interact with our views through view binding. We try to avoid having any kind of logic inside our fragments and everything is passed onto our viewModel which then decides what to do with the data.

## Testing
This project uses JUnit 4 framework and Mockk library for unit tests.

### What do we unit test?

presentation - unit test our viewModels

# Sliide Android developer challenge
## Congratulations, you have reached the next stage which is solving a Sliide practical test.
We’d like to you to write simple Android application for managing users.

### Description
When we have reviewed your test, and any accompanying documents you feel necessary, if we like what we see, we’ll invite you to join us for a video conversation during which we’ll ask you to go through your test, explaining any decisions that you made.

### Implementation
For implementation we use https://gorest.co.in/ public API

### Functional requirement
Feel free to use whatever flare you can to show off your skills.

You shouldn't spend more than 1 day on implementation, but if you need more time to show the best quality, feel free to use it. We prefer finished, clean, production ready implementation with unit tests, rather than half done solution.

#### 1 Displaying list of users
- After app is open list of users is displayed (only users from last page of the endpoint)
- Each entry contains name, email address and creation time (relative to now)
- Loading and error state are welcome

#### 2 Adding new user
- After + button is clicked pop up dialog is displayed with name and email entries
- After confirmation and successful user creation (201 response code) item is added to the list

#### 3 Removing existing user
- After item long press pop up dialog is displayed with question “Are you sure you want to remove this user?“
- After OK is clicked and user is removed (204 response code) item is deleted from the list

### Technical requirements
- Application must be developed in Kotlin with minimum Android SDK version of 21
- You are free to use whatever frameworks or tools you see fit
- Application needs to support device rotation
- Design should follow Material design guidelines
- RxJava or Coroutines
- Architecture one of MVP/MVVM/MVI
- Dependency injection with Dagger 2 or Hilt
- Unit tests

### Evaluation Criteria
- You create testable code
- You pay attention to detail
- Code should be production ready

### Deliverables
- The forked version of this repo


