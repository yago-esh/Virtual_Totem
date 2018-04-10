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
	private Client_VT client;
	private String check_user;
	static final long serialVersionUID = 42L;
	private boolean isAction, unlock_dragon, unlock_wolf, warning_exit, CanGo, ImAlive;
	private JButton Wolf_bt, Dragon_bt, Info_bt, wolf_alert, dragon_alert;
	

	public Panel_VT(Client_VT client) {
		
		this.client=client;
		client.associate(this);
		info = new Info_VT(Panel_VT.this);
		alert = new Alert_VT(this);
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
		
		JLabel Version_lb = new JLabel("Versi\u00F3n 1.6.0");
		Version_lb.setFont(new Font("Yu Gothic", Font.BOLD, 11));
		Version_lb.setBounds(10, 180, 120, 14);
		add(Version_lb);
		
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
					client.enviar("totem,coger_lobo,"+System.getProperty("user.name"));
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
					client.enviar("totem,coger_dragon,"+System.getProperty("user.name"));
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
				alert.showIt("coger_lobo");
			}
		});
		
		dragon_alert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alert.showIt("coger_dragon");
			}
		});
	}
	
	public void initialize(){
		isAction=false;
		unlock_dragon=false;
		unlock_wolf=false;
		warning_exit=false;
		CanGo=false;
		ImAlive=false;
		check_user="";
	}
	
	public void ParseMsg(String msg){
		System.out.println("Hemos recibido: "+msg);
		if(msg.indexOf(",") != -1) {
			
			String[] parts = msg.split(",");
			
			switch(parts[0]){
				case "totem":
					showMsg(parts[1],parts[2]);
					break;
				case "list":
						alert.addList(parts[1],parts[2]);
						switch (parts[1]){
			        	case "coger_dragon":
			        		if (Dragon_bt.getText().equals("Soltar Dragon")) {
			        			show_alert("dragon",parts[2]);
			        		}
			        		break;
			        	case "coger_lobo":
			        		if (Wolf_bt.getText().equals("Soltar Lobo")) {
			        			show_alert("lobo",parts[2]);
			        		}
			        		break;
			        	}
					break;
					
				case "freedom":
					alert.clearList(parts[1]);
					showMsg(parts[1],parts[2]);
					break;
					
				case "CleanList":
					alert.removeNumList(parts[1], Integer.parseInt(parts[2]));
    				break;
    			
				case "isAlive":
					if (parts[1].equals("NO_DATA")){
						System.out.println("llegamos???");
						if(parts[2].equals(System.getProperty("user.name"))) {
							send("isAlive,ImAlive,"+System.getProperty("user.name"));
						}
					}
					else if (parts[1].equals("ImAlive")) {
						ImAlive=true;
						System.out.println("Valor de ImAlive: "+ImAlive);
					}
    			
			}

		}
		else {
			chekList(msg,"");
		}
		
	}
	
	public void chekList(String action, String name) {
		
		if (action.equals("soltar_lobo") || action.equals("soltar_dragon")) {
			
			switch (action){
        	case "soltar_dragon":
        		Dragon_bt.setText("En transición");
        		if(isAction) {
        			Wolf_bt.setForeground(Color.WHITE);
	        		
	        		if(Dragon_bt.getText()!="Soltar Dragon") {
	        			warning_exit=false;
	        		}
	        		Dragon_bt.setEnabled(false);
	        		dragon_alert.setEnabled(true);
	        		unlock_dragon=true;
	        		Dragon_bt.setFont(new Font("Yu Gothic", Font.BOLD, 14));
	        		isAction=false;
				}
        		break;
        	case "soltar_lobo":
        		Wolf_bt.setText("En transición");
        		if(isAction) {
	        		Wolf_bt.setForeground(Color.WHITE);
	        		if(Wolf_bt.getText()!="Soltar Lobo") {
	        			warning_exit=false;
	        		}	       		
	        		Wolf_bt.setEnabled(false);
	        		wolf_alert.setEnabled(true);
	        		unlock_wolf=true;
	        		Wolf_bt.setFont(new Font("Yu Gothic", Font.BOLD, 14));
	        		isAction=false;
				}
        		break;
        	}
			
			new CheckConexiones(action,name,this);
			
		}
		else showMsg(action,name);
	}
	
	public void showMsg(String text, String user_name) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
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
	        			Dragon_bt.setText("<html><font color = black>Dragon: " + user_name+"</html>");
		        		break;
		        	case "coger_lobo":
		        		Wolf_bt.setEnabled(false);
		        		wolf_alert.setEnabled(true);
		        		unlock_wolf=true;
		        		Wolf_bt.setFont(new Font("Yu Gothic", Font.BOLD, 14));
	        			Wolf_bt.setText("<html><font color = white>Lobo: " + user_name+"</html>");
		        		break;
		        	case "soltar_dragon":
		        		if (Dragon_bt.getText() == "Soltar Dragon") {
		        			show_error("dragon",user_name);
		        		}
		        		Dragon_bt.setEnabled(true);
		        		dragon_alert.setEnabled(false);
		        		Dragon_bt.setFont(new Font("Yu Gothic", Font.BOLD, 16));
		        		unlock_dragon=false;
		        		Dragon_bt.setText("Coger Dragon");
		        		break;
		        	case "soltar_lobo":
		        		if (Wolf_bt.getText() == "Soltar Lobo") {
		        			show_error("lobo",user_name);
		        		}
		        		Wolf_bt.setEnabled(true);
		        		wolf_alert.setEnabled(false);
		        		Wolf_bt.setFont(new Font("Yu Gothic", Font.BOLD, 16));
		        		unlock_wolf=false;
		        		Wolf_bt.setText("Coger Lobo");
		        		break;
		        	}
				}
			}
		});
	}
	
	public void send(String text) {
		System.out.println("Enviamos "+text);
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
	
	public boolean cant_exit() {
		return warning_exit;
	}
	
	public boolean warning_wolf() {
		if (Wolf_bt.getText() == "Soltar Lobo") {
			return true;
		}
		return false;
	}
	
	public boolean warning_dragon() {
		if (Dragon_bt.getText() == "Soltar Dragon") {
			return true;
		}
		return false;
	}
	
	public void show_error(String totem, String user_name) {
		warning_exit=false;
		JOptionPane.showMessageDialog(null,"El usuario " +user_name+ " ha forzado la liberación del " + totem);
	}
	
	public void show_alert(String totem, String user_name) {
		JOptionPane.showMessageDialog(null,"El usuario " +user_name+ " está solicitando el " + totem);
	}
	
	public void setAction(String totem) {
		
		switch (totem){

    	case "dragon":
    		Dragon_bt.setEnabled(true);
    		dragon_alert.setEnabled(false);
    		Dragon_bt.setFont(new Font("Yu Gothic", Font.BOLD, 16));
    		unlock_dragon=false;
    		Dragon_bt.setText("Coger Dragon");
    		break;
    	case "lobo":
    		Wolf_bt.setEnabled(true);
    		wolf_alert.setEnabled(false);
    		Wolf_bt.setFont(new Font("Yu Gothic", Font.BOLD, 16));
    		unlock_wolf=false;
    		Wolf_bt.setText("Coger Lobo");
    		break;
    	}
		isAction=true;
	}
	
	public boolean isAction() {
		return isAction;
	}
	
	public Alert_VT getAlert_VT() {
		return alert;
	}
	
	private class CheckConexiones{
    	
    	public CheckConexiones(String action, String name, Panel_VT panel) {
            new Thread() {
                public void run() {
                	int x;
                	check_user=alert.getUser(action);

        			while (!CanGo &&!check_user.equals("empty")) {
        					//Wait 5 seconds to know if the client is disconnected.
	                	for(x=0; x<5; x++) {
	                		send("isAlive,NO_DATA,"+check_user);
	                		try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
	                		System.out.println("for de ImAlive: "+ImAlive);
	                		if(ImAlive) {
	                			CanGo=true;
	                			break;
	                		}
	                	}
	                	if(!CanGo) {
	                		check_user=alert.getUser(action);
	                	}
        			}
        			CanGo=false;
        			ImAlive=false;
        				
        			if(check_user.equals(System.getProperty("user.name"))) {
        				
        					if(!isAction) {
        						new NextInQueue_VT(panel,action);
        					}
        					else {
        						showMsg(action,name);
        					}
        			}
        			else if(check_user.equals("empty")) {
        				showMsg(action,name);
        			}
                }
     
            }.start();
        }
    }
	
}
