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
import java.util.Scanner;

public class s_rmifs {

	/**
     * Class constructor
     */

	public s_rmifs(int lport, String name, String rname ) {
		try {
			LocateRegistry.createRegistry(lport);
			ClientServerInterface csi = new ClientServerImpl(rname);
			Naming.rebind(name, csi);

            Scanner sc = new Scanner(System.in);
            while (true) {
					 System.out.print("> ");
                String input = sc.nextLine();
                            
                if ((input.trim()).equals("log")) {
                    System.out.println("These are the last 20 commands entered");
                    String log = csi.showLog();
                    System.out.println(log);
                    continue;
                }

                if ((input.trim()).equals("sal")) {
                    System.exit(0);
                }

            }
		} catch(Exception e) {
			System.out.println("File Server: "+e.getMessage());
			e.printStackTrace();
		}
	}	

	public static void main(String argv[]) {
		ServerCli scli = new ServerCli(argv);
		String lport = "";
		lport = scli.getLport();
		String rport = "";
		rport =  scli.getRport();
		String host = "";
		host = scli.getHost();
		if (host.equals("") || rport.equals("") || lport.equals("")) {
			System.out.println("Server has to recieve -h, -r and -l parameters");
			System.exit(0);
		}
		String name = "rmi://"+host+":"+lport+"/s_rmifs";
		String rname = "rmi://"+host+":"+rport+"/a_rmifs";        
		s_rmifs server = new s_rmifs(Integer.parseInt(lport), name, rname);
    }                
}
