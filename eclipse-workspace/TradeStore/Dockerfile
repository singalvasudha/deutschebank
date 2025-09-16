# Step 1: Set the base image
FROM openjdk:21-jdk

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the Spring Boot JAR file into the container
COPY target/TradeStore-0.0.1.jar /app/tradestore.jar

# Step 4: Expose the port your application runs on
EXPOSE 8080

# Step 5: Define the command to run your Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/tradestore.jar"]