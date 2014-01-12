/**
  * @file
  * @author Gabriel Formica <gabriel@ac.labf.usb.ve>
  * @author Melecio Ponte <melecio@ac.labf.usb.ve>
  *
  * @section Description
  *
  * This is the Server Command Line Interface 
  */

public class Server {
	
	public static void main(String argv[]) {
		ServerCli sCli = new ServerCli(argv);
		int lport = sCli.getLport();
		int rport = sCli.getRport();
	}
}
