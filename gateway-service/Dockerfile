FROM eclipse-temurin:22-jre AS builder
ARG JAR_FILE=build/libs/*.jar
COPY $JAR_FILE gateway-service.jar
RUN java -Djarmode=layertools -jar gateway-service.jar extract

FROM openjdk:22

COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]