#!/usr/bin/env sh

echo "-----------------------------------------------------------------------------------" 
echo "Deploying the Kubernetes proxy endpoint for the PostgreSQL Database"
echo "-----------------------------------------------------------------------------------" 

kubectl apply -f postgres.yaml
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi

echo "-----------------------------------------------------------------------------------" 
echo "The Kubernetes proxy endpoint for the PostgreSQL Database was successfully deployed"
echo "-----------------------------------------------------------------------------------" 
echo
echo "-----------------------------------------------------------------------------------" 
echo "Deploying the Wantify Server"
echo "-----------------------------------------------------------------------------------" 

java -Dartifactory.key=${ARTIFACTORY_KEY} -Dartifactory.image=wantify-server -jar artifact-version-sleuth.jar
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi

kubectl apply -f wantify-server.yaml
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi

echo "-----------------------------------------------------------------------------------" 
echo "The Wantify Server was successfully deployed"
echo "-----------------------------------------------------------------------------------" 
echo
echo "-----------------------------------------------------------------------------------" 
echo "Deploying the Wantify Keycloak Server"
echo "-----------------------------------------------------------------------------------" 

java -Dartifactory.key=${ARTIFACTORY_KEY} -Dartifactory.image=wantify-keycloak -jar artifact-version-sleuth.jar
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi

# Uncomment the following line the first time you do a deployment
# kubectl apply -f wantify-server.yaml

kubectl apply -f wantify-keycloak.yaml
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi

echo "-----------------------------------------------------------------------------------" 
echo "The Wantify Keycloak Server was successfully deployed"
echo "-----------------------------------------------------------------------------------" 

