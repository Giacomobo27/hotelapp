package service.core;

import java.util.Random;
/*
 * every QS must be connected to the database from wich it feds the lists of hotels
 * 
 * auldfellas=check availability
 * 
 * dodgygeezers= booking
 * 
 * girsallowed = cancel
 * 
 * QS they read the client info, analize the DB, and afterwards create the quotation
 * 
 * 
 */

public abstract class AbstractQuotationService {
	private int counter = 1000;
	private Random random = new Random();
	
	public Quotation generateQuotation(ClientInfo info){  //return quotation, ok to be empty
		return null;
	}
	
	protected String generateReference(String prefix) { // useless?
		String ref = prefix;
		int length = 100000;
		while (length > 1000) {
			if (counter / length == 0) ref += "0";
			length = length / 10;
		}
		return ref + counter++;
	}

	protected double generatePrice(double min, int range) { // useless?
		return min + (double) random.nextInt(range);
	}

	public double bmi(double weight, double height) {  //useless?
		return weight / (height*height);
	}
}
