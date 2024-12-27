package service.core; 
//aggiungendo questo, so che devo fare import service.core per importare le cose in questi file
import java.io.Serializable;

/**
 * Class to store the quotations returned by the quotation services
 * 
 *
 */
public class Quotation implements Serializable{

	public Quotation(LinkedList<Hotel> listHotels, Hotel chosenHotel, boolean cancel, boolean checking, boolean booking) {
		this.listHotels = listHotels != null ? listHotels : new LinkedList<>();
		this.chosenHotel = chosenHotel;
		this.cancel = cancel;
		this.checking = checking;
		this.booking = booking;
	}
	

	public Quotation() {}  //empty or not?
	

	
	public LinkedList<Hotel> listHotels; //it was company
	public Hotel chosenHotel;  // it was reference
	public boolean cancel = false;
	public boolean checking  = true;
	public boolean boooking = false;
}
