package service.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import service.booking.BOOKService;
import service.core.Quotation;

import service.core.MicroService;
import service.core.ClientInfo; //serialized
import service.core.Quotation; //serialized

@RestController
public class QuotationController {
 private Map<String, Quotation> quotations = new TreeMap<>();  
 //We use a map because it will allow quick access to specific quotations when we implement the /quotations/{id}
//endpoint. Here, the {id} will be the key to retrieving the quotation.
 private BOOKService service = new BOOKService();

 @Value("${server.port}")
 private int port;
 
 private String getHost() {
  try {
  return InetAddress.getLocalHost().getHostAddress() + ":" + port;
  } catch (UnknownHostException e) {
  return "localhost:" + port;
  }
 }




//getQuotations() method is mapped to an HTTP GET request to the /quotations URL for 
//“application/json” content by the @GetMapping
@GetMapping(value="/quotations", produces="application/json")
public ResponseEntity<ArrayList<String>> getQuotations() {
 ArrayList<String> list = new ArrayList<>();
 for (Quotation quotation : quotations.values()) {
 list.add("http:" + getHost()
 + "/quotations/"+quotation.reference);
 }
 return ResponseEntity.status(HttpStatus.OK).body(list);
}
 //This method returns an object of type 
//ResponseEntity. This is a Spring Boot class that can be used to send back a variety of HTTP responses
    

@PostMapping(value="/quotations", consumes="application/json")
public ResponseEntity<Quotation> createQuotation(
 @RequestBody ClientInfo info) {
 Quotation quotation = service.generateQuotation(info);
 quotations.put(quotation.reference, quotation);
 String url = "http://"+getHost()+"/quotations/"
 + quotation.reference;
 return ResponseEntity
 .status(HttpStatus.CREATED)
 .header("Location", url)
 .header("Content-Location", url)
 .body(quotation);
}

   
    @GetMapping(value="/quotations/{id}", produces="application/json")
    public ResponseEntity<Quotation> getQuotation(@PathVariable String id) {
        Quotation quotation = quotations.get(id);
        return quotation == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).body(quotation);
    }




}