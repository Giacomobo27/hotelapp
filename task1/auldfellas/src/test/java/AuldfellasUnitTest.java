import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import service.core.Constants;
import service.core.QuotationService;
import service.auldfellas.AFQService;
import service.core.ClientInfo; //aggiunto
import service.core.Quotation; //aggiunto
import java.rmi.RemoteException; //aggiunto

import org.junit.*;
import static org.junit.Assert.assertNotNull;

//When a field or method is marked as private, 
//it can only be accessed within the same class where it is defined.

//For methods, static means the method can be called without creating
// an instance of the class.

//@BeforeClass means that the method that should be run once before any of the test methods in 
//are executed and The method annotated with @BeforeClass it must be a static method. 
//Any fields that need to be initialized or used within a @BeforeClass method must also be static.
public class AuldfellasUnitTest {
    private static Registry registry;
     //creates an RMI Registry (native of the rmi pacakge)
   // private static QuotationService afqService;
    @BeforeClass
    public static void setup() {
        QuotationService  afqService = new AFQService();  //a DistributedObject based on the AFQService 
       // which it registers with the registry.
        try {
            registry = LocateRegistry.createRegistry(0); //porta 0 è dinamica, la uso solo per tests
            QuotationService quotationService = (QuotationService) UnicastRemoteObject.exportObject(afqService,0);
            registry.bind(Constants.AULD_FELLAS_SERVICE, quotationService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

@Test
public void connectionTest() throws Exception {
    //unit test  looks up the service that you registered. 
    //The test passes if an object registry is returned and fails if null is returned
    QuotationService service = (QuotationService)registry.lookup(Constants.AULD_FELLAS_SERVICE);
    assertNotNull(service);
    }

@Test
public void connectionTest2() throws Exception {
    QuotationService afqService = new AFQService();
        //unit test checks whether the generateQuotation() method works
         //and returns a Quotation object
        ClientInfo Niki= new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false);
       // System.out.println("Testing 2...");
         Quotation res=afqService.generateQuotation(Niki);
         assertNotNull("Quotation should not be null", res);
      //  return res;
    
    }
    

    //junit tests They must be public.
//They must have a return type of void.
//They must not accept any arguments

}
