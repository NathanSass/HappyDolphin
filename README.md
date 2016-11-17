# HappyDolphin

This is an android app that allows users to view and interact with Instagram.

Technologies used:
[Async HTTP Client](http://loopj.com/android-async-http/)
  - Good for making single requests to update the UI thread.
  - Designed to work with REST and JSON.
  - Has an easy params builder.
  
SharedPreferences
  - I saved the access token in shared preferences so in the future I could implement a feature where the user would not need to login every time to see new posts.
  - shared preferences is perfect for a small collection of key value pairs.

RecyclerView
  - The posts are displayed in a recylcer view.
  - The recycler view uses the view holder pattern which speeds up populating the list items by caching lookups.
  - Flexible: allows animations, makes switching to another type of list easy (ex. gridview).
  - Potential drawback: allows more developer control over some types of click events but requires more code to get it working.
 
ActiveAndroid
  - I wanted to improve the experience of loading up the app.
  - ActiveAndroid is an easy to use ORM that will allow me to save the last loaded posts to the device and then show them while the user is waiting for the newest posts to load.
  - Also helps me manage screen rotations.
  
 Picasso
   - Makes downloading, caching and displaying images from the web easier.
   - Small library and easy to use API.
   
 Next Steps:
 - I have the method calls stubbed out for liking / unliking a photo but my current API permissions do not allow me to interact with the API in that way.
 - I would also add a search if the api allowed it.
 - Consider not making the user login every time. If there is an access_token in shared preferences, the user could go straight into the app without generating a new token.
 
 Cool features:
 - Phone vibrates when a photo is liked.
 - Has a placeholder image for images before they load.
 - Circular profile picture above posts.
 
 <img src='http://i.giphy.com/3o7TKy96xYx4XUYPmw.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />
   