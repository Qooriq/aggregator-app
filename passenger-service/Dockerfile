FROM openjdk:22 AS builder
ARG JAR_FILE=build/libs/*.jar
COPY $JAR_FILE passenger-service.jar
RUN java -Djarmode=layertools -jar passenger-service.jar extract

FROM openjdk:22

COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]