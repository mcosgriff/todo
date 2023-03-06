# Spring Boot "Microservice" Todo Project

This is a todo Java / Gradle / Spring Boot (version 2.7.0) todo application.

## How to Run

This application is packaged as a war which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary. You run
it using the ```java -jar``` command.

* Clone this repository
* Make sure you are using JDK 1.19 and Gradle 7.6.1
* You can build the project and run the tests by running ```./gradlew build```
* Once successfully built, you can run the service by one of these two methods:

```
    ./gradlew build
    java -jar todo-0.0.1-SNAPSHOT.jar
or
    ./gradlew bootRun
```

* Check the stdout or boot_example.log file to make sure no exceptions are thrown

Once the application runs you should see something like this

```
2023-03-05 18:00:19.318  INFO 25660 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 1 endpoint(s) beneath base path '/actuator'
2023-03-05 18:00:19.363  INFO 25660 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2023-03-05 18:00:19.380  INFO 25660 --- [           main] io.cosgriff.todo.TodoApplication         : Started TodoApplication in 4.42 seconds (JVM running for 5.042)
```

## About the Service

The service is just a simple todo REST service. It uses an in-memory database (H2) to store the data. You can
also do with a relational database like MySQL or PostgreSQL. If your database connection properties work, you can call
some REST endpoints defined in ```io.cosgriff.todo.TodoController``` on **port 8080**. (see below)

More interestingly, you can start calling some of the operational endpoints (see full list below) like ```/metrics```
and ```/health``` (these are available on **port 8081**)

You can use this sample service to understand the conventions and configurations that allow you to create a DB-backed
RESTful service. Once you understand and get comfortable with the sample app you can add your own services following the
same patterns as the sample service.

Here is what this little application demonstrates:

* Create a new to-do item with a title and description.
* Retrieve a list of all to-do items.
* Retrieve a specific to-do item by its ID.
* Update the title and description of a to-do item.
* Delete a to-do item by its ID.
* Full integration with the latest **Spring** Framework: inversion of control, dependency injection, etc.
* Packaging as a single war with embedded container (tomcat 8): No need to install a container separately on the host
  just run using the ``java -jar`` command
* Demonstrates how to set up healthcheck, metrics, info, environment, etc. endpoints automatically on a configured port.
  Inject your own health / metrics info with a few lines of code.
* Writing a RESTful service using annotation: supports both XML and JSON request / response; simply use
  desired ``Accept`` header in your request
* Exception mapping from application exceptions to the right HTTP response with exception details in the body
* *Spring Data* Integration with JPA/Hibernate with just a few lines of configuration and familiar annotations.
* Automatic CRUD functionality against the data source using Spring *Repository* pattern
* Demonstrates MockMVC test framework with associated libraries
* All APIs are "self-documented" by Swagger2 using annotations

Here are some endpoints you can call:

### Get information about system health, configurations, etc.

```
http://localhost:8081/env
http://localhost:8081/health
http://localhost:8081/info
http://localhost:8081/metrics
```

### Create a todo resource

```
POST /api/v1/todo
Accept: application/json
Content-Type: application/json

{
  "title": "grocery",
  "description": "The grocery list"
}

RESPONSE: HTTP 200 (Ok)
Location header: http://localhost:8080/api/v1/todo/1
```

### To view Swagger 2 API docs

Run the server and browse to localhost:8080/swagger-ui.html

# About Spring Boot

Spring Boot is an "opinionated" application bootstrapping framework that makes it easy to create new RESTful services (
among other types of applications). It provides many of the usual Spring facilities that can be configured easily
usually without any XML. In addition to easy set up of Spring Controllers, Spring Data, etc. Spring Boot comes with the
Actuator module that gives the application the following endpoints helpful in monitoring and operating the service:

**/metrics** Shows “metrics” information for the current application.

**/health** Shows application health information.

**/info** Displays arbitrary application info.

**/configprops** Displays a collated list of all @ConfigurationProperties.

**/mappings** Displays a collated list of all @RequestMapping paths.

**/beans** Displays a complete list of all the Spring Beans in your application.

**/env** Exposes properties from Spring’s ConfigurableEnvironment.

**/trace** Displays trace information (by default the last few HTTP requests).

### To view your H2 in-memory datbase

The 'test' profile runs on H2 in-memory database. To view and query the database you can browse
to http://localhost:8090/h2-console. Default username is 'sa' with a blank password. Make sure you disable this in your
production profiles. For more, see https://goo.gl/U8m62X