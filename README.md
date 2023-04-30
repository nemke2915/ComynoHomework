# ComynoHomework
POC for spring boot app with activeMQ 


Used jdk18.0.2_9 for development,
in memory H2 database with hibernate,
in memory activeMQ by default, can be 
changed to any other activeMQ.


To start application run mvn install
adn then mvn spring-boot:run.

App will start on port 8080.
The database can be accesed at 
http://localhost:8080/db/h2-console for login config 
JDBC url : jdbc:h2:mem:mydb,
driver : org.h2.Driver
username : sa
password : sa
This can all be found in application.properties
and can be configured.
The embeded active is also configured there 
and can be disabled and changed to any other activeMQ.
