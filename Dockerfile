FROM openjdk:11
EXPOSE 8080
ADD target/skill-enhancement-portal-server.jar skill-enhancement-portal-server.jar
ENTRYPOINT ["java","-jar","/skill-enhancement-portal-server.jar"]
