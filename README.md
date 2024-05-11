# matjarna application
Novas team are working on an E-commerce application, that handles categories and products linked to these categories as a project to demonstrate basic concepts in building full stack apps


## Requirements

For building and running the application you need:

- [JDK 17](https://adoptium.net/temurin/releases/?package=jdk&version=17&os=any)
- Create a mysql DB schema called matjarna. 

**Note: make sure that the username and password are 'root' or else you have to change the application.properties file.**


## Running the application locally
 
To run this application you can follow these few steps:
 
 - After setting the project inside the IDE of your choice.
 - Set two different Maven build configruations.
 - The first one named "install majtarna" with the goal set as "clean install".
 - The second configuration should be "run matjarna" with the goal set as:
    **spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8080"**.
 - Don't forget to set the base directory as the project directory.
 - Run the install matjarna configuration then the run matjarna configuration.
 

## Deployment 



## Authors
-Abdelrahim Abu Alrob





