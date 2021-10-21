

import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class FileServer {
   public static void main(String argv[]) {
      if(System.getSecurityManager() == null) {
         System.setSecurityManager(new RMISecurityManager());
      }
      try {
         FileInterface fi = (FileInterface) UnicastRemoteObject.exportObject(new FileImpl("FileServer"), 0);

    	 Registry registry = LocateRegistry.getRegistry();
    	 registry.rebind("FileServer", fi);
         
         System.out.println("Server ready - service is running...");
         
         
      } catch(Exception e) {
         System.out.println("FileServer: "+e.getMessage());
         e.printStackTrace();
      }
   }
}