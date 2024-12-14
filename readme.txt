task1 
go inside core project and
mvn clean install

task2
in core project
mvn clean install 
in auldfellas project
mvn clean install
mvn mvn spring-boot:run

MUST follow these steps to not get error supertype

task3
do the same for dodgygeezers and girlsalloed but each in a different terminal

check result in
localhost:8080/quotations
localhost:8081/quotations
localhost:8082/quotations

task4
in core
mvn clean install
then

i run every Quotation service and broker in a different terminal,
 then in a new terminal
in the core,
mvn clean install 
in the client
mvn clean install
mvn exec:java


broker in localhost:8083/applications


if something doesnt work, try clean install core first