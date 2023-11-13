FROM baseIMage

WORKDIR /app

COPY . .

RUN mvn dependency:resolve

EXPOSE 6060

CMD [ "./mvnw", "quarkus" ]