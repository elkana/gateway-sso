# Vaadin 2600


# Run
```sh
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

# Jenkins setup
Triggers > Poll SCM
```
H/2 * * * *
```

Build Steps > Execute shell:
```sh
docker network create gateway1-network || true
docker rmi vaadin-ui || true
# krn ada di subfolder
docker build vaadin-ui/. -t vaadin-ui
docker rm -f vaadin-ui || true
docker run -dp 2600:8080 --name=vaadin-ui --network=gateway1-network vaadin-ui
docker image prune -f || true
```

