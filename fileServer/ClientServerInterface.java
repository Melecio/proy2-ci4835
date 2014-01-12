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
	  * @param fileName: name of file that client wants to download
	  * @return Every byte of file 
	  */

	public byte[] download(String fileName) throws RemoteException;  

}
