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

### Build the Image for the Spring Boot Application

Now package the application source code into an OCI image using the Spring Boot Maven plugin and buildpacks:

```docker
./mvnw clean spring-boot:build-image
```

When the build succeeds, you should be able to see the image using Docker:
```docker
docker images | grep springboot-buildpacks-example 
springboot-buildpacks-example                                           0.0.1-SNAPSHOT               5021ff471a53        40 years ago        255MB
springboot-buildpacks-example                                           0.0.2                        82f3ad5543aa        40 years ago        255MB
hendisantika/springboot-buildpacks-example                              latest                       68f06bfcb9f9        40 years ago        255MB
```

Run the application using Docker

Now it’s time to start a container via Docker. A container is a running instance of an image. You can compare a container to an image like how an object is an instance of a class.
```docker
docker run -d -p 8080:8080 --name springbootcontainer springboot-buildpacks-example:latest
```

Check whether or not the container is running:
```docker
docker ps
CONTAINER ID        IMAGE                                        COMMAND                  CREATED             STATUS              PORTS                    NAMES
b25506eaed5b        hendisantika/springboot-buildpacks-example   "/cnb/lifecycle/laun…"   3 hours ago         Up 3 hours          0.0.0.0:8080->8080/tcp   springbootcontainer2
```

Now hit localhost:8080 in your favorite browser to see the greeting from the simple Spring MVC Rest controller. Excellent, the application is up and running in the container!


Also take a look at the Spring Boot info actuator endpoint: http://localhost:8080/actuator/info

To stop the container run:
```docker
docker stop springbootcontainer
```
Run the application using Docker Compose

Alternatively, you can also run the image using Docker compose.
Check out the docker-compose.yml at the root of the project.

Start:
```docker
docker-compose up -d
```

Show running containers (started using Docker Compose):
```docker
docker-compose ps
```

Stop:
```docker
docker-compose down
```
