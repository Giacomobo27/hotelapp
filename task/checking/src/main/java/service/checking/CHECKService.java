package service.checking;

import service.core.MicroService;
import service.core.ClientInfo; //serialized
import service.core.Quotation; //serialized
import service.core.Hotel;

import java.sql.*;
import java.util.LinkedList;

// Do not change class names or prefixes for the moment!
public class CHECKService extends MicroService {

	public static final String PREFIX = "CH";

	@Override
	public Quotation generateQuotation(ClientInfo info) { // Should fetch data from DB

		LinkedList<Hotel> listHotels = new LinkedList<>();

		try (Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/hotelapp", "root", "Hotelapp25@")) {

			// Prepare query to fetch hotels based on location and rating
			String fetchHotelsQuery = "SELECT * FROM hotels WHERE location = ? AND rating >= ? and price <= ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(fetchHotelsQuery)) {
				preparedStatement.setString(1, info.location); // Set the location from ClientInfo
				preparedStatement.setInt(2, info.stars);// Set the minimum rating from ClientInfo
				preparedStatement.setDouble(3,info.budget);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						String name = resultSet.getString("name");
						String location = resultSet.getString("location");
						int rating = resultSet.getInt("rating");
						double price = resultSet.getDouble("price");

						Hotel hotel = new Hotel(name, location, rating, price);
						listHotels.add(hotel);
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}



		// Generate the quotation and send it back
		Quotation res = new Quotation(listHotels, listHotels.peekFirst(), generateReference(PREFIX), "", false, true, false);
		// Set checking true to identify the type of the quotation

		return res;
	}
}
