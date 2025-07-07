#!/bin/bash

echo ""
echo "+--------------------------------------+"
echo "| Product Service - build Docker image |"
echo "+--------------------------------------+"
echo ""
docker build -t jasbir008/product-service:latest -f Dockerfile .