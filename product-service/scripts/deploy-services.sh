#!/bin/bash

set -e  # Exit script on error

SERVICE_NAME="gateway-service"
CHART_PATH="../helm-chart/gateway-service"

echo "🚀 Deploying $SERVICE_NAME using Helm..."

# Ensure correct context
#kubectl config use-context kind-microservices

# Install or upgrade the Helm release
helm upgrade --install $SERVICE_NAME "$CHART_PATH"
echo "⏳ Waiting for service to be ready..."
sleep 5  # Wait for 10 seconds (adjust if needed)


echo "✅ Print config map values !"
kubectl describe configmap common-config


echo "✅ Deployment complete!"
kubectl get pods
kubectl get svc


#echo "🔄 Port-forwarding $SERVICE_NAME to localhost:8088..."
#kubectl port-forward svc/config-service 8088:8088
#curl --location 'http://localhost:8088'