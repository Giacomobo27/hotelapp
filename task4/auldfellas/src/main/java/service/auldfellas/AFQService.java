package service.auldfellas;

import service.core.AbstractQuotationService;
import service.core.ClientInfo; //serialized
import service.core.Quotation; //serialized

/**
 * Auldfellas is CHECK AVAILABILITY
 * endpoint on 8080/quotations
 * 
 *need to change:
 1. connect with database
 2. get list of hotels from db
 3. create Quotation and return 
 */

 //do not change class names of prefix for the momennt!
public class AFQService extends AbstractQuotationService {
	// All references are to be prefixed with an AF (e.g. AF001000)
	public static final String PREFIX = "AF";
	public static final String COMPANY = "Auld Fellas Ltd.";
	

	@Override
	public Quotation generateQuotation(ClientInfo info) {  // should be fetching data from DB
		// Create an initial quotation between 600 and 1200
		double price = generatePrice(600, 600);
		
		int discount = 0;
		if (info.gender == ClientInfo.MALE) {
			discount = 30;
			if (info.age > 50) discount += 10;
			if (info.age > 60) discount += 10;
		} else {
			discount = -20;
		}

		// Generate the quotation and send it back
		return new Quotation(COMPANY, generateReference(PREFIX), (price * (100-discount)) / 100);
	}
}
