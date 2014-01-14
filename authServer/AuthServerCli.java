/**
  * @file
  * @author Gabriel Formica <gabriel@ac.labf.usb.ve>
  * @author Melecio Ponte <melecio@ac.labf.usb.ve>
  *
  * @section Description
  *
  * This is the Auth Server Command Line Interface 
  */

import org.apache.commons.cli.*;

public class AuthServerCli {

	private String usersFile = "";  //filename of users and passwords
	private String port = ""; //port 

	/**
     * Class constructor
     */

	public AuthServerCli (String argv[]) {

			Options options = new Options();
			CommandLineParser parser = null;
			CommandLine cmdLine = null;
			String info = "";

			info = "Filename of users and passwords";
			options.addOption("f", "users", true, info);

			info = "Port used by rmiregistry";
			options.addOption("p", "port", true, info);

			info = "Print Help";
			options.addOption("help", false, info);

		
			try {
				parser = new BasicParser();
				cmdLine = parser.parse(options, argv);
				
				if (cmdLine.hasOption("help")) {
					new HelpFormatter().printHelp("Client", options);
					System.exit(0);
				}

				if (cmdLine.hasOption("users")) {
					this.usersFile = cmdLine.getOptionValue("users");
				}

				if (cmdLine.hasOption("port")) {
					this.port = cmdLine.getOptionValue("port");
				}
			
			} catch (Exception e) {
				System.out.println("Unrecognized option. Please, try with:");
				new HelpFormatter().printHelp("AuthServer", options);
				System.exit(0);
			}
	}

	

	/**
	  * @return Get users and passwords file name
     */

	public String getUsersFile() {
		return this.usersFile;
	}

	/**
	  * @return Get port 
     */

	public String getPort() {
		return this.port;
	}

}
