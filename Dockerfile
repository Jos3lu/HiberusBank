FROM eclipse-temurin:17-jdk-jammy
COPY ./target/HiberusBank-0.0.1.jar ./app/HiberusBank-0.0.1.jar
EXPOSE 8080
CMD ["java","-jar","/app/HiberusBank-0.0.1.jar"]