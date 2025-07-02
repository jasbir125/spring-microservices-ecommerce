#!/bin/bash

# Ensure the script runs in Bash
if [ -z "$BASH_VERSION" ]; then
  echo "Please run this script with bash: bash $0"
  exit 1
fi

# Define services
services=("config-service:8088" "discovery-service:8061" "gateway-service:8060")

echo "🚀 Deploying temporary curl pod..."
kubectl run curlpod \
  --image=curlimages/curl:latest \
  --restart=Never \
  --command -- sleep 3600

echo "⏳ Waiting for pod to be ready..."
kubectl wait --for=condition=Ready pod/curlpod --timeout=30s

echo "📡 Curling services from inside the cluster:"
for service in "${services[@]}"; do
  svc_name="${service%%:*}"
  port="${service##*:}"
  full_url="http://$svc_name.default.svc.cluster.local:$port"

  echo -e "👉 Curling \033[1m$full_url\033[0m"
  response=$(kubectl exec curlpod -- curl -s -w "\n%{http_code}" "$full_url")

  body=$(echo "$response" | sed '$d')        # Everything except last line
  status_code=$(echo "$response" | tail -n1) # Last line

  echo "Status Code: $status_code"
  #echo "Response Body:"
  #echo "$body"
  echo "---------------------------"
done

echo "🧹 Cleaning up..."
kubectl delete pod curlpod --grace-period=0 --force > /dev/null

echo "✅ Done."