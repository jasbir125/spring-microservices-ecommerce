#!/bin/bash

# Set variables
IMAGE_NAME="jasbir008/product-service"
TAG="latest"
cd ..
# Step 1: Clean and package the Spring Boot app (skip tests for faster build)
echo "🔹 Building Spring Boot JAR..."
mvn clean package -DskipTests

# Step 2: Build Docker image
echo "🐳 Building Docker Image..."
docker build -t $IMAGE_NAME:$TAG .

# Step 3: Push Docker image to registry
echo "📤 Pushing Docker Image to Docker Hub..."
docker push $IMAGE_NAME:$TAG

echo "✅ Done! Image pushed: $IMAGE_NAME:$TAG"