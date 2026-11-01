# springboot-buildpacks-example

[![Java CI with Maven](https://github.com/hendisantika/springboot-buildpacks-example/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/springboot-buildpacks-example/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.x-brightgreen?logo=springboot)
![Buildpacks](https://img.shields.io/badge/Cloud%20Native-Buildpacks-blue?logo=docker)

A minimal Spring Boot application that shows how to build an OCI / Docker image **without a Dockerfile**,
using [Cloud Native Buildpacks](https://buildpacks.io/) through the Spring Boot Maven plugin.

## What are Cloud Native Buildpacks?

From https://buildpacks.io/:

> The Cloud Native Buildpacks project was initiated by Pivotal and Heroku in January 2018 and joined the Cloud Native
> Sandbox in October 2018. The project aims to unify the buildpack ecosystems with a platform-to-buildpack contract that
> is well-defined and that incorporates learnings from maintaining production-grade buildpacks for years at both Pivotal
> and Heroku.

Buildpacks provide a higher-level abstraction for building applications compared to Dockerfiles. The job of a buildpack
is to detect and gather everything your application needs to build and run.

Long story short, a buildpack will transform your source code into a runnable application image!

Spring Boot has built-in support for building images with Cloud Native Buildpacks in:

1. [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/maven-plugin/build-image.html) (via the `spring-boot:build-image` goal)
2. [Spring Boot Gradle plugin](https://docs.spring.io/spring-boot/gradle-plugin/packaging-oci-image.html) (via the `bootBuildImage` task)

## Tech Stack

| Component          | Version                                                              |
|--------------------|----------------------------------------------------------------------|
| Java (JDK)         | **25**                                                               |
| Spring Boot        | 4.1.x                                                                |
| Build tool         | Maven (wrapper included: `./mvnw`)                                   |
| Image builder      | Paketo Buildpacks (`amazon-corretto`, `ca-certificates`, `java`)     |
| Runtime JVM (image)| Amazon Corretto 25 (`BP_JVM_VERSION=25`)                             |

## Prerequisites

- **JDK 25** (only needed to build/run locally without Docker)
- **Docker** (required for `spring-boot:build-image`)
- Docker Compose (optional, now bundled with Docker as `docker compose`)

Verify:

```shell
java -version
docker -v
docker compose version
```

## Run Locally

```shell
./mvnw spring-boot:run
```

Then open http://localhost:8080.

Run the tests / build the JAR:

```shell
./mvnw clean verify
```

## Build the Image with Buildpacks

Package the application source code into an OCI image using the Spring Boot Maven plugin and buildpacks:

```shell
./mvnw clean spring-boot:build-image
```

The buildpack configuration lives in `pom.xml`:

```xml
<image>
    <buildpacks>
        <buildpack>gcr.io/paketo-buildpacks/amazon-corretto:latest</buildpack>
        <buildpack>gcr.io/paketo-buildpacks/ca-certificates:latest</buildpack>
        <buildpack>gcr.io/paketo-buildpacks/java:latest</buildpack>
    </buildpacks>
    <env>
        <BP_JVM_VERSION>25</BP_JVM_VERSION>
    </env>
    <name>docker.io/hendisantika/${project.artifactId}:${project.version}</name>
</image>
```

When the build succeeds, you should be able to see the image using Docker:

```shell
docker images | grep springboot-buildpacks-example
hendisantika/springboot-buildpacks-example   0.0.3   68f06bfcb9f9   46 years ago   255MB
```

> The "46 years ago" creation date is expected: buildpacks produce reproducible images with a fixed timestamp.

## Run the Application using Docker

A container is a running instance of an image — you can compare a container to an image like how an object is an
instance of a class.

```shell
docker run -d -p 8080:8080 --name springbootcontainer hendisantika/springboot-buildpacks-example:0.0.3
```

Check whether the container is running:

```shell
docker ps
```

Now hit http://localhost:8080 in your favorite browser to see the greeting from the simple Spring MVC REST controller:

```
Spring Build Pack Example. 2026-11-02T06:00:00.000000
```

Also take a look at the Spring Boot Actuator endpoints:

- http://localhost:8080/actuator/info — shows the Java version/vendor the app runs on
- http://localhost:8080/actuator/health

To stop the container:

```shell
docker stop springbootcontainer
```

## Run the Application using Docker Compose

Alternatively, you can run the image using Docker Compose. Check out the `docker-compose.yml` at the root of the
project (adjust the image tag to the version you built).

```shell
docker compose up -d     # start
docker compose ps        # show running containers
docker compose down      # stop
```

## Continuous Integration

GitHub Actions workflow [`.github/workflows/maven.yml`](.github/workflows/maven.yml) runs on every push / pull request
to `master` (and manually via *workflow_dispatch*):

- Builds and tests the project on **JDK 25 (Temurin)** using the Maven wrapper (`./mvnw -B clean verify`)
- Uploads the built JAR as a workflow artifact
- On push to `master`, submits the Maven dependency graph to GitHub to improve Dependabot alerts

## Dependency Updates (Dependabot)

[`.github/dependabot.yml`](.github/dependabot.yml) keeps dependencies up to date automatically:

| Ecosystem      | Schedule                        | Grouping                                                 | Commit prefix   |
|----------------|---------------------------------|----------------------------------------------------------|-----------------|
| Maven          | Daily, 21:00 Asia/Jakarta       | `spring-boot`, `maven-plugins`, `minor-and-patch`        | `build(deps)`   |
| GitHub Actions | Weekly (Monday), 21:00 Asia/Jakarta | all actions in one PR (`github-actions`)             | `ci(deps)`      |

Pull requests are labelled `dependencies` plus `java` or `github-actions`.

## Author

**Hendi Santika**

- Email: hendisantika@gmail.com
- Telegram: [@hendisantika34](https://t.me/hendisantika34)
- Link: [s.id/hendisantika](https://s.id/hendisantika)
