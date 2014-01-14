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

public interface AuthServerInterface extends Remote {
	public boolean authenticate(String username, 
										 String password) throws RemoteException;
}	
