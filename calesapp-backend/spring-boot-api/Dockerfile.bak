FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/calesapp-0.0.1.jar calesapp-0.0.1.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod

CMD ["java", "-jar", "calesapp-0.0.1.jar"]
