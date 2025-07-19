#!/bin/bash

SERVICE_NAME="product-service"
IMAGE_TAG="jasbir008/${SERVICE_NAME}:latest"

echo ""
echo "+--------------------------------------+"
echo "| Building Docker image for: $SERVICE_NAME"
echo "+--------------------------------------+"

# Ensure Dockerfile exists in current directory
if [ ! -f Dockerfile ]; then
  echo "❌ ERROR: Dockerfile not found in $(pwd)"
  exit 1
fi

# Build Docker image
docker build -t "$IMAGE_TAG" .

if [ $? -eq 0 ]; then
  echo "✅ Docker image built successfully: $IMAGE_TAG"
else
  echo "❌ Docker build failed for: $SERVICE_NAME"
  exit 1
fi