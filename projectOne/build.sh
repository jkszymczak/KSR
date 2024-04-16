#!/usr/bin/env bash

if [ $1 == "r" ] ;then
  mvn clean compile package&&java -jar target/projectOne-*-dependencies.jar -i input -o test2.csv -k 10,20,30 -m Euclidean
elif [ $1 == "s" ] ;then
  mvn clean package&&jshell --class-path target/projectOne-*-dependencies.jar
fi