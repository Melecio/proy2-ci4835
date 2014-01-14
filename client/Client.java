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

	private void executeBaj(ClientServerInterface csi, String filename) {
		try {
			byte[] filedata = csi.download(filename);
			File file = new File(filename);
			BufferedOutputStream output =
				 new BufferedOutputStream(new FileOutputStream(file.getName()));
			output.write(filedata,0,filedata.length);
			output.flush();
			output.close();
		} catch (Exception e) {
			System.err.println("FileServer exception: "+ e.getMessage());
			e.printStackTrace();
		}
	}

	public void executeSub(ClientServerInterface csi, String filename) {
		try {
			File file = new File(filename);
			byte buffer[] = new byte[(int)file.length()];
			BufferedInputStream input = new
				BufferedInputStream(new FileInputStream(filename));
			input.read(buffer,0,buffer.length);
			input.close();
			csi.upload(buffer,filename);
		} catch (Exception e) {
			System.out.println("FileImpl: "+e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String argv[]) {
		ClientCli cc = new ClientCli(argv);	
		String host = cc.getHost();
		String port = cc.getPort();
		String path = "rmi://"+host+":"+port+"/Server";	
		
		try {	
			ClientServerInterface csi = (ClientServerInterface) Naming.lookup(path);
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
				if (input.matches("baj\\s+.+")) {
					String filename = input.split("\\s+")[1];
					new Client().executeBaj(csi, filename);
					continue;
				}

				if (input.matches("sub\\s+.+")) {
					String filename = input.split("\\s+")[1];
					new Client().executeSub(csi, filename);
					continue;
				}

				if (input.matches("bor\\s+[A-Za-z0-9]+")) {
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
