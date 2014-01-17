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
import java.util.Hashtable;
import java.util.ArrayList;

public class ClientServerImpl 
		 extends UnicastRemoteObject 
		 implements ClientServerInterface {

	Hashtable<String, String> dict; //dictionary <file, owner>
    AuthServerInterface asi;
    ArrayList<String> log;
    

	/**
     * Class constructor
     */

	public ClientServerImpl(String path) throws RemoteException {
		super();
		dict = new Hashtable<String, String>();
        log = new ArrayList<String>();
        try {
            asi = (AuthServerInterface) Naming.lookup(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	  * Download a file
	  * @param filename: name of file that client wants to download
	  * @return Every byte of file 
	  */

	public byte[] download(String username, 
								  String password, 
    							  String filename) throws RemoteException {

		try {
			File file = new File(filename);
			byte buffer[] = new byte[(int)file.length()];
			BufferedInputStream input = new
				BufferedInputStream(new FileInputStream(filename));
			input.read(buffer,0,buffer.length);
			input.close();
            log.add("baj " + filename  + " \t-- "+username + "\n");
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

	public void upload(String username, 
                       String password, 
                       byte[] filedata, 
                       String filename) throws RemoteException {
		try {
			File file = new File(filename);	
			BufferedOutputStream output =
				new BufferedOutputStream(new FileOutputStream(filename));
			output.write(filedata,0,filedata.length);
			output.flush();
			output.close();
            this.dict.put(filename, username);
            log.add("sub " + filename  + " \t-- "+username + "\n");
		} catch (Exception e) {
			System.out.println("FileImpl: "+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	  * Delete a file
	  * @param filename: name of file that client wants to delete 
	  * @return True, if file was deleted successfully. False in any other case
	  */

	public boolean delete(String username, 
                          String password, 
                          String filename) throws RemoteException {
        if ((this.dict.get(filename) != null) &&
				 (this.dict.get(filename).equals(username))) {
            log.add("bor " + filename  + " \t-- "+username + "\n");
            return (new File(filename)).delete();
        }
        return false;
	}
	

	/**
	  * List files in file server
     * @return String with every file name
	  */

	public String listRemotesFiles(String username, 
                                   String password) throws RemoteException {
		String filesStr = "";
		File dir = new File(".");
		File[] filesList = dir.listFiles();
		for (File file : filesList) {
			if (file.isFile()) {
				filesStr = filesStr + file.getName() + "\n";
			}
		}
        log.add("rls \t\t-- "+username + "\n");
		return filesStr;
	}

	/**
	  * Authenticates user
	  * @param username: name of user that is gonna be authenticated
	  * @param password: password of user that is gonna be authenticated
	  * @return True, if authenticated. False in any other case
	  */

	public boolean authenticate(String username, 
								String password
                                ) throws RemoteException {
        return asi.authenticate(username, password);
	}

	/**
	  * Gets log from user
	  * @param username: name of user.
	  * @param password: password of user.
	  * @return True, if authenticated. False in any other case
	  */

    public String showLog() throws RemoteException {
        String logString = "";
        int logSize = log.size();
        if  (logSize > 20) {
            log.subList(0,logSize-20).clear();
        }
        for (String s : log) {
            logString = logString + s;
        }
        return logString;
    }
}
