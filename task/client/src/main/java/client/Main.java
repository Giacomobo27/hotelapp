package client;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.text.NumberFormat;
import okhttp3.*;
import service.core.Application;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.Hotel;
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

		try {
			while (!finished) {

				// ask which action to perform ( cancel, checking, booking, finish)

				ClientInfo info = null;

				while (true) {

					System.out.println("Which action you want to perform?");
					System.out.println("1. Search new hotels");
					System.out.println("2. Booking chosen hotel");
					System.out.println("3. Cancel chosen hotel");
					System.out.println("4. finish");

					String input = scanner.nextLine();

					if (input.equals("1")) {   //search (checking)

						System.out.println("Location?");
						String location = scanner.nextLine();
						System.out.println("Star rating?");
						String in2 = scanner.nextLine();
						System.out.println("Budget per night?");
						String in3 = scanner.nextLine();
						System.out.println("Book in data? (YYYY-MM-DD)");
						String in4 = scanner.nextLine();
						System.out.println("Book out data? (YYYY-MM-DD)");
						String in5 = scanner.nextLine();

						int rating = Integer.parseInt(in2);
						double budget = Double.parseDouble(in3);

						LocalDate bookin = LocalDate.parse(in4);
						LocalDate bookout = LocalDate.parse(in5);

						info = new ClientInfo(location, rating, budget, bookin, bookout, false, true, false);

						System.out.println("Info collected");
						break;
					} else if (input.equals("2")) { //Booking

						System.out.println("Hotel name?");
						String in1 = scanner.nextLine();
						System.out.println("Hotel address?");
						String in2 = scanner.nextLine();
						System.out.println("Hotel rating?");
						String in3 = scanner.nextLine();
						System.out.println("Hotel price?");
						String in4 = scanner.nextLine();

						int stars = Integer.parseInt(in3);
						double price = Double.parseDouble(in4);

						Hotel hotelbook = new Hotel(in1, in2, stars, price);
						System.out.println(hotelbook);


						System.out.println("Book in data? (YYYY-MM-DD)");
						String in5 = scanner.nextLine();
						System.out.println("Book out data? (YYYY-MM-DD)");
						String in6 = scanner.nextLine();
						LocalDate bookin = LocalDate.parse(in5);
						LocalDate bookout = LocalDate.parse(in6);

						info = new ClientInfo(hotelbook, bookin, bookout, false, false, true);

						System.out.println("Info collected");
						break;
					} else if (input.equals("3")) {  //cancel

						System.out.println("Hotel name?");
						String in1 = scanner.nextLine();
						System.out.println("Hotel address?");
						String in2 = scanner.nextLine();
						System.out.println("Hotel rating?");
						String in3 = scanner.nextLine();
						System.out.println("Hotel price?");
						String in4 = scanner.nextLine();

						int stars = Integer.parseInt(in3);
						double price = Double.parseDouble(in4);

						Hotel hotelbook = new Hotel(in1, in2, stars, price);
						System.out.println(hotelbook);


						System.out.println("Book in data? (YYYY-MM-DD)");
						String in5 = scanner.nextLine();
						System.out.println("Book out data? (YYYY-MM-DD)");
						String in6 = scanner.nextLine();
						LocalDate bookin = LocalDate.parse(in5);
						LocalDate bookout = LocalDate.parse(in6);

						info = new ClientInfo(hotelbook, bookin, bookout, true, false, false);

						System.out.println("Info collected");

						break;
					} else if (input.equals("4")) {
						System.out.println("Operation Finished, thank you!");
						finished = true;
						break;
					} else {
						System.out.println("input invalid, retry");
					}
				}

				if (finished) break;

				// Create an ObjectMapper instance to handle JSON serialization
				ObjectMapper objectmapper = new ObjectMapper();
				objectmapper.registerModule(new JavaTimeModule());
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
				String input = scanner.nextLine().trim(); // Removing trailing whitespace
				input = input.toUpperCase(); // Handling case where user enters lowercase y or n
				System.out.println("Input: '" + input + "'"); // Logging User input for debugging
				if (input.equals("Y")) {
					System.out.println("Loading...");
					continue;
				} else if (input.equals("N")) {
					System.out.println("Operation Finished, thank you!");
					finished = true;
					break;
				} else {
					System.out.println("Please enter Y or N");
				}
				if (finished) break;
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
		String typemsg = "Unknown";
		if(info.checking) typemsg = "Search";
		if(info.cancel) typemsg = "Cancellation";
		if(info.booking) typemsg = "Booking";

		System.out.println("|=================================================================================================================|");
		System.out.println("| "+ typemsg+" request");
		System.out.println("|                                     |                                     |                                     |");
		if(info.checking){

			System.out.println(
					"| Location: " + String.format("%1$-29s", info.location) +
							" | Rating: " + String.format("%1$-23s", String.valueOf(info.stars) ) +
							" | Budget: " + String.format("%1$-27s", String.valueOf(info.budget))+" |");
			System.out.println(
					"| Book-in date: " + String.format("%1$-37s", info.bookin.toString()) +
							" | Book-out date: " + String.format("%1$-40s", info.bookout.toString()) +" |");
			System.out.println("|                                     |                                     |                                     |");
			System.out.println("|=================================================================================================================|");

		}
		else{

			System.out.println(
					"| Hotel Name: " + String.format("%1$-29s", info.chosenHotel.name) +
							" | Address: " + String.format("%1$-27s", info.chosenHotel.address) +
							" | rating: " + String.format("%1$-30s", String.valueOf(info.chosenHotel.rating))+" |");
			System.out.println(
					"| price per night: " + String.format("%1$-20s", String.valueOf(info.chosenHotel.price)) +
							" | Book-in date: " + String.format("%1$-27s", info.bookin.toString()) +
							" | Book-out date: " + String.format("%1$-17s", info.bookout.toString())+" |");
			System.out.println("|                                     |                                     |                                     |");
			System.out.println("|=================================================================================================================|");

		}
	}

	//gotta change it
	public static void displayQuotation(Quotation quotation) {

		if(quotation.checking){
			//print list of hotels
			for(Hotel h : quotation.listHotels){

				System.out.println(
						"| Hotel Name: " + String.format("%1$-26s", h.name) +
								" | Rating: " + String.format("%1$-24s", String.valueOf(h.rating)) +
								" | Price Per Night: " + String.format("%1$-28s", String.valueOf(h.price))+" |");
				System.out.println("|=================================================================================================================|");
			}

			System.out.println(
					"| output: " + String.format("%1$-36s", quotation.news) +
							" | Reference: " + String.format("%1$-34s", quotation.reference) +" |");
			System.out.println("|=================================================================================================================|");
		}

		else{ //booking or cancellation
			System.out.println(
					"| output: " + String.format("%1$-36s", quotation.news) +
							" | Reference: " + String.format("%1$-34s", quotation.reference) +" |");
			System.out.println("|=================================================================================================================|");

		}
	}

	//for testing
	public static final ClientInfo[] clients = {
			new ClientInfo("Dublin", 5, 100.00, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 7), false, false, false)
	};

}

