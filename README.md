# Realtime Event Tracking using Spring Boot and GridDB

**Prerequisites**:

- [Java OpenJDK 17](https://bit.ly/openjdk1706)
- [Docker 23.0.1](https://docs.docker.com/engine/install/)

## Technology Stack
Spring Boot, Thymeleaf, Maven\
Database: GridDB 5.1.0


## Run Application
Build the docker image: 
```shell
docker compose build
```

Run the app with Docker Compose:

```shell
docker compose up
```

#### There is fake event producer to simulate the event creation. See [FakeEventProducer.java](src/main/java/com/galapea/techblog/springboot/timeseries/client/FakeEventProducer.java)

### To see all event, open http://localhost:8080/events
### To see chart, open http://localhost:8080/aggregate

### Example of API Payload see [TestAPI.http](TestAPI.http)