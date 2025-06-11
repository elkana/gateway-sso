# vaadin-sso1 at 2601
20250611

A simple working demo to test SSO Login using ROLE.

# Test
- https://ppusso.devsysout.com:7777/vaadin-sso/cashier
- https://ppusso.devsysout.com:7777/vaadin-sso/superuser
- https://ppusso.devsysout.com:7777/vaadin-sso/resource

# Keycloak Setup
1. create REALM: `los-realm`
2. create Realm roles: `cashier` and `superuser`
3. create CLIENT-ID: `cashier-dev2-client`
3a. Valid Redirect Uris: 
    - `https://ppusso.devoutsys.com:7777/*`
    - `http://vaadin-sso:8080/*`
    - `http://localhost:2601/*`
> Dont add another redirect uris may confuse sso client ! i do trial error to set this part.
4. create 2 dummy users:
- elkana with role `superuser`
- natalia with role `cashier`
dont forget set password
 
 Enable realm_access mapping:
 1. create Client Scope: `los-app-dedicated`
 2. set as `Default`
 3. Mappers > Configure a new mapper > choose `User Realm Role`
 3a. Name: `realm_access_mapper`
 3b. Token Claim Name: `realm_access.roles`
 4. back to `Clients` to link it (only if not shown as Default) > `cashier-dev2-client` > Client scopes > Add client scope > pick `los-app-dedicated`

Done


# Prerequisites
keycloak with following config:
- Valid Redirect Uris: http://localhost:8080/*
- Client Scopes: los-app-dedicated (Default). If not exists, refer to Steps ??? make sure realm_access exists.
https://grok.com/chat/0c5b9201-6187-4e98-9a36-62f1a4cbfd58

# Tested
-

Part of Loan Originated System

References :
- keycloak at https://ppusso.devoutsys.com
- https://github.com/elkana/los-ap-service.git
- los.ap.service.url=http://los-ap-service:8080/legacy/los-ap-svc

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
docker rmi vaadin-sso1 || true
# krn ada di subfolder
docker build vaadin-sso1/. -t vaadin-sso1
docker rm -f vaadin-sso1 || true
docker run -dp 2601:8080 --name=vaadin-sso1 --network=gateway1-network vaadin-sso1
docker image prune -f || true
```

