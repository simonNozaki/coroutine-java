#!/bin/bash

docker container stop javaee-servlet
docker rm javaee-servlet
docker rmi javaee-servlet:1.0.0

docker ps -a
