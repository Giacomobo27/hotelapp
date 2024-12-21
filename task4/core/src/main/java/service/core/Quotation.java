package service.core; 
//aggiungendo questo, so che devo fare import service.core per importare le cose in questi file
import java.io.Serializable;

/**
 * Class to store the quotations returned by the quotation services
 * 
 * @author Rem
 *
 */
public class Quotation implements Serializable{
	public Quotation(String company, String reference, double price) {
		this.company = company;
		this.reference = reference;
		this.price = price;
		
	}

	public Quotation() {}
	
	//public String company; // list of hotel classes
	//public String reference; // chosen hotel classes 
	public double price;  
	boolean cancel = false;
	boolean checking  = true;
	boolean boooking = false;
}
