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


	/**
     * Class constructor
     */

	public ClientServerImpl() throws RemoteException {
		super();
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

	/**
	  * Upload a file
	  * @param filename: name of file that client wants to upload 
	  */

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

	/**
	  * List files in file server
     * @return String with every file name
	  */

	public String listRemotesFiles() {
		String filesStr = "";
		File dir = new File(".");
		File[] filesList = dir.listFiles();
		System.out.println("These are your locals files:");
			for (File file : filesList) {
				if (file.isFile()) {
					filesStr = filesStr + file.getName() + "\n";
				}
			}
		return filesStr;
	}
}
