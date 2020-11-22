# To run the project as is (not running tests)

Open project as usual

Go to the main method at review\src\main\java\com\antonioalv\review

Run the RewiewApplication.java

It told me there was a error, clicked Proceed anyway.
The error is the MessageService class but it still runs. This might not be the
case for you. I don't get that error in IntelliJ, but like I said it still runs.

Window Defender Firewall blocked it, clicked allow access.

To go to the app, open browser and go to localhost:8080/login or localhost:8080
(it'll take you to the login page either way if you are not logged in). 

This isn't connected to an actual database, so if you restart the server and try the 
same credentails it won't work. 

# How to Making a webpage template

Inside src\main\resources\templates you will see html files. Use test.html as guideline.
You can literally copy and paste this and just edit the body for whatever you want it to be.
Your page needs to be saved inside this folder.

## Changes needed to be made in the backend

So are two things that need to be done in the backend code so this page will actually show up.

1. Make a controller class with an endpoint that will return the page you just made
    - Controllers are placed in the src\main\java\com\antonioalv\review\controller folder
    - Best convention is you name a controller after the page you made (i.e. sun.html -> SunController.java)
    - Then just mimic the TestController class. You want the same imports, annotations, and a method that returns the name of your page as a string.
    - Inside the @RequestMapping change the string to "/" + the name of your page
    - This way when you go to localhost:8080/pagename it will get that page
2. Update the SecurityConfig class
    - This class is inside src\main\java\com\antonioalv\review\config
    - at line 28 add your the endpoint to page
    - if you don't do this step then you simply need to login everytime to get to your page

## Writing tests

- Just mimic what I did with the tests that are already there. Make a test class page and make a class for the page itself.
- Inside the beforeAll method if you want the tests to be ran using the gui uncomment the code that is commented out, and do the opposite to the code that isn't commented out.
- Running tests should be as simple as right clicking your test class and the option should be there.

### Hit me up on discord, text me, or email me if you need help/have questions.
