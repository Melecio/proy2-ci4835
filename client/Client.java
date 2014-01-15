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
	
	private void executeLls() {
		File dir = new File(".");
		File[] filesList = dir.listFiles();
		System.out.println("These are your locals files:");
			for (File file : filesList) {
				if (file.isFile()) {
					System.out.println(file.getName());
				}
			}
	}

	private void executeBaj(String username, String password, 
									ClientServerInterface csi, String filename) {
		try {
			byte[] filedata = csi.download(username, password, filename);
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

	private void executeSub(String username, String password, 
									ClientServerInterface csi, String filename) {
		try {
			File file = new File(filename);
			byte buffer[] = new byte[(int)file.length()];
			BufferedInputStream input = new
				BufferedInputStream(new FileInputStream(filename));
			input.read(buffer,0,buffer.length);
			input.close();
			csi.upload(username, password, buffer,filename);
		} catch (Exception e) {
			System.out.println("FileImpl: "+e.getMessage());
			e.printStackTrace();
		}
	}

	private void printInfo() {
		System.out.println("These are the commands you can use:");
		System.out.println("   lls         :  list all locals files");
		System.out.println("   rls         :  list all remotes files");
		System.out.println("   sub <file>  :  upload <file> to file server");
		System.out.println("   baj <file>  :  download <file> from file server");
		System.out.println("   bor <file>  :  delete <file> from file server");
		System.out.println("   sal         :  ends client program execution");
	}

	public static void main(String argv[]) {
		ClientCli cc = new ClientCli(argv);	
		String host = cc.getHost();
		String port = cc.getPort();
        String userFile = cc.usersFile();
		String path = "rmi://"+host+":"+port+"/Server";
        String authPath = "rmi://"+host+":";
		String username = "";
		String password = "";
        Scanner sc = new Scanner(System.in);
        
        if (!userFile.isEmpty()) {
           File file = new File(userFile);

           try {
              Scanner fsc = new Scanner(file);
              String line = fsc.nextLine();
              username = line.split(":")[0];
              password = line.split(":")[1];
           } catch (FileNotFoundException e) {
              e.printStackTrace();
           }
        } else {
           System.out.print("Username: ");
           username = sc.nextLine();
           System.out.print("Password: ");
           password = sc.nextLine();
        }

		try {	
			ClientServerInterface csi = (ClientServerInterface) Naming.lookup(path);
			System.out.println(path);
            int numTry = 0;
            boolean validUser = false;

            while (numTry != 2 && !validUser) {
               validUser = csi.authenticate(username, password);
               System.out.println(validUser);
               if (!validUser) {
                  System.out.println("User not valid");
                  numTry += 1;
                  System.out.print("Username: ");
                  username = sc.nextLine();
                  System.out.print("Password: ");
                  password = sc.nextLine();
               }
            }

			while (true) {       //main loop
				String input = sc.nextLine();	
                
                
				if ((input.trim()).equals("rls")) {
					System.out.println("These are all server files");
					System.out.print(csi.listRemotesFiles(username, password));
					continue;
				}

				if ((input.trim()).equals("lls")) {
					new Client().executeLls();
					continue;
				}

				if (input.matches("baj\\s+.+")) {
					String filename = input.split("\\s+")[1];
					new Client().executeBaj(username, password, csi, filename);
					continue;
				}

				if (input.matches("sub\\s+.+")) {
					String filename = input.split("\\s+")[1];
					new Client().executeSub(username, password, csi, filename);
					continue;
				}

				if (input.matches("bor\\s+.+")) {
					if (! csi.delete(username, password, input.split("\\s+")[1])) {
						System.out.print("You can't delete that file. ");
						System.out.print("You can just delete your own file ");
						System.out.println("in server");
						continue;
					}

					System.out.println("File was deleted successfully");
					continue;
				}

				if ((input.trim()).equals("info")) {
					new Client().printInfo();
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
