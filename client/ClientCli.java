/**
  * @file
  * @author Gabriel Formica <gabriel@ac.labf.usb.ve>
  * @author Melecio Ponte <melecio@ac.labf.usb.ve>
  *
  * @section Description
  *
  * This is the Client Command Line Interface 
  */

import org.apache.commons.cli.*;
import java.io.OutputStream;

public class ClientCli {

	private String host = "localhost";  
	private int port;
	private String usersFile;
	private String commandsFile; 

	/**
     * Class constructor
     */

	public ClientCli (String argv[]) {

			Options options = new Options();
			CommandLineParser parser = null;
			CommandLine cmdLine = null;
			String info = "";

			info = "Name of users and passwords file";
			options.addOption("f", "users", true, info);

			info = "DNS name or IP where file server is running";
			options.addOption("m", "host", true , info);			

			info = "Port used by rmiregistry";
			options.addOption("p", "port", true, info);

			info = "Name of the commands file. ";
			info = "Needed if client wants to run commands automatically";
			options.addOption("c", true, info);

			info = "Print Help";
			options.addOption("help", false, info);

		
			try {
				parser = new BasicParser();
				cmdLine = parser.parse(options, argv);
				
				if (cmdLine.hasOption("help")) {
					new HelpFormatter().
						printHelp("Server", options);
					System.exit(0);
				}

				if (cmdLine.hasOption("users")) {
					this.usersFile = cmdLine.getOptionValue("users");
				}

				if (cmdLine.hasOption("host")) {
					this.host = cmdLine.getOptionValue("host");
				}
				
				if (cmdLine.hasOption("port")) {
					this.port = Integer.parseInt(cmdLine.getOptionValue("port"));
				}
			
				if (cmdLine.hasOption("c")) {
					this.commandsFile = cmdLine.getOptionValue("c");
				}
			

			} catch (Exception e) {
				System.out.println("Unrecognized option. Please, try with:");
				new HelpFormatter().printHelp("Server", options);
				System.exit(0);
			}
	}

	/**
	  * @return Get the host 
     */

	public String getHost() {
		return this.host;
	}

	/**
	  * @return Get the remote port 
     */

	public int port() {
		return this.port;
	}

	/**
	  * @return Get users and passwords file name
     */

	public String usersFile() {
		return this.usersFile;
	}

	/**
	  * @return Get the commands file name
     */

	public String commandsFile() {
		return this.commandsFile;
	}

}
