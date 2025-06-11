# Hello Service at 1010

A Simple service to display time at server.

# Test
https://ppusso.devoutsys.com:7777/hello/time

# Run
```sh
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

# Jenkins setup
Execute shell:
```sh
docker network create gateway1-network || true
docker rmi hello-svc || true
docker build hello-svc/. -t hello-svc
docker rm -f hello-svc || true
docker run -dp 1010:8080 --name=hello-svc --network=gateway1-network hello-svc
docker image prune -f || true
```
