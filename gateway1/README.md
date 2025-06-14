# Gateway Service #1 7777
20250611


References :
- https://github.com/elkana/los-ap-service.git
- git2

# Test
- https://ppusso.devoutsys.com:7777/hello/time
- https://ppusso.devoutsys.com:7777/vaadin-ui/
- https://ppusso.devoutsys.com:7777/vaadin-sso/


# Run
```sh
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

# Jenkins Setup
Triggers > Poll SCM
```
H/2 * * * *
```

Build Steps > Execute shell:
```sh
docker network create gateway1-network || true
docker rmi gateway1 || true
docker build gateway1/. -t gateway1
docker rm -f gateway1 || true
# need to re-register domain. 8080 is port accessible by gateway container. please test via curl first.
# to replace configuration , use -v /home/adminkc/cert-gateway/gateway-config.yaml:/app/application.yaml ...
docker run -dp 7777:8080 -e JENKINS_URL=http://jenkins:8080 -e VAADIN_SSO_URL=http://vaadin-sso1:8080 -v /home/adminkc/cert-gateway/fullchain2.pem:/app/certs/certssl.pem -v /home/adminkc/cert-gateway/privkey2.pem:/app/certs/keyssl.pem -v /home/adminkc/cert-gateway/gateway-config.yaml:/app/gateway-config.yaml --name=gateway1 --network=gateway1-network gateway1
docker image prune -f || true
```

> PERHATIKAN wajib assign `-e XXX_URL` krn gateway container ga bisa akses external via localhost

> utk nama docker jgn menggunakan _ tapi - krn nama domain ga boleh pakai _

> pay attention to SSL certificate using docker volume


# Keycloak Setup
Add https://ppusso.devoutsys.com:7777/* as valid redirect URIs


---

# Docker Tips
```sh
# assume create new network
$ docker network create los-network
# add existing containers to network bridge
$ docker network connect los-network myapp1 myapp2
# remove network
$ docker network rm los-network
```

### ping from a container
```sh
$ docker exec container1 ping container2
```

# SCG Tips

```yaml
filters:
- StripPrefix=1
- RewritePath=/service/(?<segment>.*), /api/v1/$\{segment}
```
- request: `/gateway/service/resource`
- after strip: `/service/resource`
- after rewrite: `/api/v1/resource`

### sample rewrite uri
```yaml
filters:
- RewritePath=/old/(?<segment>.*), /new/$\{segment}
- StripPrefix=1
```
- Request: `/old/path`
- After RewritePath: `/new/path`
- After StripPrefix(1): `/path`

### rename port
```yaml
routes:
- id: port-change
  predicates:
  - Host=original-domain.com:8080      # Original port
  uri: http://original-domain.com:9090  # New port
```

### rename full url
```yaml
routes:
- id: complex-rewrite
  uri: http://new-domain.com:8081
  predicates:
  - Host=old-domain.com:8080
  - Path=/old-context/**
  filters:
  - RewritePath=/old-context/(?<segment>.*), /new-context/$\{segment}
  - SetRequestHeader=Host, new-domain.com
```
