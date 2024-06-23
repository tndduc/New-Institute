# Spring Course Selling Platform 
This project is a Spring-based application designed for selling courses online. It utilizes the Spring Boot framework along with various technologies such as PostgreSQL for database management, unit testing for ensuring code quality, and RESTful API for seamless communication between different components.

## DataBase : 
![DB](https://github.com/tndduc/New-Institute/blob/main/image4github/Untitled.png?raw=true)
## API : 
### 001. Auth
![Auth Controller](https://raw.githubusercontent.com/tndduc/New-Institute/main/image4github/controller/Screenshot%20from%202024-06-23%2009-45-30.png)
### 002. Account
![Auth Controller](https://raw.githubusercontent.com/tndduc/New-Institute/main/image4github/controller/Screenshot%20from%202024-06-23%2009-46-41.png)
### 003. Course
![Auth Controller](https://github.com/tndduc/New-Institute/blob/main/image4github/controller/Screenshot%20from%202024-06-23%2009-48-12.png?raw=true)
### 004. Lesson,005. Unit
![Auth Controller](https://github.com/tndduc/New-Institute/blob/main/image4github/controller/Screenshot%20from%202024-06-23%2009-48-20.png?raw=true)
### 006. Video,007. Quiz
![Auth Controller](https://github.com/tndduc/New-Institute/blob/main/image4github/controller/Screenshot%20from%202024-06-23%2009-48-26.png?raw=true)
### 008. Question,009. Choice
![Auth Controller](https://github.com/tndduc/New-Institute/blob/main/image4github/controller/Screenshot%20from%202024-06-23%2009-48-30.png?raw=true)
### 010. Quiz Results,011. Enrollment,012. Cart
![Auth Controller](https://raw.githubusercontent.com/tndduc/New-Institute/main/image4github/controller/Screenshot%20from%202024-06-23%2009-46-41.png)
### 013. payment,014. Study Progress,015. CertificateController
![Auth Controller](https://github.com/tndduc/New-Institute/blob/main/image4github/controller/Screenshot%20from%202024-06-23%2009-48-50.png?raw=true)


## Features
- Course Management: Allows administrators to create, update, and delete courses.
- User Authentication: Provides secure authentication mechanisms for users.
- Enrollment: Enables users to enroll in courses they are interested in.
- Payment Integration: Integrates payment gateways for course purchases with sandbox VNPay.
- Search Functionality: Allows users to search for courses based on different criteria.
- Unit Testing: Ensures code quality and reliability through comprehensive unit tests.
- RESTful API: Facilitates communication between the frontend and backend components.
## Technologies Used
- Spring Boot: Framework for building enterprise-level applications.
- PostgreSQL: Relational database management system for storing course and user data.
- JUnit: Java testing framework for unit testing.
- RESTful API: Communication protocol for seamless interaction between client and server.
- Docker: Containerization platform for packaging applications and their dependencies.
## Setup Instructions
- Clone the Repository:
```
  git clone https://github.com/tndduc/New-Institute.git
```
- Configure PostgreSQL: Set up a PostgreSQL database and update the application.properties file with your database credentials.
- Build and Run: Use Maven or Gradle to build the project and run the Spring Boot application.
+ For Maven: mvn clean install && mvn spring-boot:run
+ For Gradle: ./gradlew build && ./gradlew bootRun
Access the Application: Open your browser and navigate to http://localhost:8080 to access the application.
API Documentation
The API endpoints documentation can be found by accessing /swagger-ui.html when the application is running locally. It provides detailed information about the available endpoints and their usage.
- Build Docker Image:
```

docker build -t java-spring-boot-newinstitute-network
```
Run Docker Container:
```
docker run -p 8080:8080 java-spring-boot-newinstitute-network
```
## Testing
To run the unit tests, execute the following command:

- For Maven: mvn test
- For Gradle: ./gradlew test
Contributing
Contributions are welcome! If you find any issues or have suggestions for improvements, feel free to open an issue or create a pull request.

##License
- This project is licensed under the MIT License.
- This project make by ductn aka Tran Ngoc Duc aka  🦆🦆🦆 Email : tnd.duc@gmail.com
