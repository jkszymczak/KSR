#!/usr/bin/env bash

if [ $1 == "r" ] ;then
  mvn clean package&&java -jar target/projectOne*.jar
elif [ $1 == "s" ] ;then
  mvn clean package&&jshell --class-path target/projectOne*.jar
fi