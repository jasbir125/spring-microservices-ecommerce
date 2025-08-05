#!/bin/bash
set -e

rm -rf src/main/resources/certs
mkdir -p src/main/resources/certs

# Server private key and certificate
openssl req -x509 -newkey rsa:2048 -nodes -keyout server.key -out server.crt -subj "/CN=localhost" -days 365

# Create PKCS12 keystore from server cert and key
openssl pkcs12 -export -in server.crt -inkey server.key -out src/main/resources/certs/keystore.p12 -name server -password pass:password

# Create a client certificate
openssl req -x509 -newkey rsa:2048 -nodes -keyout client.key -out client.crt -subj "/CN=test-client" -days 365

# Import client cert into truststore
keytool -import -trustcacerts -alias client-cert \
  -file client.crt -keystore src/main/resources/certs/truststore.p12 \
  -storetype PKCS12 -storepass password -noprompt