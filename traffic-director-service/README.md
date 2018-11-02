# Traffic Director Service

Simple Reference Implementation of the Traffic Director API

## Run

In a Terminal
`mvn spring-boot:run`

Prometheus Metrics are here: http://localhost:8080/actuator/prometheus

### Postman script

A simple [Postman](https://www.getpostman.com/apps) script is included.

[postman.json](postman.json)


##

Docker run / build

Automated Docker build via Maven (to ensure current artifacts are used)

Build local image using current Maven version of artifact
```
mvn package dockerfile:build
```

```
mvn dockerfile:push
```

# License

[LICENSE.txt](../LICENSE.txt)
