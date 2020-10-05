# SOAP Microservices with Spring Boot and Apache CXF
*A docker container created with Spring Boot exposing a SOAP endpoint for a legacy client*

The tech stack for this POC is:
* Spring Boot 2.3.4
* Java 15
* Apache CXF 3.4
* REST Assured 4.3
* Docker
 
## Software requirements
Workstation must be properly configured with next tools:
* Java 15 JDK - https://jdk.java.net/15/
* Maven - https://maven.apache.org/download.cgi

**Optional tools**
* Docker - https://www.docker.com/products/docker-desktop
* Docker hub account with token access enabled



## WSDL and Domain Model
In our example, we are going to work in a fictitious Employee  SOAP service with 2 operations:
* GetEmployeeById
* GetEmployeeByName

WSDL diagram:
![WSDL Diagram](/assets/wsdl-diagram.png)

Domain Model (XSD schema) and main response for the service:
![XSD Schema](/assets/xsd-employeesresponse.png)

![XSD Schema](/assets/xsd-employeebynamerequest.png)

![XSD Schema](/assets/xsd-employeebyidrequest.png)

The wsdl file definition is [here](/src/main/resources/wsdl/EmployeeServices.wsdl)

## Installation

Make sure your computer is properly configured with the tools described in the requirements section.

In order to run the project type in project root folder:
```bash
mvn spring-boot:run
```

## Usage
The list of services is available under:
```html
http://localhost:8081/soap
```

The employee wsdl can be found here:
```html
http://localhost:8081/soap/service/employee?wsdl
```

## Docker Image

A Docker Hub account is required to publish the docker image into the registry. We are going to use the next values for my personal Docker account:

* Account ($ACCOUNT): jpontdia 
* Repository ($REPOSITORY): jpontdia/ws-employee-soapcxf 

Create the jar file of the service with maven:
```bash
mvn install
```

Log in into your Docker Hub account using your access token, 
[more information here](https://docs.docker.com/docker-hub/access-tokens/)

```bash
docker login --username=$ACCOUNT
```

Example:
```bash
C:\workspace\dev\datajdbc\ws-employee-soapcxf>docker login --username=jpontdia
Password:
Login Succeeded
```

Create docker image
```bash
docker build --build-arg JAR_FILE=target/*.jar --tag $REPOSITORY .
```

Example:
```bash
C:\workspace\dev\datajdbc\ws-employee-soapcxf>docker build --build-arg JAR_FILE=target/*.jar --tag jpontdia/ws-employee-soapcxf .

Sending build context to Docker daemon  29.24MB
Step 1/5 : FROM adoptopenjdk/openjdk15:x86_64-alpine-jre-15_36
x86_64-alpine-jre-15_36: Pulling from adoptopenjdk/openjdk15
df20fa9351a1: Pull complete
485bef740c7c: Pull complete
7b26ca94da60: Pull complete
Digest: sha256:a9b20bbb07ce018260effe2113bf2f3074739afde501df615e22745df3b48571
Status: Downloaded newer image for adoptopenjdk/openjdk15:x86_64-alpine-jre-15_36
 ---> edc28ff1032d
Step 2/5 : VOLUME /tmp
 ---> Running in c6ecbb27be28
Removing intermediate container c6ecbb27be28
 ---> b6c2c51f1532
Step 3/5 : ARG JAR_FILE
 ---> Running in 193ef5bb8a6a
Removing intermediate container 193ef5bb8a6a
 ---> c4e12e3e67b2
Step 4/5 : COPY ${JAR_FILE} app.jar
 ---> 7a799bce3230
Step 5/5 : ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
 ---> Running in ff3eb68c9c64
Removing intermediate container ff3eb68c9c64
 ---> c2b63024196e
Successfully built c2b63024196e
Successfully tagged jpontdia/ws-employee-soapcxf:latest
SECURITY WARNING: You are building a Docker image from Windows against a non-Windows Docker host. All files and directories added to build context will have '-rwxr-xr-x' permissions. It is recommended to double check and reset permissions for sensitive files and directories.
```

Verify the image was created correctly by running:
```bash
C:\workspace\dev\datajdbc\ws-employee-soapcxf>docker images

REPOSITORY                     TAG                       IMAGE ID            CREATED             SIZE
jpontdia/ws-employee-soapcxf   latest                    c2b63024196e        19 minutes ago      221MB
adoptopenjdk/openjdk15         x86_64-alpine-jre-15_36   edc28ff1032d        9 days ago          192MB
```

Test image locally
```bash
docker run -p $EXPOSED_PORT:$INTERNAL_APPLICATION_PORT $REPOSITORY
```
Where:
* $EXPOSED_PORT is the port visible for other applications on your machine
* $INTERNAL_APPLICATION_PORT is the port configured in the [application.yml](/src/main/resources/application.yml)

Example:
```bash
C:\workspace\dev\datajdbc\ws-employee-soapcxf>docker run -p 8081:8081 jpontdia/ws-employee-soapcxf

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.3.4.RELEASE)

2020-10-02 22:23:49.563  INFO 1 --- [           main] com.jpworks.datajdbc.MainApplication     : Starting MainApplication v1.0.1-SNAPSHOT on b6e50b2f461b with PID 1 (/app.jar started by root in /)
2020-10-02 22:23:49.572 DEBUG 1 --- [           main] com.jpworks.datajdbc.MainApplication     : Running with Spring Boot v2.3.4.RELEASE, Spring v5.2.9.RELEASE
2020-10-02 22:23:49.573  INFO 1 --- [           main] com.jpworks.datajdbc.MainApplication     : The following profiles are active: local
2020-10-02 22:23:51.163  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8081 (http)
2020-10-02 22:23:51.179  INFO 1 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2020-10-02 22:23:51.179  INFO 1 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.38]
2020-10-02 22:23:51.254  INFO 1 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2020-10-02 22:23:51.255  INFO 1 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1625 ms
2020-10-02 22:23:51.541  INFO 1 --- [           main] o.s.boot.web.servlet.RegistrationBean    : Servlet CXFServlet was not registered (possibly already registered?)
2020-10-02 22:23:51.885  INFO 1 --- [           main] o.a.c.w.s.f.ReflectionServiceFactoryBean : Creating Service {http://service.datajdbc.jpworks.com/}EmployeeEndpointService from class com.jpworks.employee.EmployeeService
2020-10-02 22:23:52.455  INFO 1 --- [           main] org.apache.cxf.endpoint.ServerImpl       : Setting the server's publish address to be /service/employee
2020-10-02 22:23:52.646  INFO 1 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2020-10-02 22:23:52.870  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8081 (http) with context path ''
2020-10-02 22:23:52.889  INFO 1 --- [           main] com.jpworks.datajdbc.MainApplication     : Started MainApplication in 4.018 seconds (JVM running for 4.625)
```

Push image to Docker Hub
```bash
docker push $REPOSITORY
```

Example:
```bash
docker push jpontdia/ws-employee-soapcxf

The push refers to repository [docker.io/jpontdia/ws-employee-soapcxf]
a5988eef4f17: Pushed
8ec501d69d1a: Mounted from adoptopenjdk/openjdk15
41eb7e916580: Mounted from adoptopenjdk/openjdk15
50644c29ef5a: Mounted from jpontdia/ws-employee
latest: digest: sha256:f414594938049532b74fff2bd3c63269c9322de5c83007d8b42a5f46f1b93ec6 size: 1163
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[![License](https://img.shields.io/badge/License-Apache%202.0-yellowgreen.svg)](https://opensource.org/licenses/Apache-2.0)