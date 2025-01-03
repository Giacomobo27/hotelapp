# Hotel Booking Application

This project is a microservice-based hotel booking application built using **Spring Boot** and **REST APIs**. It provides functionality for checking availability, booking hotels, canceling reservations, and a broker service for managing workflows. The application also includes a client for interaction.

---

## **Prerequisites**

Ensure you have the following installed on your system:

- **Java** (version 8 or above)
- **Maven** (latest version recommended)
- A terminal or shell for running commands

---

## **Steps to Run the Application**

### **1. Build the Core Module**

The core module is a shared dependency for all services. Start by building it:
```bash
cd core
mvn clean install
 ```


### **2. Start Each Microservice**

Each microservice (checking, booking, and cancel) must be started in its own terminal. Follow these steps for each service:

Open a terminal and navigate to the respective microservice directory:

checking

booking

cancel

Build the microservice:

```bash
mvn clean install
 ```
If you want to skip the tests:

```bash
mvn clean install -DskipTests
 ```
Run the microservice:

```bash
mvn spring-boot:run
 ```
### **3. Start the Broker**

The broker service manages communication between the client and microservices. To start it:

Open a new terminal and navigate to the broker directory.

Build the broker:

```bash
mvn clean install
 ```
 Run the broker:

 ```bash
 mvn spring-boot:run
 ```
 To test the broker service, execute:

  ```bash
  mvn test -Dtest=BrokerUnitTest

   ```

### **4. Start the Client**
To interact with the system, follow these steps for the client service:

Open a new terminal and navigate to the client directory.

Build the client:

```bash
mvn clean install
 ```
Run the client:

 ```bash
 mvn exec:java
 ```

 ### **Endpoints**
 You can access the following endpoints once the application is running:

 Quotations Services:

http://localhost:8080/quotations

http://localhost:8081/quotations

http://localhost:8082/quotations

Broker Service:

http://localhost:8083/applications
