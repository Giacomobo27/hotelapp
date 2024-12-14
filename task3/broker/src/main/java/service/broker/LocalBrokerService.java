package service.broker;

import java.util.LinkedList;
import java.util.List;

import service.core.ClientInfo;
import service.core.Quotation;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject; 

//just fix the getQuotation and ur done
/**
 * Implementation of the broker service that uses the Service Registry.
 * //gotta change it that it uses RMI registry
 * https://docs.oracle.com/javase/8/docs/api/java/rmi/registry/Registry.html
 * 
 * here are the equivalent function in RMI registry
 * String[] list()
       throws RemoteException,
              AccessException
Returns an array of the names bound in this registry. 
The array will contain a snapshot of the names bound in this 
registry at the time of the given invocation of this method.

Remote lookup(String name)
       throws RemoteException,
              NotBoundException,
              AccessException
Returns the remote reference bound to the specified name in this registry.


 *
 */
public class LocalBrokerService {
	//maybe throws and try catch conflits with eachother
	/* 

	public List<Quotation> getQuotations(ClientInfo info) throws RemoteException {
		List<Quotation> quotations = new LinkedList<Quotation>();
		Registry registry = null;  //task f
		//try{
        registry = LocateRegistry.getRegistry( 1099); 

		String[] lista=registry.list(); 
		//so for every registered service(auldfellas,girlallowed,dodgy) 
		//for every one of the i call generate Quotation and i add them to my list that i am gonna return
		
		for (String name : lista) {
			System.out.println("servizio in rm:"+name);
			if (name.startsWith("qs-")) { //quotation services, should work because CONStant are defined like that
				try{
				QuotationService service = (QuotationService)registry.lookup(name); 
				quotations.add(service.generateQuotation(info));  //the getQuotation calls generateQuotation, therefore client just need to call getQuotation 
			}catch (Exception e){System.out.println("Trouble: " + e); } 
			}
		}
		return quotations;
	}
		*/
}
