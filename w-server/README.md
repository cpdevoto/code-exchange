# Wantify Server

### Starting Server

In order for the application to work you will need to connect to a database.  The best way to accomplish this is to pull down the ```postgres-schema``` docker image and run it locally:

```
docker pull shrinedevelopment-docker-develop.jfrog.io/postgres-schema:latest

docker run -it --rm -p 5432:5432 -h localhost --name wantify-db shrinedevelopment-docker-develop.jfrog.io/postgres-schema:latest
```

From server directory (where pom.xml lives)

```
mvn compile process-classes ninja:run
```
