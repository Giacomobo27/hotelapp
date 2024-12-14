package client;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

import service.auldfellas.AFQService;
import service.broker.LocalBrokerService;
import service.core.ClientInfo;
import service.core.Quotation;

import java.rmi.server.UnicastRemoteObject; 
import java.rmi.RemoteException; 
import static org.junit.Assert.assertNotNull;


public class Main {
	//This block is executed before any instance of the class is 
	//created and even before the main method is run
	//The static block in the Main class is used to bind services
	// to the registry before the main method is executed.
	/* 
	static Registry registry;
	static {

		   
            // Create instances of your remote services
            BrokerService brokerService = new LocalBrokerService();  
            QuotationService afqService = new DGQService();    
		//	QuotationService dgqService = new DGQService();        


		try {
            // Create (or get) the RMI registry on port 1099
             //registry = LocateRegistry.createRegistry(1099);
			 System.out.println("getting the registry!!!");
			 registry = LocateRegistry.getRegistry( 1099);
			System.out.println("registry created on port 1099");
        } catch (Exception e) {
            System.err.println("Port 1099 is already in use: " + e.getMessage());

            // If port 1099 is in use, fallback to dynamic port
            if (e.getCause() instanceof java.net.BindException) {
                try {
                    registry = LocateRegistry.createRegistry(0); // Use dynamic port allocation
                    System.out.println("Port 1099 is already in use, using dynamic port.");
                } catch (Exception ex) {
                    System.err.println("Error creating registry on dynamic port: " + ex.getMessage());
                    ex.printStackTrace();
                }
            } else {
                System.err.println("Error creating registry on port 1099: " + e.getMessage());
                e.printStackTrace();
            }
        }


        try{
            // making thwm remote objects
            BrokerService rbrokerService = (BrokerService) UnicastRemoteObject.exportObject(brokerService, 0);
            QuotationService rafqService = (QuotationService) UnicastRemoteObject.exportObject(afqService, 0);
		//	QuotationService rdgqService = (QuotationService) UnicastRemoteObject.exportObject(dgqService, 0);
		// Create the services and bind them to the registry.
		//ServiceRegistry.bind(Constants.GIRLS_ALLOWED_SERVICE, new GAQService());
		registry.bind(Constants.AULD_FELLAS_SERVICE, rafqService); 
	//	registry.bind(Constants.DODGY_GEEZERS_SERVICE, rdgqService);
		registry.bind(Constants.BROKER_SERVICE, rbrokerService);
		} catch(Exception e) {
           // System.err.println("Error during RMI binding: " + e.getMessage());
           // e.printStackTrace();
        }
	}
	
	/**
	 * This is the starting point for the application. Here, we must
	 * get a reference to the Broker Service and then invoke the
	 * getQuotations() method on that service.
	 * 
	 * Finally, you should print out all quotations returned
	 * by the service.
	 * 
	 * @param args
	 
	public static void main(String[] args) throws Exception{
		//Registry registry = LocateRegistry.getRegistry("localhost", 1099); 
	try{
		BrokerService rbService = (BrokerService)registry.lookup(Constants.BROKER_SERVICE);
		
		// Create the broker and run the test data
		for (ClientInfo info : clients) {
			System.out.println("displaying clients info!!!!!");
			displayProfile(info);
			
			// Retrieve quotations from the broker and display them...
			System.out.println("displaying quotations!!!!!");
			for(Quotation quotation : rbService.getQuotations(info)) {
				displayQuotation(quotation);
				System.out.println("AOOOOOOO!!!!!");
			}
			
			// Print a couple of lines between each client
			System.out.println("\n");
		}
	} catch (Exception e) {
		System.err.println("Error looking up the broker service: " + e.getMessage());
		e.printStackTrace();

	}
	}
	
	
	public static void displayProfile(ClientInfo info) {
		System.out.println("|=================================================================================================================|");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println(
				"| Name: " + String.format("%1$-29s", info.name) + 
				" | Gender: " + String.format("%1$-27s", (info.gender==ClientInfo.MALE?"Male":"Female")) +
				" | Age: " + String.format("%1$-30s", info.age)+" |");
		System.out.println(
				"| Weight/Height: " + String.format("%1$-20s", info.weight+"kg/"+info.height+"m") + 
				" | Smoker: " + String.format("%1$-27s", info.smoker?"YES":"NO") +
				" | Medical Problems: " + String.format("%1$-17s", info.medicalIssues?"YES":"NO")+" |");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println("|=================================================================================================================|");
	}

	
	public static void displayQuotation(Quotation quotation) {
		System.out.println(
				"| Company: " + String.format("%1$-26s", quotation.company) + 
				" | Reference: " + String.format("%1$-24s", quotation.reference) +
				" | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.price))+" |");
		System.out.println("|=================================================================================================================|");
	}
	
	
	public static final ClientInfo[] clients = {
		new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false),
		new ClientInfo("Old Geeza", ClientInfo.MALE, 65, 1.6, 100, true, true),
		new ClientInfo("Hannah Montana", ClientInfo.FEMALE, 21, 1.78, 65, false, false),
		new ClientInfo("Rem Collier", ClientInfo.MALE, 49, 1.8, 120, false, true),
		new ClientInfo("Jim Quinn", ClientInfo.MALE, 55, 1.9, 75, true, false),
		new ClientInfo("Donald Duck", ClientInfo.MALE, 35, 0.45, 1.6, false, false)
	};
	*/
}
