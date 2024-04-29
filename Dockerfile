FROM alpine/git as clone
WORKDIR /app
RUN git clone https://github.com/nikitakebab/backFilippCrypto.git

FROM maven:3.9.6-amazoncorretto-21-al2023 as build
WORKDIR /app
COPY --from=clone /app/crypto /app
RUN mvn package

FROM amazoncorretto:21.0.3-al2023-headless
WORKDIR /app
COPY --from=build /app/target/crypto-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar crypto-0.0.1-SNAPSHOT.jar"]