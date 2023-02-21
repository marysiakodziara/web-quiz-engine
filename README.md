# Web Quiz Engine
Web Quiz Engine is a RESTful web service that allows users to create, solve and delete quizzes. \
The service is built using Spring Boot and provides a user-friendly interface for the creation of quizzes. \
The web service provides endpoints for handling all CRUD (Create, Read, Update, Delete) operations for quizzes. 

## Table of Contents
- [Technologies Used](#Technologies-Used)
- [API Endpoints and Business Logic](#API-Endpoints-and-Business-Logic)
- [Exception Handling](#Exception-Handling)
- [Running the Application](#Running-the-Application)

# Technologies Used

> Web Quiz Engine is built with the following technologies:

- `Java 19` Web Quiz Engine is built with Java 19 programming language.
- `Spring Boot 2.7.8` Spring Boot is used as the application framework.
- `Spring Data JPA` Spring Data JPA is used for data access.
- `Spring Security` Spring Security is used for security and authentication.
- `H2 Database` H2 database is used as the in-memory database.
- `Lombok` Lombok is used for reducing boilerplate code.
- `MapStruct` MapStruct is used for mapping DTOs to entities and vice versa.
- `JUnit 5` JUnit 5 is used for testing.
- `Jacoco` Jacoco is used for code coverage.


# API Endpoints and Business Logic

> Web Quiz Engine provides the following RESTful API endpoints for handling CRUD operations for quizzes:

1. - `GET /api/v1/quizzes/{id}` This endpoint retrieves the quiz with the specified id.

> If the quiz with the specified id is found, the response will include the quiz details. \
> If the quiz with the specified id is not found, a 404 Not Found status code will be returned. 

2. - `GET /api/v1/quizzes` This endpoint retrieves all the quizzes that are available in the application.

> The endpoint supports pagination and returns a Page object containing the list of quizzes. \
> The endpoint takes a query parameter 'page' to specify the page number. \
> The response will include a list of all quizzes available. 

3. - `POST /api/v1/quizzes` This endpoint allows authenticated users to create a new quiz.

> The endpoint takes a QuizPostDTO object in the request body that contains the quiz details. \ 
> If the request is successful, a 200 OK status code will be returned. 

4. - `POST /api/v1/quizzes/{id}/solve` This endpoint allows authenticated users to solve the quiz with the specified id.

> The endpoint takes a PostedAnswer object in the request body that contains the user's answer to the quiz. \
> If the answer is correct, the response will include a success message. \
> If the answer is incorrect, the response will include an error message. 

5. - `DELETE /api/v1/quizzes/{id}` This endpoint allows authenticated users to delete the quiz with the specified id.

> If the quiz is successfully deleted, a 200 OK status code will be returned. \
> If the quiz is not found or the user is not authorized to delete the quiz, a 403 Forbidden status code will be returned. 

6. - `GET /api/v1/quizzes/completed` This endpoint retrieves all the solved quizzes for the authenticated user.

* The endpoint supports pagination and returns a Page object containing the list of solved quizzes. 
* The endpoint takes a query parameter 'page' to specify the page number. 
* The response will include a list of all the solved quizzes. 

# Exception Handling

> Web Quiz Engine provides the following custom exception handling:
