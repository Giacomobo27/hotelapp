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
import java.time.LocalDate;
import java.util.Scanner;


		/*
		 *  Looped 4ever
		 * ask wich action to perform ( cancel, checking, booking, finish)
		 * if checking:...
		 * if booking/cancel:...
		 * create clientinfo and send it
		 * print all results( all list of hotels included too)
		 * if finish, end loop and application
		 */


public class Main {
	
	 
	public static void main(String[] args) throws Exception{
		boolean finished = false;

		System.out.println(" WELCOME");

		 // Create an OkHttpClient instance for sending HTTP requests
		//OkHttpClient client = new OkHttpClient();
		OkHttpClient client = new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(0, 1, TimeUnit.MILLISECONDS)) // Close idle connections quickly
            .build();
			Scanner scanner = new Scanner(System.in);

		try{
		 while(!finished) { // change to while true 

			// ask which action to perform ( cancel, checking, booking, finish)
			
			ClientInfo info;

            while(true){
            System.out.println("Which action you want to perform?");
			System.out.println("1. Search new hotels");
			System.out.println("2. Booking chosen hotel");
			System.out.println("3. Cancel chosen hotel");
			System.out.println("4. finish");
			
			String input= scanner.nextLine();

			if(input.equals("1")) {  // call method that create clientinfo
				//fetch clientinfo (hotel filter) from terminal
				//create clientinfo and send it
				break;
			}

			else if(input.equals("2")) { // call method that create clientinfo
				//fetch chosen hotel from terminal 
		     //fetch bookin-bookout time from terminal
		     //create clientinfo and send it
				break; 
			}

			else if(input.equals("3")) { // call method that create clientinfo 
				//fetch chosen hotel from terminal 
		     //fetch bookin-bookout time from terminal
		     //create clientinfo and send it
				break;
			}

			else if(input.equals("4")) { // end things
				System.out.println("Operation Finished, thank you!");
				finished = true;
				break;
			}
			else{
				System.out.println("input invalid, retry");
			}
		}

		if(finished) break;

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

			System.out.println("continue? (Y/N)");
			String input= scanner.nextLine();
			if(input.equals("Y")){
				System.out.println("Loading...");
				continue;
			}
			else if(input.equals("N")){
				System.out.println("Operation Finished, thank you!");
				finished = true;
				break;
			}
			else{
				System.out.println("Error Input");
			}


        }
	} finally { 
		 client.connectionPool().evictAll(); // Clear the connection pool
            if (client.dispatcher().executorService() != null) {
                client.dispatcher().executorService().shutdownNow(); // Shut down dispatcher threads
            }
		scanner.close();
	}
}
	
	//gotta change it
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

	//gotta change it
	public static void displayQuotation(Quotation quotation) {
		System.out.println(
				"| Company: " + String.format("%1$-26s", quotation.company) + 
				" | Reference: " + String.format("%1$-24s", quotation.reference) +
				" | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.price))+" |");
		System.out.println("|=================================================================================================================|");
	}
	
	//for testing
	public static final ClientInfo[] clients = {
		new ClientInfo("Dublin", 5, 100.00, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 7), false, false, false);
	};
	
}
