import org.junit.Test;
import service.cancel.CANCService;
import service.core.ClientInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import service.core.Quotation;
import static org.junit.Assert.*;
import java.time.LocalDate;
import service.core.Hotel;

public class CancelUnitTest {

    @Test
    public void quotationTest() {
        // The base URL for cancellation, assuming it's the same as booking URL
        String baseURL = "http://localhost:8081/quotations";

        // Creating the ClientInfo object with cancellation flag set to true
        ClientInfo info = new ClientInfo(
                "Dublin",          // location
                5,                 // stars
                100.00,            // budget
                LocalDate.of(2025, 1, 1), // booking in date
                LocalDate.of(2025, 1, 7),  // booking out date
                true,
                false,
                false
        );

        // Set the chosen hotel for cancellation (same hotel)
        Hotel chosenHotel = new Hotel("hotel2", "Dublin", 2, 50);
        info.chosenHotel = chosenHotel;

        // Set cancellation flag to true (indicating cancellation request)
        info.cancel = true;

        // Set up a RestTemplate to call the endpoint
        RestTemplate template = new RestTemplate();

        // Simulate posting the cancellation request
        ResponseEntity<Quotation> response = template.postForEntity(baseURL, info, Quotation.class);

        // Assertions to verify the cancellation process
        assertNotNull("Response should not be null", response);
        assertEquals("HTTP status should be 201 CREATED", 201, response.getStatusCodeValue());

        // Get the quotation body from the response
        Quotation quotation = response.getBody();
        assertNotNull("Quotation object should not be null", quotation);

        // Check if the quotation has cancellation details
//        assertTrue("Quotation should indicate cancellation", quotation.isCancellation());
//        assertEquals("Quotation status message should mention cancellation", "cancellation ecct...", quotation.getNews());

        // Optionally, check if the hotels list is empty for cancellation (if that's the expected behavior)
//        assertTrue("Quotation hotels list should be empty for cancellation", quotation.getHotels().isEmpty());
    }
}
