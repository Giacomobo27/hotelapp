package service.dodgygeezers;

import service.core.AbstractQuotationService;
import service.core.ClientInfo;
import service.core.Quotation;
import java.util.LinkedList;
import service.core.Hotel;
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
 * 
 * 
 * 
 * 
 */
public class DGQService extends AbstractQuotationService {
	// All references are to be prefixed with an DG (e.g. DG001000)
	public static final String PREFIX = "DG";

	
	@Override
	public Quotation generateQuotation(ClientInfo info) {

		LinkedList<Hotel> listHotels= new LinkedList<>();
		
		//manually putting some hotel for simulation

	    Hotel h1= new Hotel("hotel1","Dublin",4,100.00);
	    Hotel h2= new Hotel("hotel2","Dublin",2,50.00);
	    Hotel h3= new Hotel("hotel3","Dublin",3,70.00);

	    listHotels.add(h1);
	    listHotels.add(h2);
	    listHotels.add(h3);

		// If hotel not found, set chosenHotel to null in the quotation
		Hotel chosenHotel = listHotels.stream().anyMatch(h -> h.equals(info.chosenHotel)) ? info.chosenHotel : null;


		// Generate the quotation and send it back

		Quotation res= new Quotation(listHotels, chosenHotel, generateReference(PREFIX), "", false, false, true);
		//set booking true to identify the type of the quotation

		return res;
	}


}
