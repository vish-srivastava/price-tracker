# price-tracker backend app



## Description

This is a Java Spring Boot microservice project that serves as the purpose of price tracking.

## Features

List the key features of your microservice, such as:

- RESTful API endpoints
- Database integration (e.g., JPA, Hibernate)
- Authentication and authorization
- Logging
- Error handling
- Swagger documentation
- Testing (unit tests, integration tests)
- Docker containerization

## Prerequisites

List the software, tools, or dependencies that need to be installed before setting up your project. For example:

- Java 8 or higher
- Maven
- Docker (for containerization)
- Your preferred IDE (e.g., IntelliJ IDEA, Eclipse)

## Installation

1. Clone the repository:

   ```bash
   https://github.com/vish-srivastava/price-tracker.git

2. Resolve maven dependencies : 
   ```bash 
    mvn clean install
   ```

3. Start the Tomcat Server on port 8080 
   ```bash 
       java -jar .\target\tracker-0.0.1-SNAPSHOT.jar
   ```

4. Check Test Coverage using Jacoco : 
   ```bash 
        mvn clean -U test jacoco:report
   ```
   We can find the test report in 
   ```target/site/jacoco/index.html``` 
   Import this in any browser to check overall test coverage

   Test Coverage Screenshot :
   
![Screenshot 2023-08-20 171853](https://github.com/vish-srivastava/price-tracker/assets/24750869/bb7309a5-2e23-4ef1-805e-57c0e29fb931)


## API Documentation

Swagger  : http://localhost:8080/swagger-ui/index.html#/

Sample :
 
![swagger](https://github.com/vish-srivastava/price-tracker/assets/24750869/0701b3c5-2704-479d-9fc9-e590c3990f33)

## HTTP Feign Library

I have used Netlfix's open source Feign Cloud Library to abstract out implementations of :
- Downstraem/External HTTP Calls
- Making it easier yet highly customizable HTTP/HTTPS calls
- Which is highly extensible & configrable
- Easy to implement

## CI/CD : 
   Added ```actions.yml``` for Github actions for running pipeline jobs for build & test
   Added ```Dockerfile``` for deployment server image configuration 

## Architecture of the Application :

![PT rch drawio](https://github.com/vish-srivastava/price-tracker/assets/24750869/1b5e4acd-97d9-49f2-a720-5ad9377a5a57)
