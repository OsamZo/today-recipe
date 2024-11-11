FROM openjdk:17-alpine

WORKDIR /app
COPY build/libs/*.jar app.jar
COPY .env .env

# 쉘을 사용하여 .env 파일의 내용을 읽어 환경 변수로 설정하고 Java 애플리케이션을 실행
ENTRYPOINT ["/bin/sh", "-c", "export $(grep -v '^#' .env | xargs) && java -Dspring.profiles.active=kubernetes -jar /app/app.jar"]
