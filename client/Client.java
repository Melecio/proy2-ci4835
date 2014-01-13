/**
  * @file
  * @author Gabriel Formica <gabriel@ac.labf.usb.ve>
  * @author Melecio Ponte <melecio@ac.labf.usb.ve>
  *
  * @section Description
  *
  * Client 
  */

import java.io.*;
import java.rmi.*;
import java.util.Scanner;

public class Client {

	public static void main(String argv[]) {
		ClientCli cc = new ClientCli(argv);	
		String host = cc.getHost();
		String port = cc.getPort();
		String path = "rmi://"+host+":"+port+"/Server";	
		
		try {	
			//ClientServerInterface csi = (ClientServerInterface) Naming.lookup(path);
			System.out.println(path);
			Scanner sc = new Scanner(System.in);

			while (true) {       //main loop
				String input = sc.nextLine();	
				if (input.equals("rls")) {
					System.out.println("Se ejecuta rls");
					continue;
				}
				if (input.equals("lls")) {
					System.out.println("Se ejecuta lls");
					continue;
				}
				if (input.equals("baj")) {
					System.out.println("Se ejecuta baj");
					continue;
				}
				if (input.equals("bor")) {
					System.out.println("Se ejecuta bor");
					continue;
				}
				if (input.equals("info")) {
					System.out.println("Se ejecuta info");
					continue;
				}
				if (input.equals("sal")) {
					System.exit(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
