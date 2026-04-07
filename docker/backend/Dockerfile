FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /build

COPY pom.xml .
COPY lis-common/pom.xml lis-common/
COPY lis-gateway/pom.xml lis-gateway/
COPY lis-auth/pom.xml lis-auth/
COPY lis-user/pom.xml lis-user/
COPY lis-specimen/pom.xml lis-specimen/
COPY lis-report/pom.xml lis-report/
COPY lis-equipment/pom.xml lis-equipment/
COPY lis-hl7/pom.xml lis-hl7/
COPY lis-statistics/pom.xml lis-statistics/
COPY lis-ai/pom.xml lis-ai/

RUN apk add --no-cache maven && \
    mvn dependency:go-offline -B

COPY . .

RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone && \
    apk del tzdata

RUN addgroup -S lis && adduser -S lis -G lis

ARG SERVICE_NAME
COPY --from=builder /build/${SERVICE_NAME}/target/*.jar app.jar

RUN chown -R lis:lis /app

USER lis

EXPOSE 8080

ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC -XX:+HeapDumpOnOutOfMemoryError"
ENV SERVICE_NAME=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar"]
