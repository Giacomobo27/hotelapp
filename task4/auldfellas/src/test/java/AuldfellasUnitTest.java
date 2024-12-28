import org.junit.Test;
import service.auldfellas.AFQService;
import service.core.ClientInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import service.core.Quotation;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class AuldfellasUnitTest {

    @Test
    public void quotationTest() {
        String baseURL = "http://localhost:8080/quotations";
        ClientInfo info = new ClientInfo(
                "Dublin",          // location
                5,                 // stars
                100.00,            // budget
                LocalDate.of(2025, 1, 1), // bookin
                LocalDate.of(2025, 1, 7)  // bookout
        );
        RestTemplate template = new RestTemplate();
        ResponseEntity<Quotation> response = template.postForEntity(baseURL, info, Quotation.class);

        // Assertions to verify the response
        assertNotNull("Response should not be null", response);
        assertEquals("HTTP status should be 201 CREATED", 201, response.getStatusCodeValue());
        Quotation quotation = response.getBody();
        assertNotNull("Quotation object should not be null", quotation);
//        assertNotNull("Quotation reference should not be null", quotation.getReference());
//        assertTrue("Quotation should have a list of hotels", quotation.getHotels().size() > 0);
    }
}