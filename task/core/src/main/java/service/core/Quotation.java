package service.core; 
//aggiungendo questo, so che devo fare import service.core per importare le cose in questi file
import java.io.Serializable;
import java.util.LinkedList;

/**
 * Class to store the quotations returned by the quotation services
 * 
 *
 */
public class Quotation implements Serializable{

	public Quotation(LinkedList<Hotel> listHotels, Hotel chosenHotel, String reference, String news, boolean cancel, boolean checking, boolean booking) {
		this.listHotels = listHotels != null ? listHotels : new LinkedList<>(); // does it make sense?
		this.chosenHotel = chosenHotel;
		this.reference = reference;
		this.news = news;
		this.cancel = cancel;
		this.checking = checking;
		this.booking = booking;
	}
	

	public Quotation() {}  //empty or not?
	

	
	public LinkedList<Hotel> listHotels; //it was company
	public Hotel chosenHotel; 
	public String reference;  //id of the quotation, use generateReference for it
	public String news;  //write if booking/cancel is succesfull or not msg to client;
	public boolean cancel = false;
	public boolean checking  = false;
	public boolean booking = false;
}
