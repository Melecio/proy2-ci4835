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

public class Server {

	/**
     * Class constructor
     */

	public Server() {
		try {
			LocateRegistry.createRegistry(21000);
			ClientServerInterface csi = new ClientServerImpl("Server");
			Naming.rebind("rmi://localhost:21000/Server", csi);
		} catch(Exception e) {
			System.out.println("FileServer: "+e.getMessage());
			e.printStackTrace();
		}
	}	

	public static void main(String argv[]) {
		ServerCli sCli = new ServerCli(argv);
		int lport = sCli.getLport();
		int rport = sCli.getRport();
	}
}
