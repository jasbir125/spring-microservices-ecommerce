#!/bin/bash
set -e

# Directory setup
BASE_DIR=src/main/resources/certs
SERVER_DIR=$BASE_DIR/server
CLIENT_DIR=$BASE_DIR/client

# Clean up and create necessary folders
rm -rf "$BASE_DIR"
mkdir -p "$SERVER_DIR"
mkdir -p "$CLIENT_DIR"

# ==== SERVER CERTS ====
echo "🔐 Generating server certificate and keystore..."

openssl req -x509 -newkey rsa:2048 -nodes \
  -keyout "$SERVER_DIR/server.key" \
  -out "$SERVER_DIR/server.crt" \
  -subj "/CN=localhost" -days 365

openssl pkcs12 -export \
  -in "$SERVER_DIR/server.crt" \
  -inkey "$SERVER_DIR/server.key" \
  -out "$SERVER_DIR/keystore.p12" \
  -name server \
  -password pass:password

echo "✅ Server keystore created at $SERVER_DIR/keystore.p12"

# ==== CLIENT CERTS ====
echo "🔐 Generating client certificate and keystore..."

openssl req -x509 -newkey rsa:2048 -nodes \
  -keyout "$CLIENT_DIR/client.key" \
  -out "$CLIENT_DIR/client.crt" \
  -subj "/CN=test-client" -days 365

openssl pkcs12 -export \
  -in "$CLIENT_DIR/client.crt" \
  -inkey "$CLIENT_DIR/client.key" \
  -out "$CLIENT_DIR/keystore.p12" \
  -name client \
  -password pass:password

echo "✅ Client keystore created at $CLIENT_DIR/keystore.p12"

# ==== TRUSTSTORES ====

echo "🔒 Importing client certificate into server truststore..."
keytool -import -trustcacerts \
  -alias client-cert \
  -file "$CLIENT_DIR/client.crt" \
  -keystore "$SERVER_DIR/truststore.p12" \
  -storetype PKCS12 \
  -storepass password \
  -noprompt

echo "✅ Server truststore created at $SERVER_DIR/truststore.p12"

echo "🔒 Importing server certificate into client truststore..."
keytool -import -trustcacerts \
  -alias server-cert \
  -file "$SERVER_DIR/server.crt" \
  -keystore "$CLIENT_DIR/truststore.p12" \
  -storetype PKCS12 \
  -storepass password \
  -noprompt

echo "✅ Client truststore created at $CLIENT_DIR/truststore.p12"

echo "🎉 Mutual TLS setup complete!"