package service.core;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Data Class that contains client information
 * 
 * @author Rem
 *
 */
public class ClientInfo implements Serializable {
	
	public ClientInfo(String location, int stars, double budget, LocalDate bookin, LocalDate bookout) {
		this.location = location; 
		this.stars = stars;
		this.budget = budget;
		this.bookin = bookin;
		this.bookout = bookout;
	}
	
	public ClientInfo() {}

	/**
	 * Public fields are used as modern best practice argues that use of set/get
	 * methods is unnecessary as (1) set/get makes the field mutable anyway, and
	 * (2) set/get introduces additional method calls, which reduces performance.
	 */
	public String location;
	public int stars;
	public double budget;
    LocalDate bookin;
	LocalDate bookout;

	boolean cancel = false;
	boolean checking  = true;
	boolean boooking = false;

    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // Format the date
	//String formattedDate = date.format(formatter);




}
