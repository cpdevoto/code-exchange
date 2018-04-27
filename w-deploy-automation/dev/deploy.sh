#!/usr/bin/env sh

echo "Development Deployment started..."

source ./migrations.sh

source ./kubernetes.sh

echo "Development Deployment completed."
