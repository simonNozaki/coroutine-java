#!/bin/bash

# Compile sources and compress classes to war
javac -sourcepath src -classpath lib/* -d WebContent/WEB-INF/classes src/main/java/app/server/ServletController.java
jar cvf servlet.war -C WebContent .

# build the container
docker build -t javaee-servlet:1.0.0 .
docker run --name javaee-servlet -d -p 8080:8080 -p 8009:8009 -p 80:80 -p 8111:8111 javaee-servlet:1.0.0

docker ps
