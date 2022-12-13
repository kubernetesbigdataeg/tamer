# Tamer Application

This project uses Quarkus and Reactjs as core technologies.

<p align="center">
<img  width="80%" src="images/tamer-screenshot.png">
</p>

## Quarkus

Quarkus rests on a vast ecosystem of technologies, standards, libraries, and APIs.
Among the specifications and technologies underlying Quarkus are:

| Technology                | Description                                                                                                                                                                                                                                                                                                                                                                                                       |
|---------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Eclipse MicroProfile      | Quarkus integrates the entire set of specifications of Eclipse MicroProfile through SmallRye. Eclipse MicroProfile is specified under the Eclipse Foundation and is implemented by SmallRye                                                                                                                                                                                                                       |
| SmallRye                  | The reference implementacion of Eclipse Microprofile specification                                                                                                                                                                                                                                                                                                                                                |
| CDI                       | Context and Dependency Injection (CDI) is a central technology in Jakarta EE or in MicroProfile. Quarkus uses CDI extensively. However, it is not a full CDI implementation verified by the TCK (Eclipse MicroProfile Technology Compatibility Kits). CDI being runtime based and not compile time based, Quarkus decided to only implement the most useful CDI features that could be generated at compile time. |
| JAX-RS                    | Central technology in Microprofile. Java API for RESTful Web Services (JAX-RS) is a specification that provides support for creating web services according to the Representational State Transfer (REST) architectural style. JAX-RS provides a set of annotations and classes/interfaces to simplify the development and deployment of REST endpoints.                                                          |
| JSON-B                    | JSON Binding (JSON-B-JSR367 and JSON-B-Spec) is a standard binding layer for converting Java objects to/from JSON documents. It defines a default mapping algorithm for converting existing Java classes to JSON. JSON-B is used to customize the JSON output of RESTful web services.                                                                                                                            |
| JSON-P                    | JSON-P-JSR374 and JSON-P-Spec JSON Processing (JSON-P), is a specification that allows JSON processing in Java. he processing includes mechanisms to parse, generate, transform, and query JSON data. At the same time, it provides a mechanism to produce and consume JSON by streaming in a manner similar to StAX (Streaming API for XML) for XML.                                                             |
| Hibernate                 | Hibernate ORM is a library providing Object/Relational Mapping (ORM) support to applications, libraries, and frameworks. It also provides an implementation of the JPA specification, which is the standard Java specification for ORM.                                                                                                                                                                           |
| JPA                       | Jakarta Persistence (JPA; formerly Java Persistence API) is a Jakarta EE application programming interface specification that describes the management of relational data in enterprise Java applications. The implementation of the JPA standard used in Quarkus is Hibernate.                                                                                                                                   |
| Panache                   | Panache is a Quarkus-specific library that simplifies the development of the Hibernate-based persistence layer.                                                                                                                                                                                                                                                                                                   |
| Agroal                    | It's an advanced datasource connection pool implementation with integration with transaction and security. It’s the choice of the Qarkus platform                                                                                                                                                                                                                                                                 |
| RESTEasy classic/reactive | Quarkus is to unify both imperative (RESTEasy classic) and reactive (RESTEasy reactive) programming models seamlessly. Thanks to its reactive core based on Netty and Eclipse Vert.x, everything in Quarkus is non-blocking.                                                                                                                                                                                      |
| Netty                     | Netty is an asynchronous event-driven framework. That means that every request is handled by an event loop (the IO thread) and then, depending on the destination, it can invoke the imperative code on a worker thread (e.g. servlet, Jax-RS) or the reactive code on an IO thread (reactive route).                                                                                                             |
| Vert.x                    | Vert.x is a toolkit to build reactive applications. The Vert.x ecosystem is enormous. From HTTP and data access abilities to messaging clients via microservice and security facilities. Quarkus is based on Vert.x under the hood                                                                                                                                                                                |
| SmallRye Mutiny | Mutiny is an event-driven reactive programming library. It’s not related to Vert.x. Mutiny is integrated in Quarkus where every reactive API uses Mutiny, and Eclipse Vert.x clients are made available using Mutiny bindings                                                                                                                                                                                                                                                                                                                                                                                                                 |
## Front Local Development

Tamer UI local development steps:

Start the development backend:

```
cd utils
podman-compose -f tamer-compose.yml up
```

Start the development frontend:

```
cd src/main/webapp/
npm install
npm run start:dev
```

## Push image to registry

Manual Docker Hub push: manual-push.sh
Quarkus automatic push and k8s deployment: sh utils/push.sh
