#!/usr/bin/env sh

# Establish an SSH tunnel to the database server, binding it to localhost port

echo "[INFO] Starting to migrate the database"

ssh -N -L 5462:wantify-dev.cgcghsuzur6g.us-east-2.rds.amazonaws.com:5432 admin@api.dev.apiv2.wantify.com -i ~/.ssh/shrinedev-aws.pem &

SSH_PID=$!


# Create a temporary directory that we can bind a volume to


docker rmi shrinedevelopment-docker-develop.jfrog.io/migrations-app:latest

docker pull shrinedevelopment-docker-develop.jfrog.io/migrations-app:latest

docker run --env-file migrations.env --net host shrinedevelopment-docker-develop.jfrog.io/migrations-app:latest /bin/bash -c "rake db:create; rake db:migrate" 

# Kill the SSH tunnel to the database server

kill $SSH_PID

echo "[INFO] Finished migrating the database"

