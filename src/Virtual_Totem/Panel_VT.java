package Virtual_Totem;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
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
	
	private Info_VT info;
	private Alert_VT alert;
	private String set_name;
	private Client_VT client;
	static final long serialVersionUID = 42L;
	private boolean isAction, unlock_dragon, unlock_wolf, warning_exit;
	private JButton Wolf_bt, Dragon_bt, Info_bt, wolf_alert, dragon_alert;
	

	public Panel_VT(Client_VT client) {
		
		this.client=client;
		client.associate(this);
		info = new Info_VT(Panel_VT.this);
		alert = new Alert_VT();
		setLayout(null);
		
		//------------------------------------Buttons------------------------------------------//
		Wolf_bt = new JButton("Coger Lobo");
		Wolf_bt.setBounds(74, 25, 150, 50);
		Wolf_bt.setForeground(Color.WHITE);
		Wolf_bt.setFont(new Font("Yu Gothic", Font.BOLD, 16));
		Wolf_bt.setVerticalTextPosition(SwingConstants.CENTER);
		Wolf_bt.setHorizontalTextPosition(SwingConstants.CENTER);
		Wolf_bt.setBorder(BorderFactory.createLineBorder(new Color(79,202,217), 2));
		Wolf_bt.setIcon(new ImageIcon(Panel_VT.class.getResource("/Img/bt_Wolf_bt.jpg")));
		Wolf_bt.setDisabledIcon(new ImageIcon(Panel_VT.class.getResource("/Img/bt_Wolf_bt_disabled.jpg")));
		add(Wolf_bt);
		
		Dragon_bt = new JButton("Coger Dragon");		
		Dragon_bt.setForeground(Color.WHITE);
		Dragon_bt.setBounds(74, 95, 150, 50);
		Dragon_bt.setFont(new Font("Yu Gothic", Font.BOLD, 16));
		Dragon_bt.setVerticalTextPosition(SwingConstants.CENTER);
		Dragon_bt.setHorizontalTextPosition(SwingConstants.CENTER);
		Dragon_bt.setBorder(BorderFactory.createLineBorder(new Color(232,183,169), 2));
		Dragon_bt.setIcon(new ImageIcon(Panel_VT.class.getResource("/Img/bt_dragon.jpg")));
		Dragon_bt.setDisabledIcon(new ImageIcon(Panel_VT.class.getResource("/Img/bt_dragon_disabled.jpg")));
		add(Dragon_bt);
		
		Info_bt = new JButton("");
		Info_bt.setBorder(null);
		Info_bt.setOpaque(false);
		Info_bt.setBounds(133, 154, 32, 32);
		Info_bt.setBackground(Color.LIGHT_GRAY);
		Info_bt.setMargin(new Insets(0, 0, 0, 0));
		Info_bt.setIcon(new ImageIcon(Panel_VT.class.getResource("/javax/swing/plaf/metal/icons/ocean/warning.png")));
		add(Info_bt);
		
		wolf_alert = new JButton("");
		wolf_alert.setBorder(null);
		wolf_alert.setOpaque(false);
		wolf_alert.setEnabled(false);
		wolf_alert.setBounds(233, 36, 32, 32);
		wolf_alert.setBackground(Color.LIGHT_GRAY);
		wolf_alert.setMargin(new Insets(0, 0, 0, 0));
		wolf_alert.setIcon(new ImageIcon(Panel_VT.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		add(wolf_alert);
		
		dragon_alert = new JButton("");
		dragon_alert.setBorder(null);
		dragon_alert.setOpaque(false);
		dragon_alert.setEnabled(false);
		dragon_alert.setBounds(233, 106, 32, 32);
		dragon_alert.setBackground(Color.LIGHT_GRAY);
		dragon_alert.setMargin(new Insets(0, 0, 0, 0));
		dragon_alert.setIcon(new ImageIcon(Panel_VT.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		add(dragon_alert);
		
		//------------------------------------Labels------------------------------------------//
		
		JLabel background = new JLabel("New label");
		background.setBounds(0, 0, 299, 212);
		background.setIcon(new ImageIcon(Panel_VT.class.getResource("/Img/Background_main.png")));
		add(background);
		
		//------------------------------------Initialize Variables--------------------------------//
		initialize();
		listeners();
	}
	
	public void listeners() {
		
		Wolf_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Wolf_bt.getText() == "Coger Lobo") {
					isAction=true;
					client.enviar("coger_lobo,"+System.getProperty("user.name"));
				}
				else {
					isAction=true;
					client.enviar("soltar_lobo");
				}
			}
		});
		
		Dragon_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Dragon_bt.getText() == "Coger Dragon") {
					isAction=true;
					client.enviar("coger_dragon,"+System.getProperty("user.name"));
				}
				else {
					isAction=true;
					client.enviar("soltar_dragon");
				}
			}
		});
		
		Info_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				info.showIt(Panel_VT.this.is_totem_taked("lobo"), Panel_VT.this.is_totem_taked("dragon"));
			}
		});
		
		wolf_alert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alert.showIt("wolf");
			}
		});
		
		dragon_alert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alert.showIt("dragon");
			}
		});
	}
	
	public void initialize(){
		isAction=false;
		set_name="";
		unlock_dragon=false;
		unlock_wolf=false;
		warning_exit=false;
	}
	
	public void showMsg(final String msg) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				String forced_name="";
				String text=msg;
				if(text.indexOf(",") != -1) {
					String[] parts = msg.split(",");
					text = parts[0]; 
					forced_name = parts[1]; 
				}
				
				//------------------------------------I did the action--------------------------------//
				if(isAction) {
					switch (text){
		        	case "coger_dragon":
		        		Dragon_bt.setText("Soltar Dragon");
		        		warning_exit=true;
		        		break;
		        	case "coger_lobo":
		        		Wolf_bt.setText("Soltar Lobo");
		        		warning_exit=true;
		        		break;
		        	case "soltar_dragon":
		        		Dragon_bt.setText("Coger Dragon");
		        		if(Wolf_bt.getText()!="Soltar Lobo") {
		        			warning_exit=false;
		        		}
		        		break;
		        	case "soltar_lobo":
		        		Wolf_bt.setText("Coger Lobo");
		        		Wolf_bt.setForeground(Color.WHITE);
		        		if(Dragon_bt.getText()!="Soltar Dragon") {
		        			warning_exit=false;
		        		}
		        		break;
		        	}
					isAction=false;
				}
				
				//------------------------------------I received the action--------------------------------//
				else {
					switch (text){
		        	case "coger_dragon":
		        		Dragon_bt.setEnabled(false);
		        		dragon_alert.setEnabled(true);
		        		unlock_dragon=true;
		        		Dragon_bt.setFont(new Font("Yu Gothic", Font.BOLD, 14));
	        			Dragon_bt.setText("<html><font color = black>Dragon: " + forced_name+"</html>");
		        		break;
		        	case "coger_lobo":
		        		Wolf_bt.setEnabled(false);
		        		wolf_alert.setEnabled(true);
		        		unlock_wolf=true;
		        		Wolf_bt.setFont(new Font("Yu Gothic", Font.BOLD, 14));
	        			Wolf_bt.setText("<html><font color = white>Lobo: " + forced_name+"</html>");
		        		break;
		        	case "soltar_dragon":
		        		if (Dragon_bt.getText() == "Soltar Dragon") {
		        			show_error("dragon",forced_name);
		        		}
		        		Dragon_bt.setEnabled(true);
		        		dragon_alert.setEnabled(false);
		        		Dragon_bt.setFont(new Font("Yu Gothic", Font.BOLD, 16));
		        		unlock_dragon=false;
		        		Dragon_bt.setText("Coger Dragon");
		        		break;
		        	case "soltar_lobo":
		        		if (Wolf_bt.getText() == "Soltar Lobo") {
		        			show_error("lobo",forced_name);
		        		}
		        		Wolf_bt.setEnabled(true);
		        		wolf_alert.setEnabled(false);
		        		Wolf_bt.setFont(new Font("Yu Gothic", Font.BOLD, 14));
		        		unlock_wolf=false;
		        		Wolf_bt.setText("Coger Lobo");
		        		break;
		        	}
				}
			}
		});
	}
	
	public void send(String text) {
		System.out.println("isAction recibida"+text);
		client.enviar(text);
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
	
	public void show_error(String totem, String forced_name) {
		warning_exit=false;
		JOptionPane.showMessageDialog(null,"El usuario " +forced_name+ " a forzado la liberación del " + totem);
	}
}
