package service.cancel;

import service.core.MicroService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.Hotel;

import java.sql.*;
import java.util.LinkedList;



public class CANCService extends MicroService {
	// All references are to be prefixed with an CA (e.g. CA001000)
	public static final String PREFIX = "CA";

	@Override
	public Quotation generateQuotation(ClientInfo info) {
		LinkedList<Hotel> listHotels = new LinkedList<>();
		Hotel chosenHotel = null;
		boolean cancellationSuccessful = false;

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

					if (info.chosenHotel != null && info.chosenHotel.getName().equals(name)) {
						chosenHotel = hotel;
					}
				}
			}

			// Check if the chosen hotel is booked
			if (chosenHotel != null) {
				String checkBookingQuery = "SELECT booked FROM hotels WHERE name = ?";
				try (PreparedStatement preparedStatement = connection.prepareStatement(checkBookingQuery)) {
					preparedStatement.setString(1, chosenHotel.getName());

					try (ResultSet resultSet = preparedStatement.executeQuery()) {
						if (resultSet.next() && resultSet.getBoolean("booked")) {
							// Update booking status to cancel the booking
							String updateBookingQuery = "UPDATE hotels SET booked = false WHERE name = ?";
							try (PreparedStatement updateStatement = connection.prepareStatement(updateBookingQuery)) {
								updateStatement.setString(1, chosenHotel.getName());
								updateStatement.executeUpdate();
								cancellationSuccessful = true;
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Generate the quotation and send it back
		String news = cancellationSuccessful ? "Cancellation successful." : "Cancellation failed.";
		Quotation res = new Quotation(listHotels, chosenHotel, generateReference(PREFIX), news, true, false, false);

		return res;
	}
}
