package Virtual_Totem;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import Virtual_Totem.Client_VT;
import javax.swing.SwingUtilities;


public class Panel_VT extends JPanel {
	
	static final long serialVersionUID = 42L;
	private JButton Lobo_bt;
	private JButton Dragon_bt;
	private boolean accion;
	
	public Panel_VT(Client_VT cliente) {
		
		cliente.asociar(this);
		accion=false;
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setForeground(Color.BLACK);
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		Lobo_bt = new JButton("Coger Lobo");
		Lobo_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Lobo_bt.getText() == "Coger Lobo") {
					accion=true;
					cliente.enviar("coger_lobo");
				}
				else {
					accion=true;
					cliente.enviar("soltar_lobo");
				}
			}
		});
		Lobo_bt.setBounds(74, 38, 150, 50);
		add(Lobo_bt);
		
		Dragon_bt = new JButton("Coger Dragon");
		Dragon_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Dragon_bt.getText() == "Coger Dragon") {
					accion=true;
					cliente.enviar("coger_dragon");
				}
				else {
					accion=true;
					cliente.enviar("soltar_dragon");
				}
			}
		});
		Dragon_bt.setBounds(74, 116, 150, 50);
		add(Dragon_bt);

	}
	
	public void mostrarMensaje(final String texto) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if(accion) {
					switch (texto){
		        	case "coger_dragon":
		        		Dragon_bt.setText("Soltar Dragon");
		        		break;
		        	case "coger_lobo":
		        		Lobo_bt.setText("Soltar Lobo");
		        		break;
		        	case "soltar_dragon":
		        		Dragon_bt.setText("Coger Dragon");
		        		break;
		        	case "soltar_lobo":
		        		Lobo_bt.setText("Coger Lobo");
		        		break;
		        	}
					accion=false;
				}
				else {
					switch (texto){
		        	case "coger_dragon":
		        		Dragon_bt.setEnabled(false);
		        		break;
		        	case "coger_lobo":
		        		Lobo_bt.setEnabled(false);
		        		break;
		        	case "soltar_dragon":
		        		Dragon_bt.setEnabled(true);
		        		break;
		        	case "soltar_lobo":
		        		Lobo_bt.setEnabled(true);
		        		break;
		        	}
				}
			}
		});
	}
}
