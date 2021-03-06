/**
  * @file
  * @author Gabriel Formica <gabriel@ac.labf.usb.ve>
  * @author Melecio Ponte <melecio@ac.labf.usb.ve>
  *
  * @section Description
  *
  * This is the Server Command Line Interface 
  */

import org.apache.commons.cli.*;

public class ServerCli {

	private String host = "";  
	private String rport = ""; //remote port	
	private String lport = ""; //local port

	public ServerCli (String argv[]) {

			Options options = new Options();
			CommandLineParser parser = null;
			CommandLine cmdLine = null;
			String info = "";

			info = "Port used by rmiregistry. It has information about";
			info = info + " remotes objects published by file server";
			options.addOption("l", "lport", true, info);

			info = "DNS name or IP where authentication server is running";
			options.addOption("h", "host", true , info);			

			info = "Port used by rmiregistry. It has information about";
			info = info + " remotes objects published by authentication server";
			options.addOption("r", "rport", true, info);

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

				if (cmdLine.hasOption("host")) {
					this.host = cmdLine.getOptionValue("host");
				}

				if (cmdLine.hasOption("l")) {
					this.lport = cmdLine.getOptionValue("l");
				}
				
				if (cmdLine.hasOption("r")) {
					this.rport = cmdLine.getOptionValue("r");
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

	public String getRport() {
		return this.rport;
	}

	/**
	  * @return Get the local port 
     */

	public String getLport() {
		return this.lport;
	}

}
