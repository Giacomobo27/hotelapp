package service.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import java.util.HashMap;
import java.util.List;

import javax.xml.ws.Endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import service.core.Quotation;

import service.core.Application;

import service.core.AbstractQuotationService;
import service.core.ClientInfo; //serialized
import service.core.Quotation; //serialized

@RestController
public class BrokerController {

    
 @Value("${server.port}")
 private int port;
 
 private String getHost() {
  try {
  return InetAddress.getLocalHost().getHostAddress() + ":" + port;
  } catch (UnknownHostException e) {
  return "localhost:" + port;
  }
 }



private Map<Integer, Application> applications = new HashMap<>();   //or hashmap?

    // RestTemplate is used to send HTTP requests to quotation services
 private RestTemplate restTemplate = new RestTemplate();


    // List of URLs for quotation services
    private static final String[] SERVICES = {
        "http://localhost:8080/quotations",
        "http://localhost:8081/quotations",
        "http://localhost:8082/quotations"
    };

    /**
     * Endpoint to create a new application.
     * Accepts a ClientInfo object in JSON format from client, sends it to quotation services, and aggregates the results.
     * 
     */

     //POST
    @PostMapping(value = "/applications", consumes = "application/json")
    public ResponseEntity<Application> createApplication(@RequestBody ClientInfo info) {
        Application application = new Application(info);  // Create a new Application object for the client

        // Loop through the list of quotation service URLs
        for (String serviceUrl : SERVICES) {
            try {

                // Send a POST request to the current quotation service with the ClientInfo object
                ResponseEntity<Quotation> response = restTemplate.postForEntity(serviceUrl, info, Quotation.class);
                if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                    application.quotations.add(response.getBody());  // Add the quotation to the application's list, there should be automatic conversion from json to quotation
                }
            } catch (Exception e) {
                System.out.println("Failed to contact service: " + serviceUrl);
            }
        }

        // Store the newly created application in the in-memory map
        applications.put(application.id, application);
        return ResponseEntity.status(HttpStatus.CREATED).body(application);
    }
    
    //GET ALL
    @GetMapping(value = "/applications", produces = "application/json")
    public ResponseEntity<List<Application>> getApplications() {
        List<Application> applicationlist =new ArrayList<>(applications.values());
        
        return ResponseEntity.status(HttpStatus.OK).body(applicationlist);
    } 


     /* Endpoint to retrieve a specific application by its ID.
     * 
     * @param id The ID of the application to retrieve
     */
    //GET SINGOLO
    @GetMapping(value = "/applications/{id}", produces = "application/json")
    public ResponseEntity<Application> getApplication(@PathVariable int id) {
         // Retrieve the application with the specified ID from the map
        Application application = applications.get(id);
        
        return ResponseEntity.status(HttpStatus.OK).body(application);
      
    }




}