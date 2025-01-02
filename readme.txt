
go inside core 
mvn clean install

for each microservice ( checking, booking, cancel) in a separate terminal
mvn clean install
(mvn clean install -DskipTests) if u want to skip the tests
mvn spring-boot:run

same for broker in a different terminal

for running broker test 
 mvn test -Dtest=BrokerUnitTest


check result in
localhost:8080/quotations
localhost:8081/quotations
localhost:8082/quotations

broker in localhost:8083/applications


in a new terminal for the client
mvn clean install
mvn exec:java

if something doesnt work, try clean install core first
