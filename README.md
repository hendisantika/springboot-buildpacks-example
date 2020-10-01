# springboot-buildpacks-example
What are Cloud Native Buildpacks?

From https://buildpacks.io/:

>The Cloud Native Buildpacks project was initiated by Pivotal and Heroku in January 2018 and joined the Cloud Native Sandbox in October 2018. The project aims to unify the buildpack ecosystems with a platform-to-buildpack contract that is well-defined and that incorporates learnings from maintaining production-grade buildpacks for years at both Pivotal and Heroku.

Buildpacks provide a higher-level abstraction for building applications compared to Dockerfiles. The job of a buildpack is to detect and gather everything your application needs to build and run.

Long story short, a buildpack will transform your source code into a runnable application image!

Spring Boot 2.3.x introduces the support for building Docker images using Cloud Native Buildpacks has been added to the:
1. Spring Boot Maven plugins (via the spring-boot:build-image goal)
2. Spring Boot Gradle plugin (via the bootBuildImage task)

## Prerequisites

Make sure you have Docker installed. If not, install Docker.
Optionally install Docker Compose as well.

Verify Docker:
```
docker -v
Docker version 19.03.12, build 48a66213fe
```

And optionally Docker Compose:
```docker
docker-compose -v
docker-compose version 1.27.2, build 18f557f9
```