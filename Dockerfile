FROM openjdk:19-jdk-slim
COPY "./target/stock-0.0.1-SNAPSHOT.jar" "stock.jar"
EXPOSE 8080
ENTRYPOINT [ "java","-jar","stock.jar" ]