package Virtual_Totem;

import java.io.IOException;

import javax.swing.JOptionPane;

class ClientGraphic {
	
	private static Window_VT window;
	
    public static void main(String args[]) {
        new ClientGraphic().execute();
    }

	public void execute() {
		Client_VT client = new Client_VT();
		window = new Window_VT(client);
		try {
			//Connect the client with the server
			client.execute();
			//Shows the windows
			window.showIt();
		} catch (IOException ex) {
			//If the server is not responding, the app shows a message and close itself after it
			JOptionPane.showMessageDialog(window,
					"No se puede conectar con el servidor",
					"ERROR", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
			System.exit(-1);
		}
    }
	
}