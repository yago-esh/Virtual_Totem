package Virtual_Totem;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Virtual_Totem.Client_VT;
import Virtual_Totem.Window_VT;

class Window_VT extends JFrame {

	private Client_VT cliente;
	private Panel_VT Panel_VT;
	static final long serialVersionUID = 42L;

	public Window_VT(Client_VT cliente) {
		this.cliente = cliente;
		Panel_VT = new Panel_VT(cliente);
		this.setContentPane(Panel_VT);
		this.setTitle("Virtual Totem");
		this.setLocation(810, 425);
		this.setSize(300, 230);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(Panel_VT.can_exit()) {
					String ObjButtons[] = {"Yes","No"};
			        int PromptResult = JOptionPane.showOptionDialog(null,"¿Estás seguro de que quieres salir? Tienes un totem cogido.","Advertencia de salida",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
			        if(PromptResult==JOptionPane.YES_OPTION)
			        {
			        	Window_VT.this.cliente.terminar();
			            System.exit(0);
			        }
				}
				else {
					Window_VT.this.cliente.terminar();
		            System.exit(0);
				}
			}
		});
	}

	public void mostrar() {
		this.setVisible(true);
	}
}

