FROM eclipse-temurin:21-jre-alpine

LABEL authors="Simon"

WORKDIR /app

COPY forum-service-0.0.1-SNAPSHOT.jar ./forum-service.jar

ENV MONGODB_USER=user
ENV MONGODB_PASSWORD=""
ENV MONGODB_BASE=test

CMD ["java", "-jar", "/app/forum-service.jar"]