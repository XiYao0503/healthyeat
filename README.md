# healthyeat
HealthyEats mobile app

Main Features and Workflow:
Welcome (activity_main.xml)
	Display the logo of our app. User can get to register or login page from here.
Register (register.xml register2.xml)
	User can use register pages to create a new account. They need to input some basic information like username, email, password, gender, birthday, height, current weight, target weight. Our app will compute how much calories the user need to get base on the information they provide.
Login (activity_login.xml)
	User already with the account can get access to the app by inputting the username and password.After the user logged in, he will get into the main page which records what he has eaten in the current day.
Edit the profile (profile.xml)
	User can change the current weight, current height and goal weight in this page. The app will recalculate the goal calories according to the new information from the user. Besides, users can also change the profile image if they want.
Add records of  the food (user_input.xml)
	Here are two ways to add records of the food:
Add by using camera:
     	User can use the camera of the mobile to take the picture of the food they eat and the API will detect all the food in the picture. Here we use the Amazon Rekognition API.https://console.aws.amazon.com/rekognition/home?region=us-east-1#/label-detection
Add by typing
           User can input the name of the food and the app will recommend the list of the food for the user to select. The app will also offer a link of the detailed information of the food. User can select the size of the food on this page directly.
     Both the approaches will offer a list of food the user might have eaten. Users need to choose the right food and the serving size of the food.The app will calculate the current calories and update the records shown in the main page.
Food information (food_info.xml)
	Show detailed information about the food. We use Edamam’s Nutrition Analysis API(https://developer.edamam.com/edamam-nutrition-api) to get the detailed nutrition information of food.
User can choose the serving size and number of servings here.
Home Page (home.xml)
	A simple equation shows the user how much calories they need per day and how much they have got.
	A list of food  the user have eaten today, where they can review or delete them.

