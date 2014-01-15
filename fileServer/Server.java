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

	public Server(int lport, String name, String rname ) {
		try {
			LocateRegistry.createRegistry(lport);
			ClientServerInterface csi = new ClientServerImpl(rname);
			Naming.rebind(name, csi);
		} catch(Exception e) {
			System.out.println("File Server: "+e.getMessage());
			e.printStackTrace();
		}
	}	

	public static void main(String argv[]) {
		ServerCli scli = new ServerCli(argv);
		String lport = scli.getLport();
		String rport = scli.getRport();
		String host = scli.getHost();
		String name = "rmi://"+host+":"+lport+"/Server";
		String rname = "rmi://"+host+":"+rport+"/AuthServer";        
		Server server = new Server(Integer.parseInt(lport), name, rname);
	}
}
