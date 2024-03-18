# Spring Course Selling Platform 
This project is a Spring-based application designed for selling courses online. It utilizes the Spring Boot framework along with various technologies such as PostgreSQL for database management, unit testing for ensuring code quality, and RESTful API for seamless communication between different components.

## Features
- Course Management: Allows administrators to create, update, and delete courses.
- User Authentication: Provides secure authentication mechanisms for users.
- Enrollment: Enables users to enroll in courses they are interested in.
- Payment Integration: Integrates payment gateways for course purchases.
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
- Clone the Repository: git clone https://github.com/yourusername/spring-course-selling.git
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

License
This project is licensed under the MIT License.

