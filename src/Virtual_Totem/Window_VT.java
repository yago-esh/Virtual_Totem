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
				System.out.println(Panel_VT.cant_exit());
				if(Panel_VT.cant_exit()) {
					String ObjButtons[] = {"Yes","No"};
			        int PromptResult = JOptionPane.showOptionDialog(null,"¿Estás seguro de que quieres salir? Tienes un totem cogido.",
			        		"Advertencia de salida",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
			        if(PromptResult==JOptionPane.YES_OPTION)
			        {
			        	Panel_VT.getAlert_VT().removeMeUserFromList();

			        	if (Panel_VT.warning_wolf()) {
			    			cliente.enviar("soltar_lobo");
			    		}
			    		if ( Panel_VT.warning_dragon()) {
			    			cliente.enviar("soltar_dragon");
			    		}
			    		
			        	try {
			        		//Need to wait 2 second to let enough time to the server to read the last message and be available to read the next one before close the flows.
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			        	
			        	Window_VT.this.cliente.terminar();
			            System.exit(-1);
			        }
				}
				else {
					Panel_VT.getAlert_VT().removeMeUserFromList();
					Window_VT.this.cliente.terminar();
		            System.exit(-1);
				}
			}
		});
	}

	public void showIt() {
		this.setVisible(true);
	}
}

