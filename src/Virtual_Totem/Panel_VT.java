package Virtual_Totem;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Insets;

import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

import Virtual_Totem.Client_VT;
import javafx.scene.layout.Border;

import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.Font;


public class Panel_VT extends JPanel {
	
	static final long serialVersionUID = 42L;
	private JButton Lobo_bt;
	private JButton Dragon_bt;
	private boolean accion;
	private String set_name;
	private Client_VT cliente;
	private Info_VT info;
	private boolean unlock_dragon;
	private boolean unlock_wolf;
	private boolean warning_exit;
	
	public Panel_VT(Client_VT client) {
		
		cliente=client;
		cliente.asociar(this);
		accion=false;
		set_name="";
		unlock_dragon=false;
		unlock_wolf=false;
		warning_exit=false;

		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setForeground(Color.BLACK);
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		info = new Info_VT(Panel_VT.this);
		
		Lobo_bt = new JButton("Coger Lobo");
		Lobo_bt.setFont(new Font("Yu Gothic", Font.BOLD, 16));
		Lobo_bt.setIcon(new ImageIcon(Panel_VT.class.getResource("/Img/bt_lobo_bt.jpg")));
		Lobo_bt.setForeground(Color.WHITE);
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
		Lobo_bt.setBounds(74, 25, 150, 50);
		Lobo_bt.setHorizontalTextPosition( SwingConstants.CENTER );
		Lobo_bt.setVerticalTextPosition( SwingConstants.CENTER );
		Lobo_bt.setBorder(BorderFactory.createLineBorder(new Color(79,202,217), 2));
		//Lobo_bt.setDisabledIcon(new ImageIcon(Panel_VT.class.getResource("/Img/bt_lobo_bt.jpg")));
		add(Lobo_bt);
		
		Dragon_bt = new JButton("Coger Dragon");		
		Dragon_bt.setFont(new Font("Yu Gothic", Font.BOLD, 16));		
		Dragon_bt.setIcon(new ImageIcon(Panel_VT.class.getResource("/Img/bt_dragon.jpg")));
		Dragon_bt.setForeground(Color.WHITE);
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
		Dragon_bt.setBounds(74, 95, 150, 50);
		Dragon_bt.setHorizontalTextPosition( SwingConstants.CENTER );
		Dragon_bt.setVerticalTextPosition( SwingConstants.CENTER );
		Dragon_bt.setBorder(BorderFactory.createLineBorder(new Color(232,183,169), 2));
		add(Dragon_bt);
		
		JButton Info_bt = new JButton("");
		Info_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				info.showIt(Panel_VT.this.is_totem_taked("lobo"), Panel_VT.this.is_totem_taked("dragon"));
			}
		});
		Info_bt.setBackground(Color.LIGHT_GRAY);
		Info_bt.setOpaque(false);
		Info_bt.setMargin(new Insets(0, 0, 0, 0));
		Info_bt.setBorder(null);
		Info_bt.setIcon(new ImageIcon(Panel_VT.class.getResource("/javax/swing/plaf/metal/icons/ocean/warning.png")));
		Info_bt.setBounds(133, 154, 32, 32);
		add(Info_bt);
		
		JLabel background = new JLabel("New label");
		background.setIcon(new ImageIcon(Panel_VT.class.getResource("/Img/Background_main.png")));
		background.setBounds(0, 0, 299, 212);
		add(background);
		
		
	}
	
	public void mostrarMensaje(final String texto) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if(accion) {
					switch (texto){
		        	case "coger_dragon":
		        		Dragon_bt.setText("Soltar Dragon");
		        		warning_exit=true;
		        		break;
		        	case "coger_lobo":
		        		Lobo_bt.setText("Soltar Lobo");
		        		warning_exit=true;
		        		break;
		        	case "soltar_dragon":
		        		Dragon_bt.setText("Coger Dragon");
		        		warning_exit=false;
		        		break;
		        	case "soltar_lobo":
		        		Lobo_bt.setText("Coger Lobo");
		        		warning_exit=false;
		        		break;
		        	}
					accion=false;
				}
				else {
					switch (texto){
		        	case "coger_dragon":
		        		Dragon_bt.setEnabled(false);
		        		unlock_dragon=true;
		        		set_name="dragon";
		        		break;
		        	case "coger_lobo":
		        		Lobo_bt.setEnabled(false);
		        		unlock_wolf=true;
		        		set_name="lobo";
		        		break;
		        	case "soltar_dragon":
		        		if (Dragon_bt.getText() == "Soltar Dragon") {
		        			show_error("dragon");
		        		}
		        		Dragon_bt.setEnabled(true);
		        		unlock_dragon=false;
		        		Dragon_bt.setText("Coger Dragon");
		        		break;
		        	case "soltar_lobo":
		        		if (Lobo_bt.getText() == "Soltar Lobo") {
		        			show_error("lobo");
		        		}
		        		Lobo_bt.setEnabled(true);
		        		unlock_wolf=false;
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
	
	public boolean is_totem_taked(String totem) {
		if(totem == "lobo") {
			return unlock_wolf;
		}
		else if (totem == "dragon") {
			return unlock_dragon;
		}
		return false;
	}
	
	public boolean can_exit() {
		return warning_exit;
	}
	public void show_error(String totem) {
		warning_exit=false;
		JOptionPane.showMessageDialog(null,"Otro usuario a forzado la liberación del " + totem);
	}
}
