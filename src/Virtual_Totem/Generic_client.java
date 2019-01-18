package Virtual_Totem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sun.applet.Main;


public class Generic_client extends JPanel {
	
	protected Info_VT info;
	protected Alert_VT alert;
	protected Client_VT client;
	protected String myName, clientName;
	protected Window_VT window;
	static final long serialVersionUID = 42L;
	protected boolean dragon_blocked, wolf_blocked, ImAlive;
	protected JButton Wolf_bt, Dragon_bt, Info_bt, wolf_alert, dragon_alert;
	protected JLabel wolf_time_lb, dragon_time_lb;
	protected Integer time_wolf[], time_dragon[];
	protected JLabel clientIcon;

	public Generic_client(Client_VT client, Window_VT window, String clientName) {
		
		//------------------------------Develop mode--------------------------------
		myName=(System.getProperty("user.name")+String.valueOf(Math.floor(Math.random()*999)));
		//---------------------------------------------------------------------------
//		myName=System.getProperty("user.name");
		
		this.client=client;
		this.window=window;
		this.clientName=clientName;
		client.associate(this);
		info = new Info_VT(Generic_client.this);
		alert = new Alert_VT(this,myName);
		setLayout(null);
		
		//------------------------------------Buttons------------------------------------------//
		Wolf_bt = new JButton("Coger Lobo");
		Wolf_bt.setBounds(74, 25, 150, 50);
		Wolf_bt.setForeground(Color.WHITE);
		Wolf_bt.setFont(new Font("Yu Gothic", Font.BOLD, 16));
		Wolf_bt.setVerticalTextPosition(SwingConstants.CENTER);
		Wolf_bt.setHorizontalTextPosition(SwingConstants.CENTER);
		Wolf_bt.setBorder(BorderFactory.createLineBorder(new Color(79,202,217), 2));
		Wolf_bt.setIcon(new ImageIcon(Generic_client.class.getResource("/Img/bt_Wolf_bt.jpg")));
		Wolf_bt.setDisabledIcon(new ImageIcon(Generic_client.class.getResource("/Img/bt_Wolf_bt_disabled.jpg")));
		add(Wolf_bt);
		
		Dragon_bt = new JButton("Coger Dragon");		
		Dragon_bt.setForeground(Color.WHITE);
		Dragon_bt.setBounds(74, 95, 150, 50);
		Dragon_bt.setFont(new Font("Yu Gothic", Font.BOLD, 16));
		Dragon_bt.setVerticalTextPosition(SwingConstants.CENTER);
		Dragon_bt.setHorizontalTextPosition(SwingConstants.CENTER);
		Dragon_bt.setBorder(BorderFactory.createLineBorder(new Color(232,183,169), 2));
		Dragon_bt.setIcon(new ImageIcon(Generic_client.class.getResource("/Img/bt_dragon.jpg")));
		Dragon_bt.setDisabledIcon(new ImageIcon(Generic_client.class.getResource("/Img/bt_dragon_disabled.jpg")));
		add(Dragon_bt);
		
		Info_bt = new JButton("");
		Info_bt.setBorder(null);
		Info_bt.setOpaque(false);
		Info_bt.setBounds(133, 169, 32, 32);
		Info_bt.setBackground(Color.LIGHT_GRAY);
		Info_bt.setMargin(new Insets(0, 0, 0, 0));
		Info_bt.setIcon(new ImageIcon(Generic_client.class.getResource("/javax/swing/plaf/metal/icons/ocean/warning.png")));
		add(Info_bt);
		
		wolf_alert = new JButton("");
		wolf_alert.setBorder(null);
		wolf_alert.setOpaque(false);
		wolf_alert.setBounds(233, 36, 32, 32);
		wolf_alert.setBackground(Color.LIGHT_GRAY);
		wolf_alert.setMargin(new Insets(0, 0, 0, 0));
		wolf_alert.setIcon(new ImageIcon(Generic_client.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		add(wolf_alert);
		
		dragon_alert = new JButton("");
		dragon_alert.setBorder(null);
		dragon_alert.setOpaque(false);
		dragon_alert.setBounds(233, 106, 32, 32);
		dragon_alert.setBackground(Color.LIGHT_GRAY);
		dragon_alert.setMargin(new Insets(0, 0, 0, 0));
		dragon_alert.setIcon(new ImageIcon(Generic_client.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		add(dragon_alert);
		
		wolf_time_lb = new JLabel("");
		wolf_time_lb.setBackground(Color.BLACK);
		wolf_time_lb.setFont(new Font("Tahoma", Font.BOLD, 11));
		wolf_time_lb.setForeground(Color.BLACK);
		wolf_time_lb.setHorizontalAlignment(SwingConstants.CENTER);
		wolf_time_lb.setBounds(74, 75, 150, 15);
		add(wolf_time_lb);
		
		dragon_time_lb = new JLabel("");
		dragon_time_lb.setHorizontalAlignment(SwingConstants.CENTER);
		dragon_time_lb.setForeground(Color.BLACK);
		dragon_time_lb.setFont(new Font("Tahoma", Font.BOLD, 11));
		dragon_time_lb.setBackground(Color.BLACK);
		dragon_time_lb.setBounds(74, 145, 150, 15);
		add(dragon_time_lb);
		
		JLabel Version_lb = new JLabel("Versi\u00F3n DEV");
		Version_lb.setFont(new Font("Yu Gothic", Font.BOLD, 11));
		Version_lb.setBounds(10, 195, 120, 14);
		add(Version_lb);
		
		clientIcon = new JLabel("");
		clientIcon.setBounds(10, 63, 50, 50);
		add(clientIcon);
		
		//------------------------------------Labels------------------------------------------//
		
		JLabel background = new JLabel("New label");
		background.setBounds(0, 0, 299, 221);
		background.setIcon(new ImageIcon(Generic_client.class.getResource("/Img/Background_main.png")));
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
					Wolf_bt.setBorder(BorderFactory.createLineBorder(new Color(254,0,0), 5));
					send("totem,coger_lobo,"+myName);
				}
				else {
					blockWolf("");
					new CheckConexiones("soltar_lobo",Generic_client.this);
				}
			}
		});
		
		Dragon_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Dragon_bt.getText() == "Coger Dragon") {
					Dragon_bt.setText("Soltar Dragon");
					Dragon_bt.setBorder(BorderFactory.createLineBorder(new Color(254,0,0), 5));
					send("totem,coger_dragon,"+myName);
				}
				else {
	        		blockDragon("");
	        		new CheckConexiones("soltar_dragon",Generic_client.this);
				}
			}
		});
		
		Info_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				info.showIt(is_totem_taked("lobo"), is_totem_taked("dragon"));
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
		dragon_blocked=false;
		wolf_blocked=false;
		ImAlive=false;
		time_wolf = new Integer[3];
        time_wolf[0]=1;
        time_wolf[1]=0;
        time_wolf[2]=0;
        time_dragon = new Integer[3];
        time_dragon[0]=1;
        time_dragon[1]=0;
        time_dragon[2]=0;
	}
	
	public String getClientName() {
		return clientName;
	}
	
	public void freeWolf(String user_name) {
		
		if (Wolf_bt.getText() == "Soltar Lobo") {
			show_error("lobo",user_name);
		}
		Wolf_bt.setEnabled(true);
		alert.changeActiveOkButton("wolf", false);
		Wolf_bt.setFont(new Font("Yu Gothic", Font.BOLD, 16));
		Wolf_bt.setBorder(BorderFactory.createLineBorder(new Color(79,202,217), 2));
		wolf_blocked=false;
		Wolf_bt.setText("Coger Lobo");
		
	}
	
	public void freeDragon(String user_name) {
		
		if (Dragon_bt.getText() == "Soltar Dragon") {
			show_error("dragon",user_name);
		}
		Dragon_bt.setEnabled(true);
		alert.changeActiveOkButton("dragon", false);
		Dragon_bt.setFont(new Font("Yu Gothic", Font.BOLD, 16));
		Dragon_bt.setBorder(BorderFactory.createLineBorder(new Color(232,183,169), 2));
		dragon_blocked=false;
		Dragon_bt.setText("Coger Dragon");
		
	}
	
	public void blockDragon(String user_name) {
		
		Dragon_bt.setEnabled(false);
		alert.changeActiveOkButton("dragon", true);
		dragon_blocked=true;
		if(time_dragon[0]+time_dragon[1]+time_dragon[2] == 1) { //If the time is not already running
			time("dragon");
		}
		else {
			time_dragon[0]=1;
	        time_dragon[1]=0;
	        time_dragon[2]=0;
		}
		Dragon_bt.setFont(new Font("Yu Gothic", Font.BOLD, 14));
		Dragon_bt.setBorder(BorderFactory.createLineBorder(new Color(232,183,169), 2));
		if(user_name.equals("")) {
			Dragon_bt.setText("En transición");
		}
		else {
			Dragon_bt.setText("<html><font color = black>Dragon: " + user_name+"</html>");
		}
		
	}
	
	public void blockWolf(String user_name) {
		
		Wolf_bt.setEnabled(false);
		alert.changeActiveOkButton("wolf", true);
		wolf_blocked=true;
		System.out.println("La hora con la que entro al bucle es: "+time_wolf[2]+":"+time_wolf[1]+":"+time_wolf[0]);
		if(time_wolf[0]+time_wolf[1]+time_wolf[2] == 1) { //If the time is not already running
			time("wolf");
		}
		else {
			time_wolf[0]=1;
			time_wolf[1]=0;
			time_wolf[2]=0;
		}
		Wolf_bt.setFont(new Font("Yu Gothic", Font.BOLD, 14));
		Wolf_bt.setBorder(BorderFactory.createLineBorder(new Color(79,202,217), 2));
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
			if(!"ACK".equals(parts[0])) {
				if(clientName.equals(parts[3])) {
					switch(parts[0]){
						case "totem":
							showMsg(parts[1],parts[2],parts[3]);
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
							showMsg(parts[1],parts[2],parts[3]);
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
							
						case "time":
							
								System.out.println("Hora recibida: "+parts[2]);
								String[] times = parts[2].split(":");
								if(parts[1].equals("wolf")) {
									time_wolf[0]=Integer.valueOf(times[2]);
							        time_wolf[1]=Integer.valueOf(times[1]);
							        time_wolf[2]=Integer.valueOf(times[0]);
								}
								else if(parts[1].equals("dragon")) {
									time_dragon[0]=Integer.valueOf(times[2]);
									time_dragon[1]=Integer.valueOf(times[1]);
									time_dragon[2]=Integer.valueOf(times[0]);
								}
							
		    				break;
						case "freeTotem":
							chekList(parts[1]+","+parts[3],"");
		    			default:
		    				chekList(msg,"");
					}
				}
			}
		}
	}
	
	public void chekList(String action, String name) {
		
		if(alert.getUser(action).equals(myName)) {
			
			String totem="";
			String okAction="";
			if (action.equals("soltar_lobo,"+clientName)) {
				send("CleanList,wolf,0");
				totem="lobo";
				okAction="coger_lobo";
				promt("free_wolf");
			}
			if (action.equals("soltar_dragon,"+clientName)) {
				send("CleanList,dragon,0");
				totem="dragon";
				okAction="coger_dragon";
				promt("free_dragon");
			}
			window.msgTry("Se acaba de liberar el " +totem+ " y eres el siguiente en la cola.");
			String ObjButtons[] = {"Yes","No"};
	        int PromptResult = JOptionPane.showOptionDialog(null,"Se acaba de liberar el " +totem+ " y eres el siguiente en la cola. ¿Quieres cogerlo?",
	        		"Liberación del Totem",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
	        if(PromptResult==JOptionPane.YES_OPTION)
	        {
	        	if (action.equals("soltar_lobo,"+clientName)) {
	        		freeWolf(name);
	        		Wolf_bt.setText("Soltar Lobo");
					Wolf_bt.setBorder(BorderFactory.createLineBorder(new Color(254,0,0), 5));
				}
				if (action.equals("soltar_dragon,"+clientName)) {
					freeDragon(name);
					Dragon_bt.setText("Soltar Dragon");
					Dragon_bt.setBorder(BorderFactory.createLineBorder(new Color(254,0,0), 5));
				}
				send("totem,"+okAction+","+myName);
	        }
	        else {
	        	send(action);
	        }
			
		}
		else if(alert.getUser(action).equals("empty")) {
			showMsg(action,name,"");
		}
	}
	
	public void showMsg(String text, String user_name, String client) {
				
		if("coger_dragon".equals(text) && clientName.equals(client)){
			if(!Dragon_bt.getText().equals("Soltar Dragon")) {
				blockDragon(user_name);
			}
		}
		else if("coger_lobo".equals(text) && clientName.equals(client)){
			if(!Wolf_bt.getText().equals("Soltar Lobo")) {
				blockWolf(user_name);
			}
		}
		else if(("soltar_dragon,"+clientName).equals(text)){
			freeDragon(user_name);
		}
		else if(("soltar_lobo,"+clientName).equals(text)){
			freeWolf(user_name);
		}
	}
	
	public void promt(String wav) {
		if(!window.isSilence()) {
			URL audio = Main.class.getResource("/Sounds/"+wav+".wav");
			if(audio==null) {
				audio = Main.class.getResource("/Sounds/user_default.wav");
			}
			Clip clip;
			try {
				clip = AudioSystem.getClip();
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(audio);
				clip.addLineListener(event -> {
			        if (event.getType().equals(LineEvent.Type.START)) {
			        }
			    });
		        clip.open(inputStream);
		        clip.start();
			} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
				System.out.println("No se ha podido reproducir el archivo de: "+wav);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void promt(String wav, String wav2) {
		if(!window.isSilence()) {
			URL audio = Main.class.getResource("/Sounds/"+wav+".wav");
			if(audio==null) {
				audio = Main.class.getResource("/Sounds/user_default.wav");
			}
			URL audio2 = Main.class.getResource("/Sounds/"+wav2+".wav");
			Clip clip, clip2;
			try {
				CountDownLatch go = new CountDownLatch(1);
				clip = AudioSystem.getClip();
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(audio);
				clip.addLineListener(event -> {
			        if (event.getType().equals(LineEvent.Type.START)) {
			            go.countDown();
			        }
			    });
		        clip.open(inputStream);
		        clip.start();
		        go.await();
		        while(clip.isActive()) {
		        	Thread.sleep(100);
		        }
			} catch (LineUnavailableException | UnsupportedAudioFileException | IOException | InterruptedException e) {
				System.out.println("No se ha podido reproducir el archivo de: "+wav);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				clip2 = AudioSystem.getClip();
				AudioInputStream inputStream2 = AudioSystem.getAudioInputStream(audio2);
		        clip2.open(inputStream2);
		        clip2.start();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
}
	
	public void send(String text) {
		System.out.println("We are sending: "+text+","+clientName);
		client.enviar(text+","+clientName);
	}
	
	public boolean is_totem_taked(String totem) {
		if(totem == "lobo") {
			return wolf_blocked;
		}
		else if (totem == "dragon") {
			return dragon_blocked;
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
		window.msgTry("El usuario " +user_name+ " ha forzado la liberación del " + totem);
		if(totem.equals("lobo")) {
			promt(user_name,"force_wolf");
		}
		else if(totem.equals("dragon")) {
			promt(user_name,"force_dragon");
		}
		JOptionPane.showMessageDialog(null,"El usuario " +user_name+ " ha forzado la liberación del " + totem);
	}
	
	public void show_alert(String totem, String user_name) {
		window.msgTry("El usuario " +user_name+ " está solicitando el " + totem);
		if(totem.equals("lobo")) {
			promt(user_name,"asking_wolf");
		}
		else if(totem.equals("dragon")) {
			promt(user_name,"asking_dragon");
		}
		JOptionPane.showMessageDialog(null,"El usuario " +user_name+ " está solicitando el " + totem);
	}
	
	public Alert_VT getAlert_VT() {
		return alert;
	}
	
	public void time(String totem) {
		Thread t1 = new Thread(new Runnable() {
	         public void run() {
	        	 try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
	        	 if(totem.equals("dragon")) {
	        		 while(dragon_blocked) {
	        			 
	        			 if(time_dragon[0]==60) {
	        				 time_dragon[0]=0;
	        				 time_dragon[1]+=1;
	        			 }
	        			 if(time_dragon[1]==60) {
	        				 time_dragon[1]=0;
	        				 time_dragon[2]+=1;
	        			 }
	        			 if(time_dragon[2] != 0) {
	        				 dragon_time_lb.setText("Retenido: "+ String.valueOf(time_dragon[2]) + " h " + String.valueOf(time_dragon[1]) + " min "+ String.valueOf(time_dragon[0])+ " seg");
	        			 }
	        			 else if(time_dragon[1] !=0) {
	        				 dragon_time_lb.setText("Retenido: "+ String.valueOf(time_dragon[1]) + " min "+ String.valueOf(time_dragon[0])+ " seg");
	        			 }
	        			 else {
	        				 dragon_time_lb.setText("Retenido: "+ String.valueOf(time_dragon[0])+ " seg");
	        			 }
	        			 time_dragon[0]++;
			        	 try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        	 }
	        		 time_dragon[0]=1;
	        		 time_dragon[1]=0;
	        		 time_dragon[2]=0;
	        		 dragon_time_lb.setText("");
	        	 }
	        	 else if (totem.equals("wolf")) {
	        		 while(wolf_blocked) {
	        			 if(time_wolf[0]==60) {
	        				 time_wolf[0]=0;
	        				 time_wolf[1]+=1;
	        			 }
	        			 if(time_wolf[1]==60) {
	        				 time_wolf[1]=0;
	        				 time_wolf[2]+=1;
	        			 }
	        			 if(time_wolf[2] != 0) {
	        				 wolf_time_lb.setText("Retenido: "+ String.valueOf(time_wolf[2]) + " h " + String.valueOf(time_wolf[1]) + " min "+ String.valueOf(time_wolf[0])+ " seg");
	        			 }
	        			 else if(time_wolf[1] !=0) {
	        				 wolf_time_lb.setText("Retenido: "+ String.valueOf(time_wolf[1]) + " min "+ String.valueOf(time_wolf[0])+ " seg");
	        			 }
	        			 else {
	        				 wolf_time_lb.setText("Retenido: "+ String.valueOf(time_wolf[0])+ " seg");
	        			 }
	        			 time_wolf[0]++;
			        	 try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        	 }
	        		 time_wolf[0]=1;
	        		 time_wolf[1]=0;
	        		 time_wolf[2]=0;
	        		 wolf_time_lb.setText("");
	        	 }
	         }
	    });  
	    t1.start();
		
	}
	
	protected class CheckConexiones{
    	
    	public CheckConexiones(String action, Generic_client panel) {
            new Thread() {
                public void run() {
                	String user=alert.getUser(action+","+clientName);
                	boolean CanGo=false;
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
	                		if (action.equals("soltar_lobo,"+clientName))	panel.send("CleanList,wolf,0");
	        				if (action.equals("soltar_dragon,"+clientName))	panel.send("CleanList,dragon,0");
	        				try {
	        					//Wait 2 second to let enough time to the server to read the last message and send us the CleanList back.
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
	                		user=alert.getUser(action+","+clientName);
	                	}
        			}
        			ImAlive=false;
        			send("freeTotem,"+action+","+myName);
                }
            }.start();
        }
    }
}
