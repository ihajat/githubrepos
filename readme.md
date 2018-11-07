Introduction

Features This sample contains two screens: a list of repos, from github,  and a detail view that shows the repo information.

This example apo consists of two screens displaying data about repos. The first screen will display a list of repos.
When the user selects an item the application should show the second screen containing more information about the repo.

The user should be able to invoke a reloading of the data from the server.

The application should fetch the list of repos available from https://api.github.com/users/ihajat/repos

Repos persisted for offline viewing, using ROOM database
Includes a detailed pane, for future use, if there is extra information to be displayed about the repo
Uses DI with Dagger 2
Uses Android Architeture Components to ensure Lifecycle aware View Models.
The app uses a Model-View-ViewModel (MVVM) architecture for the presentation layer.
Includes pagination.
Includes a limit to prevent overloading too much data.
Package Structure

api - bbc service interface, returns a live data object a generic live data with a generic arguments of API response
applicaiton - initials DaggerAppComponent and LoggingExceptionHandle
dao - database related classes including data access objects
di - dependency injection Dagger 2
dto - model
exceptions - exception handling for reporting crashes and exceptions
guiView - gui interface
network - contains the NetworkBoundResource
repo - repository layer. repository to separate the logic that retrieves the data and maps it to the entity model from the business logic that acts on the model
ui - activities, adapters
utils - adapters used by retrofit, eg A Retrofit adapter that converts the Call into a LiveData of ApiResponse.
viewmodel - view model for Main Activity
NetworkBoundResource A generic class that can provide a resource backed by both the ROOM database and the network ( Both the netowrk and the database both return LiveData). The NetworkBoundResource class is an abstract class with the following abstract method:

1. saveCallResult: This method is responsible for updating/inserting the result of the API into the local database. This method will be called when the data from the remote server is successfully fetched.
shouldFetch: Based on implementation, this method should return true if it is needed to fetch the data from a remote server. For example when the data is outdated. Else it should return false.

2. loadFromDb: This method is responsible for returning the data from the local database.

3. createCall: This method is responsible for creating a remote server call which is responsible for fetching the data from the server. Later we will see how to wrap this result in LiveData.
After implementing these abstract methods, we can call the getAsLiveData method of NetworkBoundResource class, which returns a LiveData object that can be observed for changes.

Unit Tests:

Contains two classes:

1. RepoApiTest:
    Test 1: Test the rest api point using the mock server
    Test 2: Test the Repos Size is Correct
    Test 3: Test Gets Firsts repo Successfully

2. LiveDataTestUtil
    Get the value from a LiveData object. We're waiting for LiveData to emit, for 2 seconds. Once we got a notification via
    onChanged, we stop observing.
Instrumentation Tests: Loads the data and compares the actual size of the list with the expected size.

RecyclerView does not inherit from AdapterView (it’s a direct subclass of ViewGroup instead), so you can’t use onData with it; hence need to write lots of boilerplate code, called idling resource, to ensure recycler_view is loaded before checking size, or value checking of rows.

Please Note, this is no longer required with the latest Espresso library , as it supports RecyclerViews.

Recommendations for future improvements:

1. Convert to Kotlin, however , please note, it's not jsut a case of converting the code from Java to Kotlin, rather, Kotlin brings        a whole new paradigm shift, with immutable variables , and using features like lazy initialization to eliminate nullable                variables.

2. Add coroutines for the async tasks.