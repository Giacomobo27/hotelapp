package service.checking;

import service.core.MicroService;
import service.core.ClientInfo; //serialized
import service.core.Quotation; //serialized
import service.core.Hotel;

import java.sql.*;
import java.util.LinkedList;

/**
 * Auldfellas is CHECK AVAILABILITY
 * endpoint on 8080/quotations
 *
 *need to do:
 1. connect with database
 2.analize Clientinfo request
 3. get list of hotels from DB
 4. create Quotation and return
 */

//do not change class names of prefix for the momennt!
public class CHECKService extends MicroService {

	public static final String PREFIX = "CH";

	@Override
	public Quotation generateQuotation(ClientInfo info) {  // should be fetching data from DB

		LinkedList<Hotel> listHotels = new LinkedList<>();

		try (Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/hotelapp", "root", "Hotelapp25@")) {

			// Fetch all hotels from the database
			String fetchHotelsQuery = "SELECT * FROM hotels";
			try (Statement statement = connection.createStatement();
				 ResultSet resultSet = statement.executeQuery(fetchHotelsQuery)) {

				while (resultSet.next()) {
					String name = resultSet.getString("name");
					String location = resultSet.getString("location");
					int rating = resultSet.getInt("rating");
					double price = resultSet.getDouble("price");

					Hotel hotel = new Hotel(name, location, rating, price);
					listHotels.add(hotel);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
        System.out.println(listHotels.size());
		System.out.println(listHotels);
		// Generate the quotation and send it back
		Quotation res = new Quotation(listHotels, listHotels.peekFirst(), generateReference(PREFIX), "", false, true, false);
		//set checking true to identify the type of the quotation

		return res;
	}
}
