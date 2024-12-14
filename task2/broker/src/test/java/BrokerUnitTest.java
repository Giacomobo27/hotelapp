import java.rmi.registry.*;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException; 


import java.util.LinkedList;
import java.util.List;

import service.broker.LocalBrokerService;
import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Constants;
import service.core.Quotation;
import service.core.QuotationService;
import service.broker.LocalBrokerService;
import org.junit.*;
import static org.junit.Assert.assertNotNull;



public class BrokerUnitTest {

    private static Registry registry;
    //here very similar to associated main.java
    @BeforeClass
    public static void setup() {
            BrokerService LocalService= new LocalBrokerService();
       // which it registers with the registry.
        try {
            //create registry
            registry = LocateRegistry.createRegistry(1099); 

        } catch (Exception e) {

            System.out.println("Trouble: " + e);

            if (e.getCause() instanceof java.net.BindException) {
                System.out.println("Port 1099 is already in use, using dynamic port allocation.");
                try {
                    registry = LocateRegistry.createRegistry(0); // Use dynamic port allocation, gotta change the test to look at the port 0 too
                
                } catch (Exception ex) {
                    System.err.println("Error creating registry on dynamic port: " + ex.getMessage());
                    ex.printStackTrace();
                }
            } else {
                System.err.println("Error creating registry on port 1099: " + e.getMessage());
                e.printStackTrace();
            }
        }

        try {

           
           BrokerService RemoteBrokerService = (BrokerService)UnicastRemoteObject.exportObject(LocalService,0); 
          
           registry.bind(Constants.BROKER_SERVICE , RemoteBrokerService); 

        } catch (Exception e2) {
        System.err.println("Error after port allocation: " + e2.getMessage());
        e2.printStackTrace();
}
}

@Test
    public void connectionTest() throws Exception {
        //unit test  looks up the service that you registered. 
        //The test passes if an object registry is returned and fails if null is returned
        BrokerService service = (BrokerService)registry.lookup(Constants.BROKER_SERVICE); //cerca il remote object che ho salvato prima in registro usando bind
        assertNotNull(service);
        }

@Test
    public void connectionTest2() throws Exception {
        BrokerService LocalService= new LocalBrokerService();
        List<Quotation> listQuotations= new LinkedList<Quotation>();
                //unit test checks whether the getgenerateQuotation() method works
                 //and returns a Quotation object
        ClientInfo Niki= new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false);
               // System.out.println("Testing 2...");
        listQuotations= LocalService.getQuotations(Niki);
        assertNotNull("list Quotation should not be null", listQuotations);
              //  return res;

    
}
}