package Virtual_Totem;

import java.io.IOException;
import javax.swing.JOptionPane;

import Virtual_Totem.Client_VT;
import Virtual_Totem.Client_VT_graphic;
import Virtual_Totem.Window_VT;

class Client_VT_graphic {

    public static void main(String args[]) {
        Client_VT_graphic client = new Client_VT_graphic();
		client.execute();
    }

	public void execute() {
		Client_VT client = new Client_VT();
		Window_VT window = new Window_VT(client);
		try {
			client.execute();
			window.show();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(window,
					"No se puede conectar con el servidor",
					"ERROR", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
			System.exit(-1);
		}
    }
}