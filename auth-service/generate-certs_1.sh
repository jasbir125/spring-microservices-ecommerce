#!/bin/bash
set -e

BASE_DIR=src/main/resources/certs
SERVER_DIR=$BASE_DIR/server
CLIENT_DIR=$BASE_DIR/client

# Clean existing certs and create folders
rm -rf "$BASE_DIR"
mkdir -p "$SERVER_DIR"
mkdir -p "$CLIENT_DIR"

echo "🔐 Generating server certificate and keystore..."

# Server private key and certificate
openssl req -x509 -newkey rsa:2048 -nodes \
  -keyout "$SERVER_DIR/server.key" \
  -out "$SERVER_DIR/server.crt" \
  -subj "/CN=localhost" -days 365

# Create PKCS12 keystore from server cert and key
openssl pkcs12 -export \
  -in "$SERVER_DIR/server.crt" \
  -inkey "$SERVER_DIR/server.key" \
  -out "$SERVER_DIR/keystore.p12" \
  -name server \
  -password pass:password

echo "✅ Server keystore created at $SERVER_DIR/keystore.p12"

echo "🔐 Generating client certificate..."

# Client private key and certificate
openssl req -x509 -newkey rsa:2048 -nodes \
  -keyout "$CLIENT_DIR/client.key" \
  -out "$CLIENT_DIR/client.crt" \
  -subj "/CN=test-client" -days 365

echo "✅ Client certificate created."

echo "🔒 Importing client certificate into server truststore..."

# Import client cert into server truststore
keytool -import -trustcacerts \
  -alias client-cert \
  -file "$CLIENT_DIR/client.crt" \
  -keystore "$SERVER_DIR/truststore.p12" \
  -storetype PKCS12 \
  -storepass password \
  -noprompt

echo "✅ Truststore created at $SERVER_DIR/truststore.p12"

echo "🎉 Certificates and keystores generated successfully!"