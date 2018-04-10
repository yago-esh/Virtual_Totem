package Virtual_Totem;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Insets;

import Virtual_Totem.Client_VT;

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
	private String myName;
	static final long serialVersionUID = 42L;
	private boolean unlock_dragon, unlock_wolf, CanGo, ImAlive;
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
		
		JLabel Version_lb = new JLabel("Versi\u00F3n DEV 1");
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
					Wolf_bt.setText("Soltar Lobo");
					client.enviar("totem,coger_lobo,"+myName);
				}
				else {
					blockWolf("");
		    		checkConexion("soltar_lobo");
				}
			}
		});
		
		Dragon_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Dragon_bt.getText() == "Coger Dragon") {
					Dragon_bt.setText("Soltar Dragon");
					client.enviar("totem,coger_dragon,"+myName);
				}
				else {
	        		blockDragon("");
		    		checkConexion("soltar_dragon");
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
		//------------------------------Develop mode--------------------------------
		myName=(System.getProperty("user.name")+String.valueOf(Math.floor(Math.random()*999)));
		//---------------------------------------------------------------------------
		//myName=System.getProperty("user.name");
		unlock_dragon=false;
		unlock_wolf=false;
		CanGo=false;
		ImAlive=false;
	}
	
	public void checkConexion(String mode) {
		new CheckConexiones(mode,this);
	}
	
	public void freeWolf(String user_name) {
		
		if (Wolf_bt.getText() == "Soltar Lobo") {
			show_error("lobo",user_name);
		}
		Wolf_bt.setEnabled(true);
		wolf_alert.setEnabled(false);
		Wolf_bt.setFont(new Font("Yu Gothic", Font.BOLD, 16));
		unlock_wolf=false;
		Wolf_bt.setText("Coger Lobo");
		
	}
	
	public void freeDragon(String user_name) {
		
		if (Dragon_bt.getText() == "Soltar Dragon") {
			show_error("dragon",user_name);
		}
		Dragon_bt.setEnabled(true);
		dragon_alert.setEnabled(false);
		Dragon_bt.setFont(new Font("Yu Gothic", Font.BOLD, 16));
		unlock_dragon=false;
		Dragon_bt.setText("Coger Dragon");
		
	}
	
	public void blockDragon(String user_name) {
		
		Dragon_bt.setEnabled(false);
		dragon_alert.setEnabled(true);
		unlock_dragon=true;
		Dragon_bt.setFont(new Font("Yu Gothic", Font.BOLD, 14));
		if(user_name.equals("")) {
			Dragon_bt.setText("En transición");
		}
		else {
			Dragon_bt.setText("<html><font color = black>Dragon: " + user_name+"</html>");
		}
	}
	
	public void blockWolf(String user_name) {
		
		Wolf_bt.setEnabled(false);
		wolf_alert.setEnabled(true);
		unlock_wolf=true;
		Wolf_bt.setFont(new Font("Yu Gothic", Font.BOLD, 14));
		if(user_name.equals("")) {
			Wolf_bt.setText("En transición");
		}
		else {
			Wolf_bt.setText("<html><font color = white>Lobo: " + user_name+"</html>");
		}
	}
	public void ParseMsg(String msg){
		System.out.println("Message received: "+msg);
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
						if(parts[2].equals(myName)) {
							send("isAlive,ImAlive,"+myName);
						}
					}
					else if (parts[1].equals("ImAlive")) {
						ImAlive=true;
					}
    			
			}

		}
		else {
			chekList(msg,"");
		}
		
	}
	
	public void chekList(String action, String name) {
		
		if(alert.getUser(action).equals(myName)) {
			if (action.equals("soltar_lobo"))	send("CleanList,wolf,0");
			if (action.equals("soltar_dragon"))	send("CleanList,dragon,0");
			new NextInQueue_VT(this,action);

		}
		else if(alert.getUser(action).equals("empty")) {
			showMsg(action,name);
		}
	}
	
	public void showMsg(String text, String user_name) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				switch (text){
	        	case "coger_dragon":
	        		if(!user_name.equals(myName)) {
	        			blockDragon(user_name);
	        		}
	        		break;
	        	case "coger_lobo":
	        		if(!user_name.equals(myName)) {
	        			blockWolf(user_name);
	        		}
	        		break;
	        	case "soltar_dragon":
	        		freeDragon(user_name);
	        		break;
	        	case "soltar_lobo":
	        		freeWolf(user_name);
	        		break;
	        	}
			}
		});
	}
	
	public void send(String text) {
		System.out.println("We are sending: "+text);
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
		return (warning_wolf() || warning_dragon());
	}
	
	public boolean warning_wolf() {
		if (Wolf_bt.getText().equals("Soltar Lobo")) {
			return true;
		}
		return false;
	}
	
	public boolean warning_dragon() {
		if (Dragon_bt.getText().equals("Soltar Dragon")) {
			return true;
		}
		return false;
	}
	
	public void show_error(String totem, String user_name) {
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
	}
	
	
	public Alert_VT getAlert_VT() {
		return alert;
	}
	
	private class CheckConexiones{
    	
    	public CheckConexiones(String action, Panel_VT panel) {
            new Thread() {
                public void run() {
                	String user=alert.getUser(action);

        			while (!CanGo &&!user.equals("empty")) {
        				
        					//Wait 3 seconds to know if the client is disconnected.
	                		send("isAlive,NO_DATA,"+user);
	                		try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
	                		if(ImAlive) {
	                			CanGo=true;
	                			break;
	                		}
	                		
	                	if(!CanGo) {
	                		if (action.equals("soltar_lobo"))	panel.send("CleanList,wolf,0");
	        				if (action.equals("soltar_dragon"))	panel.send("CleanList,dragon,0");
	        				try {
	        					//Wait 2 second to let enough time to the server to read the last message and send us the CleanList back.
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
	                		user=alert.getUser(action);
	                	}
        			}
        			CanGo=false;
        			ImAlive=false;
        			send(action);
                }
     
            }.start();
        }
    }
	
}
