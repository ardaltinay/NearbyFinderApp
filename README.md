# NearbyFinderApp

NearbyFinderApp is a simple web application that helps users find nearby places with given parameters(latitude, longitude, radius). It utilizes the Google Places API to provide accurate and up-to-date information about nearby places.

## Technologies Used

- Java(JDK17)
- Spring Boot(3.*)
- Spring Data JPA
- Mysql 8.0.*
- Maven 3.*
- Docker
- Unit Test(Junit5+ and Mockito)
- Heroku

## Getting Started on Local Machine

1. Clone this repository.
2. Run docker-compose.yml file with ```docker compose up```
3. Do not forget to set env variables for your local environment inside docker.compose.yml file.(MYSQL_DATABASE, MYSQL_PASSWORD etc.)