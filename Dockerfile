FROM tomcat:9.0.30-jdk8-openjdk

# Deploy the war
COPY servlet.war  /usr/local/tomcat/webapps

# target uri: http://localhost:8080/servlet/index
EXPOSE 8080 8009