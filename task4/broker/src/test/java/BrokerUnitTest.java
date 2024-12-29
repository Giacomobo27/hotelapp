//import org.junit.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//import service.core.ClientInfo;
//import service.core.Quotation;
//import service.core.Application;
//
//import static org.junit.Assert.*;
//import java.time.LocalDate;
//
//public class BrokerUnitTest {
//    @Test
//    public void quotationTest() {
//        // Initialize RestTemplate
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Create ClientInfo object for the test case
//        ClientInfo info = new ClientInfo(
//                "Dublin",
//                5,
//                50.0, // Corrected to double
//                LocalDate.of(2025, 1, 1),
//                LocalDate.of(2025, 1, 7),
//                false,
//                true,
//                false // Flags: cancel=false, checking=true, booking=false
//        );
//
//        // Determine the service URL based on flags
//        String serviceUrl = null;
//        if (info.cancel) {
//            serviceUrl = "http://localhost:8081/quotations";
//        } else if (info.checking) {
//            serviceUrl = "http://localhost:8080/quotations";
//        } else if (info.booking) {
//            serviceUrl = "http://localhost:8082/quotations";
//        }
//
//        // Assert that a valid URL has been selected
//        assertNotNull("Service URL should not be null", serviceUrl);
//
//        try {
//            // Send POST request
//            ResponseEntity<Quotation> response = restTemplate.postForEntity(serviceUrl, info, Quotation.class);
//
//            // Validate response
//            assertNotNull("Response should not be null", response);
//            assertEquals("HTTP status should be 201 CREATED", HttpStatus.CREATED, response.getStatusCode());
//
//            // Validate Quotation object
//            Quotation quotation = response.getBody();
//            assertNotNull("Quotation object should not be null", quotation);
//
//            // Add Quotation to application's list (assume application is set up correctly)
//            Application application = new Application();
//            application.quotations.add(quotation);
//
//        } catch (Exception e) {
//            System.err.println("Failed to contact service: " + serviceUrl + " | Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//
//}
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.Application;
import java.util.ArrayList;


import static org.junit.Assert.*;
import java.time.LocalDate;

public class BrokerUnitTest {

    @Test
    public void quotationTest() {
        // Initialize RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Create ClientInfo object for the test case
        ClientInfo info = new ClientInfo(
                "Dublin",
                5,
                50.0, // Corrected to double
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 7),
                false,
                true,
                false // Flags: cancel=false, checking=true, booking=false
        );

        // Determine the service URL based on flags
        String serviceUrl = null;
        if (info.cancel) {
            serviceUrl = "http://localhost:8081/quotations";
        } else if (info.checking) {
            serviceUrl = "http://localhost:8080/quotations";
        } else if (info.booking) {
            serviceUrl = "http://localhost:8082/quotations";
        }

        // Assert that a valid URL has been selected
        assertNotNull("Service URL should not be null", serviceUrl);

        try {
            // Send POST request to the service
            ResponseEntity<Quotation> response = restTemplate.postForEntity(serviceUrl, info, Quotation.class);

            // Validate response status
            assertNotNull("Response should not be null", response);
            assertEquals("HTTP status should be 201 CREATED", HttpStatus.CREATED, response.getStatusCode());

            // Log the status and response body for debugging purposes
            System.out.println("Response Status: " + response.getStatusCode());
            if (response.getBody() != null) {
                System.out.println("Response Body: " + response.getBody());
            }

            // Validate Quotation object
            Quotation quotation = response.getBody();
            assertNotNull("Quotation object should not be null", quotation);

            // Initialize the Application object with quotations list (ensure it's not null)
            Application application = new Application();
            if (application.quotations == null) {
                application.quotations = new ArrayList<>();  // Initialize the list if it's null
            }
            application.quotations.add(quotation);

        } catch (Exception e) {
            // Log the exception message and stack trace
            System.err.println("Failed to contact service: " + serviceUrl + " | Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

