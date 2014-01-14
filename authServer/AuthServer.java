/**
  * @file
  * @author Gabriel Formica <gabriel@ac.labf.usb.ve>
  * @author Melecio Ponte <melecio@ac.labf.usb.ve>
  *
  * @section Description
  *
  * This is the Server Command Line Interface 
  */


import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;

public class AuthServer {

	/**
     * Class constructor
     */

	public AuthServer(int port, String name, String usersFile) {
		try {
			LocateRegistry.createRegistry(port);
			AuthServerInterface asi = new AuthServerImpl(usersFile);
			Naming.rebind(name, asi);
		} catch(Exception e) {
			System.out.println("Auth Server: "+e.getMessage());
			e.printStackTrace();
		}
	}	

	public static void main(String argv[]) {
		AuthServerCli ascli = new AuthServerCli(argv);
		String port = ascli.getPort();
		String usersFile = ascli.getUsersFile();
		
		String name = "rmi://localhost"+":"+port+"/AuthServer";
		AuthServer authServer; 
		authServer = new AuthServer(Integer.parseInt(port), name, usersFile);
	}
}
