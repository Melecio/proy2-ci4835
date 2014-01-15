/**
  * @file
  * @author Gabriel Formica <gabriel@ac.labf.usb.ve>
  * @author Melecio Ponte <melecio@ac.labf.usb.ve>
  *
  * @section Description
  *
  * Interface of RMI CLient methods
  */


import java.rmi.Remote;  
import java.rmi.RemoteException; 

public interface ClientServerInterface extends Remote {

	/**
	  * Download a file
	  * @param filename: name of file that client wants to download
	  * @return Every byte of file 
	  */

	public byte[] download(String username, 
								  String password, 
								  String filename) throws RemoteException;  

	/**
	  * Upload a file
	  * @param filename: name of file that client wants to upload 
	  */

	public void upload(String username, 
							 String password, 
						    byte[] filedata, String filename) throws RemoteException;
	
	/**
	  * Delete a file
	  * @param filename: name of file that client wants to delete 
	  * @return True, if file was deleted successfully. False in any other case
	  */

	public boolean delete(String username, 
							    String password, 
								 String filename) throws RemoteException;
	

	/**
	  * List files in file server
	  */

	public String listRemotesFiles(String username,
											 String password) throws RemoteException;

	/**
	  * Authenticates user
	  * @param username: name of user that is gonna be authenticated
	  * @param password: password of user that is gonna be authenticated
	  * @return True, if authenticated. False in any other case
	  */


	public boolean authenticate(String username, 
								String password
								) throws RemoteException; 
}
