package Virtual_Totem;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

import Virtual_Totem.Client_VT;
import Virtual_Totem.Window_VT;

class Window_VT extends JFrame {

	private Client_VT client;
	private Panel_VT Panel_VT;
	static final long serialVersionUID = 42L;

	public Window_VT(Client_VT client) {
		this.client = client;
		Panel_VT = new Panel_VT(client);
		this.setContentPane(Panel_VT);
		this.setTitle("Chat");
		this.setLocation(150, 150);
		this.setSize(300, 250);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Window_VT.this.client.finish();
				System.exit(0);
			}
		});
	}

	public void show() {
		this.setVisible(true);
	}
}

