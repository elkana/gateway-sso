# Gateway Samples
20250611

WARNING ! Use for DEVELOPMENT only. For Production use should have own git repository !

# Test
- https://ppusso.devoutsys.com:7777/los-hello/time
- https://ppusso.devoutsys.com:7777/vaadin-ui/
- https://ppusso.devoutsys.com:7777/vaadin-sso/

## Gateway Projects
- `gateway1` (Recommended, network without host) a clean gateway acting a proxy to support secured (oauth2) and unsecured downstream services.

## Downstream Projects
- `vaadin-sso1` a simple vaadin interface with keycloak login and its roles.
- `hello` a simple api that display time.
- `vaadin-ui` a simple vaadin interface without login needed.

# Jenkins Setup
> Be careful when using poll scm. an update for a project may overwrite another project.
> The good thing we stick on A PUBLIC PORT made you disable other project, so it should safe to execute, port conflict otherwise.


Execute shell:



