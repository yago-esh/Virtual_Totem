package Virtual_Totem;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Insets;

import javax.swing.border.BevelBorder;
import Virtual_Totem.Client_VT;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;


public class Panel_VT extends JPanel {
	
	static final long serialVersionUID = 42L;
	private JButton Lobo_bt;
	private JButton Dragon_bt;
	private boolean accion;
	private String set_name;
	private Client_VT cliente;
	private Info_VT info;
	
	public Panel_VT(Client_VT client) {
		
		cliente=client;
		cliente.asociar(this);
		accion=false;
		set_name="";
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setForeground(Color.BLACK);
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		info = new Info_VT(Panel_VT.this);
		
		Lobo_bt = new JButton("Coger Lobo");
		Lobo_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Lobo_bt.getText() == "Coger Lobo") {
					accion=true;
					cliente.enviar("coger_lobo");
					cliente.enviar(System.getProperty("user.name"));
				}
				else {
					accion=true;
					cliente.enviar("soltar_lobo");
				}
			}
		});
		Lobo_bt.setBounds(75, 30, 150, 50);
		add(Lobo_bt);
		
		Dragon_bt = new JButton("Coger Dragon");
		Dragon_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Dragon_bt.getText() == "Coger Dragon") {
					accion=true;
					cliente.enviar("coger_dragon");
					cliente.enviar(System.getProperty("user.name"));
				}
				else {
					accion=true;
					cliente.enviar("soltar_dragon");
				}
			}
		});
		Dragon_bt.setBounds(75, 100, 150, 50);
		add(Dragon_bt);
		
		JLabel lblCreatedByYago = new JLabel("Created by Yago Echave-Sustaeta");
		lblCreatedByYago.setBounds(30, 170, 195, 16);
		add(lblCreatedByYago);
		
		JButton Info_bt = new JButton("");
		Info_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				info.setVisible(true);
				info.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				
				
			}
		});
		Info_bt.setBackground(Color.LIGHT_GRAY);
		Info_bt.setMargin(new Insets(0, 0, 0, 0));
		Info_bt.setBorder(null);
		Info_bt.setIcon(new ImageIcon(Panel_VT.class.getResource("/javax/swing/plaf/metal/icons/ocean/warning.png")));
		Info_bt.setBounds(240, 159, 32, 32);
		add(Info_bt);

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
		        		set_name="dragon";
		        		break;
		        	case "coger_lobo":
		        		Lobo_bt.setEnabled(false);
		        		set_name="lobo";
		        		break;
		        	case "soltar_dragon":
		        		Dragon_bt.setEnabled(true);
		        		Dragon_bt.setText("Coger Dragon");
		        		break;
		        	case "soltar_lobo":
		        		Lobo_bt.setEnabled(true);
		        		Lobo_bt.setText("Coger Lobo");
		        		break;
		        	default:
		        		if (set_name == "dragon") {
		        			Dragon_bt.setText("Dragon: " + texto);
		        			set_name="";
		        		}
		        		else if (set_name == "lobo") {
		        			Lobo_bt.setText("Lobo: " + texto);
		        			set_name="";
		        		}
		        		
		        	}
				}
			}
		});
	}
	
	public void send(String texto) {
		System.out.println("Accion recibida"+texto);
		cliente.enviar(texto);
	}
}
