package service.broker;

import java.rmi.registry.*; 
import java.rmi.server.UnicastRemoteObject; 
//import service.auldfellas.AFQService; 
//import service.core.QuotationService; 
import service.core.Constants; 
import service.core.BrokerService;
//Expose the LocalBrokerService as a distributed object and modify the local broker service to use the RMI 
//Registry to find the quotation services.
//broker is also a object that has to register in the RMI registry

public class Main {
    public static void main(String[] args) { 
        // create localbroker service
        BrokerService LocalService= new LocalBrokerService();
        try { 
        // creating the registry , responsability of the broker
        Registry registry = LocateRegistry.createRegistry(1099); 
        //createremote object
        BrokerService RemoteBrokerService = (BrokerService)UnicastRemoteObject.exportObject(LocalService,0); 
        // Register the remote object within the RMI Registry 
        //Binds a remote reference to the specified name in this registry.
        registry.bind(Constants.BROKER_SERVICE , RemoteBrokerService); 
        System.out.println("Registry is on");
     
        while (true) {Thread.sleep(1000); } 
        } catch (Exception e) { 
        System.out.println("Trouble: " + e); 
        } 
    }
}
    
