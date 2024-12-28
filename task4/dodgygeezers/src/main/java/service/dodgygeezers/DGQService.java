package service.dodgygeezers;

import service.core.AbstractQuotationService;
import service.core.ClientInfo;
import service.core.Quotation;

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
		
		//manually putting some hotel for simulation

	    Hotel h1= new Hotel("hotel1","Dublin1",4,100);
	    Hotel h2= new Hotel("hotel2","Dublin2",2,50);
	    Hotel h3= new Hotel("hotel3","Dublin3",3,70);

	    listHotels.add(h1);
	    listHotels.add(h2);
	    listHotels.add(h3);

		// Generate the quotation and send it back
		Quotation res= new Quotation(listhotels, listhotels.peekFirst(), generateReference(PREFIX), "", false, false, true); 
		//set booking true to identify the type of the quotation

		return res;
	}


}
