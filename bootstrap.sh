#!/bin/bash

echo "******************************************"
echo "* [1/3] Compile and package all services *"
echo "******************************************"
echo ""
BASE_DIR="${PWD}"
echo "BASE_DIR = $BASE_DIR"

if ! mvn clean package -DskipTests; then
    echo ""
    echo "ERROR: Maven encountered errors and cannot continue."
    exit 1
fi

echo ""
echo "**********************************************"
echo "* [2/3] Build Docker images for all services *"
echo "**********************************************"

# List of all service directories that need Docker builds
SERVICES=(
  config-service
  discovery-service
  gateway-service
)

for SERVICE in "${SERVICES[@]}"; do
  SERVICE_DIR="${BASE_DIR}/${SERVICE}"
  SCRIPT_PATH="${SERVICE_DIR}/scripts/build-image-docker.sh"

  echo ""
  echo "+------------------------------+"
  echo "| Building: $SERVICE"
  echo "+------------------------------+"

  if [ ! -d "$SERVICE_DIR" ]; then
    echo "❌ ERROR: Directory $SERVICE_DIR does not exist. Skipping..."
    continue
  fi

  if [ ! -f "$SCRIPT_PATH" ]; then
    echo "❌ ERROR: Script $SCRIPT_PATH not found. Skipping..."
    continue
  fi

  echo "▶ Running: $SCRIPT_PATH"
  cd "$SERVICE_DIR"
  chmod +x "$SCRIPT_PATH"
  "$SCRIPT_PATH"
  cd "$BASE_DIR"
done

echo ""
echo "******************************************"
echo "* Docker image build completed! 🎉       *"
echo "******************************************"