About plan-generator
=========================

This is a Java 8 + Spring Boot application, which responsibility is to generate a final repayment schedule for borrowers, by pre-calculating repayment plans throughout the life time of a loan.

Prerequisites
=============

* Java 1.8
* Maven 3.1.1+

Building
========

From IDE (Intellij, Eclipse)
----------------------------

Open as a Maven project and compile.

From Command Line
-----------------

    mvn clean install

Running the Application
======================

Run or debug the app with the ```PlanGeneratorApplication``` main class at the root of your Java package hierarchy

Alternately, you may use either of the following Maven targets to run the application from either the command line or 
your IDE:

    mvn spring-boot:run

or:

    mvn exec:exec
    
Accessing the Application Endpoints
=======================

Swagger API documentation
------------------------
Open a browser and visit:
[http://localhost:8080/](http://localhost:8080/)

Testing Application Endpoints
---------------------------

 Swagger UI.
 [http://localhost:8080/](http://localhost:8080/)
 
 Alternatively, you may use Postman. 
[http://localhost:8080/generate-plan/](http://localhost:8080/generate-plan/)

Spring Boot Configuration Properties
====================================

Please see [here](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html) 
for details.

Conventions
==========

Testing
-------------------------
- unit tests

Logging
----------------------
- Log4j2
