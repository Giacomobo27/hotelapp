package client;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.NumberFormat;
import okhttp3.*;
import service.core.Application;
import service.core.ClientInfo;
import service.core.Quotation;
import java.util.concurrent.TimeUnit;



public class Main {
	
	 
	public static void main(String[] args) throws Exception{
		 // Create an OkHttpClient instance for sending HTTP requests
		//OkHttpClient client = new OkHttpClient();
		OkHttpClient client = new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(0, 1, TimeUnit.MILLISECONDS)) // Close idle connections quickly
            .build();
		try{

		 for (ClientInfo info : clients) {
			 // Create an ObjectMapper instance to handle JSON serialization
            ObjectMapper objectmapper = new ObjectMapper();

            // Serialize the `ClientInfo` object into a JSON string
            String s = objectmapper.writeValueAsString(info);

           
          //  OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(s, MediaType.parse("application/json"));
// Build the HTTP POST request to send the client info to the broker service
            Request request = new Request.Builder().url("http://localhost:8083/applications").post(body).build();

            // Execute the HTTP request and receive the response
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new IOException("Error:" + response);
            }
            // Extract the response body as a string
            String responseString = response.body().string();
            // Deserialize the JSON response into an Application object
            Application application = objectmapper.readValue(responseString, Application.class);

            displayProfile(application.info);
            for (Quotation quotation : application.quotations) {
                displayQuotation(quotation);
            }

            System.out.println("\n");
        }
	} finally { 
		 client.connectionPool().evictAll(); // Clear the connection pool
            if (client.dispatcher().executorService() != null) {
                client.dispatcher().executorService().shutdownNow(); // Shut down dispatcher threads
            }

	}
}
	
	
	public static void displayProfile(ClientInfo info) {
		System.out.println("|=================================================================================================================|");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println(
				"| Name: " + String.format("%1$-29s", info.name) + 
				" | Gender: " + String.format("%1$-27s", (info.gender==ClientInfo.MALE?"Male":"Female")) +
				" | Age: " + String.format("%1$-30s", info.age)+" |");
		System.out.println(
				"| Weight/Height: " + String.format("%1$-20s", info.weight+"kg/"+info.height+"m") + 
				" | Smoker: " + String.format("%1$-27s", info.smoker?"YES":"NO") +
				" | Medical Problems: " + String.format("%1$-17s", info.medicalIssues?"YES":"NO")+" |");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println("|=================================================================================================================|");
	}

	
	public static void displayQuotation(Quotation quotation) {
		System.out.println(
				"| Company: " + String.format("%1$-26s", quotation.company) + 
				" | Reference: " + String.format("%1$-24s", quotation.reference) +
				" | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.price))+" |");
		System.out.println("|=================================================================================================================|");
	}
	
	
	public static final ClientInfo[] clients = {
		new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false),
		new ClientInfo("Old Geeza", ClientInfo.MALE, 65, 1.6, 100, true, true),
		new ClientInfo("Hannah Montana", ClientInfo.FEMALE, 21, 1.78, 65, false, false),
		new ClientInfo("Rem Collier", ClientInfo.MALE, 49, 1.8, 120, false, true),
		new ClientInfo("Jim Quinn", ClientInfo.MALE, 55, 1.9, 75, true, false),
		new ClientInfo("Donald Duck", ClientInfo.MALE, 35, 0.45, 1.6, false, false)
	};
	
}
