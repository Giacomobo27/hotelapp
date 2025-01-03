package service.booking;

import service.core.MicroService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.Hotel;

import java.sql.*;
import java.util.LinkedList;

/**
 * DGQ is BOOKING
 * endpoint on 8082/quotations
 *
 * need to do:
 *
 * find the hotel indicated by clientinfo inside the DB
 *
 * see if hotel is available within bookin bookout data
 *
 * if yes, modify DB, and return success in quotation.news
 *
 * if no, return failure in quotation.news
 *
 * return quotation (quotation.listhotel can be empty)
 */
public class BOOKService extends MicroService {
	// All references are to be prefixed with an DG (e.g. DG001000)
	public static final String PREFIX = "BK";

	@Override
	public Quotation generateQuotation(ClientInfo info) {
		LinkedList<Hotel> listHotels = new LinkedList<>();
		Hotel chosenHotel = null;
		boolean bookingSuccessful = false;

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

			// Check if the chosen hotel is available
			if (chosenHotel != null) {
				String checkAvailabilityQuery = "SELECT booked FROM hotels WHERE name = ?";
				try (PreparedStatement preparedStatement = connection.prepareStatement(checkAvailabilityQuery)) {
					preparedStatement.setString(1, chosenHotel.getName());

					try (ResultSet resultSet = preparedStatement.executeQuery()) {
						if (resultSet.next() && !resultSet.getBoolean("booked")) {
							// Update booking status to mark the hotel as booked
							String updateBookingQuery = "UPDATE hotels SET booked = true WHERE name = ?";
							try (PreparedStatement updateStatement = connection.prepareStatement(updateBookingQuery)) {
								updateStatement.setString(1, chosenHotel.getName());
								updateStatement.executeUpdate();
								bookingSuccessful = true;
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Generate the quotation and send it back
		String news = bookingSuccessful ? "Booking successful." : "Booking failed.";
		Quotation res = new Quotation(listHotels, chosenHotel, generateReference(PREFIX), news, false, false, true);

		return res;
	}
}
