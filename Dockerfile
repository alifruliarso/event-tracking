FROM eclipse-temurin:17-jdk-jammy@sha256:34161363f20bc85a98d230f41126b75ac40935580378c8d9ca043ec7a96f23da

WORKDIR /app
 
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
 
COPY src ./src
 
CMD ["./mvnw", "spring-boot:run"]