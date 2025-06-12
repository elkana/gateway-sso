# Gateway Samples
20250611

WARNING ! Use for DEVELOPMENT only. For Production use should have own git repository !

## Gateway Projects
- `gateway1` (Recommended, dockerized) a clean gateway acting a proxy to support secured (oauth2) and unsecured downstream services.

## Downstream Projects
- `vaadin-sso1` a simple vaadin interface with keycloak login and its roles. https://ppusso.devoutsys.com:7777/vaadin-sso1/
- `hello` a simple api that display time. https://ppusso.devoutsys.com:7777/hello/time
- `vaadin-ui` a simple vaadin interface without login needed. https://ppusso.devoutsys.com:7777/vaadin-ui/

# Jenkins Setup
> Be careful when using poll scm. an update for a project may overwrite another project.

Make sure plugins: `docker`, `maven`, `git`, `pipeline stage view`



