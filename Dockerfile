FROM openjdk:11
EXPOSE 8085
ADD target/skill-enhancement-portal-server.jar skill-enhancement-portal-server.jar
ENTRYPOINT ["java","-jar","/skill-enhancement-portal-server.jar"]
