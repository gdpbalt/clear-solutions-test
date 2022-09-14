# Clear solutions test project

### Project description

This is a test project that propose CRUD operations with users:

1. Endpoint that return list of users. 
You can use the endpoint with parameters `from` and `to`, and get users between those two dates.

    > GET http://localhost:8080/users/by-birthday?from=01.01.2000&to=01.01.2020

Date in format: DD.MM.YYYY

You may use only one, both or not use any parameters. 
If you not use any parameters the list will not be bounded by date.

2. Create new user

   > POST http://localhost:8080/users

3. Update user via set all fields.

    > PUT http://localhost:8080/users/{id}

4. Change one/some fields of user

   > PATCH http://localhost:8080/users/{id}

5. Delete user by id

   > DELETE http://localhost:8080/users/{id}

### Features
This project works with base of users, where users have such fields:
- Id (set by application, when user is created)
- Email (required)
- First name (required)
- Last name (required)
- Birth date (required)
- Address (optional)
- Phone number (optional)

You can via endpoints control users in database. 
After the application is started it already has three test users.
The application don't use any database so users are not saved between starts the application.

### Project architecture
1. Controllers - Presentation layer
2. Services - Application layer
3. DAO - Data access layer

### Technologies used in project
- Spring Boot 2.7.3
- Java JDK v.17
- Apache Maven v.3.8
- Lombok

### For launch project
1. Computer must have working Git, Maven and Java JDK. See versions above.

2. Clone this project to you computer
   
   > git clone git@github.com:gdpbalt/clear-solutions-test.git 

3. Run next command in terminal from project directory:

   > mvn clean package

4. Run next command in terminal from the project directory:

   > java -jar target/clear-solutions-test-0.0.1-SNAPSHOT.jar

5. Open your browser on http://localhost:8080/users/by-birthday
