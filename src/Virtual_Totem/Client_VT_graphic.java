package Virtual_Totem;

import java.io.IOException;
import javax.swing.JOptionPane;

import Virtual_Totem.Client_VT;
import Virtual_Totem.Client_VT_graphic;
import Virtual_Totem.Window_VT;

class Client_VT_graphic {

    public static void main(String args[]) {
        Client_VT_graphic cliente = new Client_VT_graphic();
		cliente.ejecutar();
    }

	public void ejecutar() {
		Client_VT cliente = new Client_VT();
		Window_VT ventana = new Window_VT(cliente);
		try {
			cliente.ejecutar();
			ventana.mostrar();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(ventana,
					"No se puede conectar con el servidor",
					"ERROR", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
			System.exit(-1);
		}
    }
}