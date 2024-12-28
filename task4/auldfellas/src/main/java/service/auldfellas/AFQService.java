package service.auldfellas;

import service.core.AbstractQuotationService;
import service.core.ClientInfo; //serialized
import service.core.Quotation; //serialized
import java.util.LinkedList;
import service.core.Hotel;

/**
 * Auldfellas is CHECK AVAILABILITY
 * endpoint on 8080/quotations
 * 
 *need to do:
 1. connect with database
 2. get list of hotels from db
 3. create Quotation and return 
 */

 //do not change class names of prefix for the momennt!
public class AFQService extends AbstractQuotationService {

	public static final String PREFIX = "AF";
	
	@Override
	public Quotation generateQuotation(ClientInfo info) {  // should be fetching data from DB
		
	    LinkedList<Hotel> listHotels= new LinkedList<>();
		
       //analize Clientinfo request and fill listhotel by fetching from DB
       
	   //manually putting some hotel for simulation

	    Hotel h1= new Hotel("hotel1","Dublin",5,100);
	    Hotel h2= new Hotel("hotel2","Dublin",5,50);
	    Hotel h3= new Hotel("hotel3","Dublin",5,70);

	    listHotels.add(h1);
	    listHotels.add(h2);
	    listHotels.add(h3);

		// Generate the quotation and send it back
		Quotation res= new Quotation(listHotels, listHotels.peekFirst(), generateReference(PREFIX), "", false, true, false);
		//set checking true to identify the type of the quotation

		return res;
	}
}
