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

public class c_rmifs {
	
	private void executeLls() {
		File dir = new File(".");
		File[] filesList = dir.listFiles();
		System.out.println("---These are your local files---\n");
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

    private void parseCmd(ClientServerInterface csi,
                            String input,
                            String username,
                            String password) {
        try {
        if ((input.trim()).equals("rls")) {
            System.out.println("---These are all server files---\n");
            System.out.print(csi.listRemotesFiles(username, password));
            return;
        }

        if ((input.trim()).equals("lls")) {
            new c_rmifs().executeLls();
            return;
        }

        if (input.matches("baj\\s+.+")) {
            String filename = input.split("\\s+")[1];
            new c_rmifs().executeBaj(username, password, csi, filename);
            return;
        }

        if (input.matches("sub\\s+.+")) {
            String filename = input.split("\\s+")[1];
            new c_rmifs().executeSub(username, password, csi, filename);
            return;
        }

        if (input.matches("bor\\s+.+")) {
            if (! csi.delete(username, password, input.split("\\s+")[1])) {
                System.out.print("You can't delete that file. ");
                System.out.print("You can just delete your own file ");
                System.out.println("in server");
                return;
            }

            System.out.println("File was deleted successfully");
            return;
        }

        if ((input.trim()).equals("info")) {
            new c_rmifs().printInfo();
            return;
        }
				
        if (input.equals("sal")) {
            System.exit(0);
        }
        } catch (Exception e) {
			e.printStackTrace();
		}
    }

	public static void main(String argv[]) {
		ClientCli cc = new ClientCli(argv);
      c_rmifs c = new c_rmifs();
		String host = "";
		host = cc.getHost();
		String port = "";
		port = cc.getPort();
        String commFile = cc.commandsFile();
        String userFile = cc.usersFile();
		String path = "rmi://"+host+":"+port+"/s_rmifs";
        String authPath = "rmi://"+host+":";
		String username = "";
		String password = "";
        Scanner sc = new Scanner(System.in);
       
		if (port.equals("") || host.equals("")) {
			System.out.println("Client has to recieve -p and -m flags");
			System.exit(0);
		} 
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
            int numTry = 0;
            boolean validUser = false;
            while (numTry != 2 && !validUser) {
               validUser = csi.authenticate(username, password);
               if (!validUser) {
                   System.out.println("User not valid");
                  numTry += 1;
                  System.out.print("Username: ");
                  username = sc.nextLine();
                  System.out.print("Password: ");
                  password = sc.nextLine();
               }
            }

            if (!validUser) {
                System.out.println("You've exceeded the number of login attempts");
                System.exit(0);
            }

            if (!commFile.isEmpty()) {
                File file = new File(commFile);
                Scanner fsc;
                try {
                  fsc = new Scanner(file);
                  while (fsc.hasNext()) {
                    String input = fsc.nextLine();
                    c.parseCmd(csi, input, username, password);
                  }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
			while (true) {       //main loop
				String input = sc.nextLine();	
                c.parseCmd(csi, input, username, password);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
