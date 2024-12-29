package service.girlsallowed;

import service.core.AbstractQuotationService;
import service.core.ClientInfo;
import service.core.Quotation;
import java.util.LinkedList;
import service.core.Hotel;
import java.util.LinkedList;

/**
 * GA is CANCEL
 * endpoint on 8081/quotations
 * 
 * 
 * need to do:
 * 
 * find the hotel indicated by clientinfo inside the DB
 * 
 * see if hotel is booked
 * 
 * if yes, modify DB, and return success cancelation in quotation.news 
 * 
 * if no, return failure cancellation in quotation.news
 * 
 * return quotation (quotation.listhotel can be empty)
 * 
 * 
 *
 */
public class GAQService extends AbstractQuotationService {
	// All references are to be prefixed with an GA (e.g. GA001000)
	public static final String PREFIX = "GA";
	
	@Override
	public Quotation generateQuotation(ClientInfo info) {
		LinkedList<Hotel> listHotels= new LinkedList<>();

		
		LinkedList<Hotel> listHotels= new LinkedList<>();
		//manually putting some hotel for simulation

	    Hotel h1= new Hotel("hotel1","Dublin1",4,100);
	    Hotel h2= new Hotel("hotel2","Dublin2",2,50);
	    Hotel h3= new Hotel("hotel3","Dublin3",3,70);

	    listHotels.add(h1);
	    listHotels.add(h2);
	    listHotels.add(h3);

		// If hotel not found, set chosenHotel to null in the quotation
		Hotel chosenHotel = listHotels.stream().anyMatch(h -> h.equals(info.chosenHotel)) ? info.chosenHotel : null;

		// Generate the quotation and send it back
		Quotation res= new Quotation(listHotels, chosenHotel, generateReference(PREFIX), "cancellation ecct...", true, false, false);
		//set cancellation true to identify the type of the quotation

		return res;
	}


}
