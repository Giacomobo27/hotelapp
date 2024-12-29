package service.core;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Data Class that contains client information
 */
public class ClientInfo implements Serializable {
	
	public ClientInfo(String location, int stars, double budget, LocalDate bookin, LocalDate bookout, boolean cancel, boolean checking, boolean booking) {
		this.location = location; 
		this.stars = stars;
		this.budget = budget;
		this.bookin = bookin;
		this.bookout = bookout;
		this.cancel = cancel;
		this.checking = checking;
		this.booking = booking;
	}
	
	public ClientInfo() {}


	public String location;
	public int stars;
	public double budget;
    public LocalDate bookin;
	public LocalDate bookout;
	public Hotel chosenHotel; 

	public boolean cancel = false;
	public boolean checking  = false;
	public boolean booking = false;

    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // Format the date
	//String formattedDate = date.format(formatter);




}
