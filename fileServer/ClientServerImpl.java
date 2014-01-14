/**
  * @file
  * @author Gabriel Formica <gabriel@ac.labf.usb.ve>
  * @author Melecio Ponte <melecio@ac.labf.usb.ve>
  *
  * @section Description
  *
  * This class implements methods defined in ClientServerInterface
  */



import java.io.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class ClientServerImpl 
		 extends UnicastRemoteObject 
		 implements ClientServerInterface {

	private String name;

	/**
     * Class constructor
     */

	public ClientServerImpl(String s) throws RemoteException {
		super();
		name = s;
	}

	/**
	  * Download a file
	  * @param filename: name of file that client wants to download
	  * @return Every byte of file 
	  */

	public byte[] download(String filename) {

		try {
			File file = new File(filename);
			byte buffer[] = new byte[(int)file.length()];
			BufferedInputStream input = new
				BufferedInputStream(new FileInputStream(filename));
			input.read(buffer,0,buffer.length);
			input.close();
			return(buffer);
		} catch(Exception e){
			System.out.println("FileImpl: "+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public void upload(byte[] filedata, String filename) {
		try {
			File file = new File(filename);	
			BufferedOutputStream output =
				new BufferedOutputStream(new FileOutputStream(filename));
			output.write(filedata,0,filedata.length);
			output.flush();
			output.close();
		} catch (Exception e) {
			System.out.println("FileImpl: "+e.getMessage());
			e.printStackTrace();
		}
	}
}
