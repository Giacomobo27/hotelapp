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

import service.auldfellas.AFQService;
import service.core.Quotation;

import service.core.AbstractQuotationService;
import service.core.ClientInfo; //serialized
import service.core.Quotation; //serialized

@RestController
public class QuotationController {
 private Map<String, Quotation> quotations = new TreeMap<>();  
 //We use a map for quick access to specific quotations when we implement the /quotations/{id}
//endpoint.  {id} is the key to retrieve the quotation.
 private AFQService service = new AFQService();

 @Value("${server.port}")
 private int port;
 
 private String getHost() {
  try {
  return InetAddress.getLocalHost().getHostAddress() + ":" + port;
  } catch (UnknownHostException e) {
  return "localhost:" + port;
  }
 }




//This endpoint can be used by a client or service to get a 
//list of all available quotation resources.
//Purpose: Retrieves a list of all quotations as URLs.
@GetMapping(value="/quotations", produces="application/json")
public ResponseEntity<ArrayList<String>> getQuotations() {
 ArrayList<String> list = new ArrayList<>();
 for (Quotation quotation : quotations.values()) {
 list.add("http:" + getHost()
 + "/quotations/"+quotation.reference);
 }
 return ResponseEntity.status(HttpStatus.OK).body(list);
}
 
    
//when broker send clientinfo and expect quotation in return, this method is triggered
// It Creates a new quotation based on the provided ClientInfo
@PostMapping(value="/quotations", consumes="application/json")
public ResponseEntity<Quotation> createQuotation(
 @RequestBody ClientInfo info) {
 Quotation quotation = service.generateQuotation(info);  //here it calls the generatequotation method
 quotations.put(quotation.reference, quotation);
 String url = "http://"+getHost()+"/quotations/"
 + quotation.reference;
 return ResponseEntity
 .status(HttpStatus.CREATED)
 .header("Location", url)
 .header("Content-Location", url)
 .body(quotation);
}
// ok no problem compatibility

   
    @GetMapping(value="/quotations/{id}", produces="application/json")
    public ResponseEntity<Quotation> getQuotation(@PathVariable String id) {
        Quotation quotation = quotations.get(id);
        return quotation == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).body(quotation);
    }




}