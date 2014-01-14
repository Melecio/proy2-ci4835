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
import java.util.Scanner;
import java.util.Hashtable;

public class AuthServerImpl 
		 extends UnicastRemoteObject 
		 implements AuthServerInterface {

	Hashtable<String, String> dict; //dictionary <user, password>

	/**
     * Class constructor
     */
	
	public AuthServerImpl(String passwd) throws RemoteException {
		super();
		this.dict = new Hastable<String, String>();
		File file = new File(passwd);		
		Scanner sc = new Scanner(file);
		while (sc.hasNextLine()) {
     		String line = sc.nextLine();
			this.dict.put(line.split(":")[0],line.split(":")[1]);
      	}
		} catch (Exception e) {
	}

	/**
	  * Authenticates user
	  * @param username: name of user that is gonna be authenticated
	  * @param password: password of user that is gonna be authenticated
	  */


	public boolean authenticate(String username, 
										 String password) throws RemoteException {
		return (this.dict.get(username) == password); 
	}
}
