package service.auldfellas;
import java.rmi.registry.*; 
import java.rmi.server.UnicastRemoteObject; 
import service.auldfellas.DGQService; 
import service.core.QuotationService; 
import service.core.Constants; 
//usa AFQService, si collega al registry, genera quotation e lo registra sul registro (usa le funzioni di java.rmi.registry.*; )
public class Main {
    public static void main(String[] args) { 
        QuotationService afqService = new DGQService(); 
        try { 
        // Connect to the RMI Registry - creating the registry will be the 
        // responsibility of the broker. 
       // Registry registry = LocateRegistry.createRegistry(1099); 
       Registry registry = null;  //task f
       if (args.length == 0) { // so when compiling on task3 if i dont put the -dexec.args=localhost, it will create the registry on its own
        registry = LocateRegistry.createRegistry(1099); } 
       else { 
       registry = LocateRegistry.getRegistry(args[0], 1099); } 
        // Create the Remote Object 
        QuotationService quotationService = (QuotationService)  
        UnicastRemoteObject.exportObject(afqService,0); 
        // Register the object with the RMI Registry 
        registry.bind(Constants.AULD_FELLAS_SERVICE, quotationService); 
        System.out.println("STOPPING SERVER SHUTDOWN");
        while (true) {Thread.sleep(1000); } 
        } catch (Exception e) { 
        System.out.println("Trouble: " + e); 
        } 
    }
}
    
