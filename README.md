# WeatherApp

WeatherApp is a Kotlin-based application that provides weather forecasts using the OpenWeather API. 
The application is built with Spring Boot and uses Redis for caching weather data.

## Features

- Fetch weather forecasts by city name
- Support for different temperature units (Celsius, Fahrenheit and Kelvin)
- Caching of weather data to improve performance
- Error handling for various API response scenarios

## Technologies Used

- Kotlin
- Spring Boot
- Redis
- Maven

## Project Structure

- `src/main/kotlin/com/alexs/weatherapp/api`: Contains the REST API controllers for handling incoming requests.
- `src/main/kotlin/com/alexs/weatherapp/application`: Contains application-level services, queries, and repository interfaces.
- `src/main/kotlin/com/alexs/weatherapp/domain`: Contains domain models and value objects.
- `src/main/kotlin/com/alexs/weatherapp/infrastructure`: Contains infrastructure implementations for caching and external API interactions.

## Getting Started

### Prerequisites

- JDK 11 or higher
- Maven
- Redis server

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/AlessandroSpasiano/weatherapp.git
    cd weatherapp
    ```

2. Build the project:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

### Configuration

Configure the application properties in `src/main/resources/application.yml`:

```yaml
spring:
  redis:
    host: localhost
    port: 6379

openweather:
  apiKey: YOUR_API_KEY
```

### Using Docker for Redis
If you prefer to use Docker for running Redis, you can start a Redis container:

1. Pull the Redis image:
    ```sh
    docker pull redis
    ```
2. Run the Redis container:
    ```sh
    docker run -d -p 6379:6379 --name redis
    ```
This starts a Redis server on your local machine, accessible on port 6379.

### Usage
You can access the weather forecast by sending a GET request to the following endpoint:

```
GET /weather/forecast?city={cityName}&unit={unit}
```
- `city`: The name of the city you want to get the weather for.
- `unit`: The temperature unit (Celsius, Fahrenheit, or Kelvin). Default is Celsius.

### Example Request

```sh
curl -X GET "http://localhost:8080/weather/forecast?city=London&unit=celsius"
```