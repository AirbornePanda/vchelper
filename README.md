# Valhalla
[![pipeline status](https://gitlab.com/AirbornePanda/valhalla/badges/master/pipeline.svg)](https://gitlab.com/AirbornePanda/valhalla/commits/master)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=AirbornePanda_vchelper&metric=alert_status)](https://sonarcloud.io/dashboard?id=AirbornePanda_vchelper)

[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=AirbornePanda_vchelper&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=AirbornePanda_vchelper)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=AirbornePanda_vchelper&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=AirbornePanda_vchelper)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=AirbornePanda_vchelper&metric=security_rating)](https://sonarcloud.io/dashboard?id=AirbornePanda_vchelper)

This is a personal project to play around with [Vaadin](https://vaadin.com/) and [Spring Boot](https://spring.io/projects/spring-boot/).

It's a helper app for the mobile game [Valkyrie Connect](https://play.google.com/store/apps/details?id=jp.co.atm.vcon.ww&hl=en).
The information can be obtained either by official [Wiki Valkypedia](http://jam-capture-vcon-ww.ateamid.com/en/) or the unofficial [Fan-Wiki Garm](https://garm.ml/).

All you need to do after cloning is to run `mvn spring-boot:run`.
The application can be reached under http://localhost:8080.

Visit the Administration view of the app http://localhost:8080/adminView to start the import.

**Grabbing from Garm takes some time.**

**Both grabbers aren't finished yet, tho the grabber from garm is more advanced.**

The main focus of the project is to create a tool, to share team and gear compositions with other players.
This functionality can be accessed under http://localhost:8080/teamBuilder.
**This functionality has not been finished yet!**

## Prerequisites
### Nodejs
Have [Node.js](https://nodejs.org/en/) installed. If you haven't or it can't be located, Vaadin will throw an error and abort.
 
Alternatively you can run `mvn com.github.eirslett:frontend-maven-plugin:1.7.6:install-node-and-npm -DnodeVersion="v11.6.0"` beforehand.

### Persistent Database
This tool comes with an integrated in memory H2 database, but those data will be lost on application restart because the database is **not** persistent.
If you have a persistent database you can configure it in the application.properties file (src/main/resources/application.properties). It already includes examples to make the setup easy.

### Static content
The location to put you static resources depends on the way you build this application. 

Static resources for a `.war` should be put into `src/main/webapp/frontend/`. (Like this project does) 
Static resources for a `.jar` should be put into `src/main/resources/META-INF/resources/frontend/`.
See [this blog entry](https://vaadin.com/blog/vaadin-10-and-static-resources) for more information.

## Error Handling
### Wall of errors like 'ERROR in ../target/frontend/generated-flow-imports'

Run `mvn clean install`.
Afterwards you can run `mvn spring-boot:run` and everything should run fine.