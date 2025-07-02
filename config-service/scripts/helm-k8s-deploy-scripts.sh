#!/bin/bash
cd ..
# Step 1: Build project
mvn clean install -DskipTests

# Step 2: Build Docker image
docker build -t jasbir008/config-service:latest .

# Step 3: Push to Docker Hub
docker push jasbir008/config-service:latest

# Step 4: Deploy via Helm
helm upgrade --install config-service ./helm-chart/config-service \
  --namespace default \
  --create-namespace \
  --set image.repository=jasbir008/config-service \
  --set image.tag=latest \
  --set image.pullPolicy=Always