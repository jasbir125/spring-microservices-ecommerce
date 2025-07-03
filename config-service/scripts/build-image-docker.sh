#!/bin/bash

BASE_DIR="${PWD}"
echo "BASE_DIR = ${BASE_DIR}"

# Build the project (optional here if already built)
cd "${BASE_DIR}/.."
mvn clean install

echo ""
echo "+--------------------------------------+"
echo "| Config Service - build Docker image |"
echo "+--------------------------------------+"
echo ""

# Move into config-service folder (where Dockerfile is)
cd "${BASE_DIR}"

# Ensure Dockerfile exists
if [ ! -f Dockerfile ]; then
  echo "ERROR: Dockerfile not found in $(pwd)"
  exit 1
fi

# Build the Docker image from current directory
docker build -t jasbir008/config-service:latest .