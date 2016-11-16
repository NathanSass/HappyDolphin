# HappyDolphin

This is an android app that allows users to interact with the android api.

Technologies used:
[Async HTTP Client](http://loopj.com/android-async-http/)
  - Good for making single requests to update the UI thread
  - Performant and is designed to work with REST and JSON.
  - Has an easy params builder
  
 SharedPreferences
  - I saved the access token in shared preferences so it could be accessed easily around the app
  - shared preferences is perfect for a small collection of key value pairs
  - The access token does need to be re-generated for every app launch so I could save it in shared preferences and avoid making the user sign in every time.

RecyclerView
  - The posts are displayed in a recylcer view.
  - The recycler view uses the view holder pattern which speeds up populating the list items by caching lookups.
  - Flexible: allows animations, makes switching to another type of list type easy (ex. gridview).
  - Potential drawback: allows more developer control over click events but requires more code to get it working. I chose to attach the like image behavoir to the entire list item because it made sense for the current use case.
 
 ActiveAndroid
  - I wanted to improve the experience of loading up the app.
  - ActiveAndroid is an easy to use ORM that will allow me to save the last loaded posts to the device and then show them while the user is waiting for the newest posts to load.
  
  Picasso
   - Makes downloading, caching and displaying images from the web easier.
   
