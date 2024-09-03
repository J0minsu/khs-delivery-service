FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar
ENTRYPOINT [
            "java",
            "-Dspring.profiles.active=prod",
            "-DGEMINI=${{ secrets.GEMINI }}",
            "-DPOSTGRES_URL=${{ secrets.POSTGRES_URL }}",
            "-DPOSTGRES_USERNAME=${{ secrets.POSTGRES_USERNAME }}",
            "-DPOSTGRES_PASSWORD=${{ secrets.POSTGRES_PASSWORD }}",
            "-DREDIS_URL=${{ secrets.REDIS_URL }}",
            "-DREDIS_PORT=${{ secrets.REDIS_PORT }}",
            "-DREDIS_USERNAME=${{ secrets.REDIS_USERNAME }}",
            "-DREDIS_PASSWORD=${{ secrets.REDIS_PASSWORD }}",
            "-DJWT_KEY=${{ secrets.JWT_KEY }}",
            "-jar",
            "/app.jar"
            ]
