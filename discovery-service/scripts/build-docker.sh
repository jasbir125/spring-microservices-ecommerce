#!/bin/bash

BASE_DIR="${PWD}"
echo "BASE_DIR = "${BASE_DIR}
cd ..
mvn clean install

echo ""
echo "+--------------------------------------+"
echo "| Discovery Service - build Docker image |"
echo "+--------------------------------------+"
echo ""
docker build -t jasbir008/discovery-service:latest  .