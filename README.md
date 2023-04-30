# ComynoHomework
POC spring boot app with rest API and activeMQ 


Used jdk18.0.2_9 for development,
in memory H2 database with hibernate,
in memory activeMQ by default, can be 
changed to any other activeMQ.


To start application run mvn install
and then mvn spring-boot:run.

The database will be populated at start with
3 records. Endpoints for rest api are 
localhost:8080/books
localhost:8080/books/{id}
localhost:8080/books/delete/{id}
These rest calls will triger jms senders/receivers
which will further do basic CRUD operations.

App will start on port 8080.
The database can be accesed at 
http://localhost:8080/db/h2-console for login config 
JDBC url : jdbc:h2:mem:mydb,
driver : org.h2.Driver
username : sa
password : sa
This can all be found in application.properties
and can be configured.
The embeded activeMQ is also configured there 
and can be disabled and changed to any other activeMQ.
