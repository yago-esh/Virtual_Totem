package Virtual_Totem;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;

import javax.swing.JOptionPane;

class Client_VT_graphic {
	
	private static final int PORT = 9999;
	@SuppressWarnings("unused")
	private static ServerSocket socket; 
	private static Window_VT window;
	
    public static void main(String args[]) {
        Client_VT_graphic client = new Client_VT_graphic();
		client.execute();
    }

	public void execute() {
		Client_VT client = new Client_VT();
		window = new Window_VT(client);
//		checkIfRunning();
		try {
			client.execute();
			window.showIt();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(window,
					"No se puede conectar con el servidor",
					"ERROR", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
			System.exit(-1);
		}
    }
	
	private static void checkIfRunning() {
	  try {
	    socket = new ServerSocket(PORT,0,InetAddress.getByAddress(new byte[] {127,0,0,1}));
	  }
	  catch (BindException e) {
		  JOptionPane.showMessageDialog(window,
					"La aplicación ya se está ejecutando",
					"ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
	    System.exit(1);
	  }
	  catch (IOException e) {
		  JOptionPane.showMessageDialog(window,
					"Unexpected error.",
					"ERROR", JOptionPane.ERROR_MESSAGE);
	    e.printStackTrace();
	    System.exit(2);
	  }
	}
}