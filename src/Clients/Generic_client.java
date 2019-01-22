package Clients;

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

import Virtual_Totem.InfoPanel;
import Virtual_Totem.Client;
import Virtual_Totem.AlertPanel;
import Virtual_Totem.Window;
import sun.applet.Main;


public class Generic_client extends JPanel {
	
	protected AlertPanel alertPanel;
	protected InfoPanel infoPanel;
	protected Client client;
	protected String myName, clientName, id, totemTopName, totemBotName;
	protected Window window;
	static final long serialVersionUID = 42L;
	protected boolean totemBotTaken, totemTopTaken, ImAlive;
	protected JButton totemTopButton, totemBotButton, alertPanelButton, infoPanelTotemTopButton, infoPanelTotemBotButton;
	protected JLabel totemTopTimerLabel, totemBotTimerLabel;
	protected Integer totemTopTimer[], totemBotTimer[];
	protected JLabel clientIcon;
	private String totemTopForcedAlert = "force_wolf";
	private String totemBotForcedAlert = "force_dragon";

	public Generic_client(Client client, Window window, String clientName, String[] totemNames) {
		
		//------------------------------Develop mode--------------------------------
		myName=(System.getProperty("user.name")+String.valueOf(Math.floor(Math.random()*999)));
//		myName="Cristina";
		//---------------------------------------------------------------------------
//		myName=System.getProperty("user.name");
		
		this.client=client;
		this.window=window;
		this.clientName=clientName;
		this.totemTopName = totemNames[0];
		this.totemBotName = totemNames[1];
		client.associate(this);
		alertPanel = new AlertPanel(Generic_client.this,totemNames);
		infoPanel = new InfoPanel(this,myName, totemNames);
		setLayout(null);
		
		//------------------------------------Buttons------------------------------------------//
		totemTopButton = new JButton("Coger "+totemTopName);
		totemTopButton.setBounds(74, 25, 150, 50);
		totemTopButton.setForeground(Color.WHITE);
		totemTopButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
		totemTopButton.setVerticalTextPosition(SwingConstants.CENTER);
		totemTopButton.setHorizontalTextPosition(SwingConstants.CENTER);
		totemTopButton.setBorder(BorderFactory.createLineBorder(new Color(79,202,217), 2));
		add(totemTopButton);
		
		totemBotButton = new JButton("Coger "+totemBotName);		
		totemBotButton.setForeground(Color.WHITE);
		totemBotButton.setBounds(74, 95, 150, 50);
		totemBotButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
		totemBotButton.setVerticalTextPosition(SwingConstants.CENTER);
		totemBotButton.setHorizontalTextPosition(SwingConstants.CENTER);
		totemBotButton.setBorder(BorderFactory.createLineBorder(new Color(232,183,169), 2));
		add(totemBotButton);
		
		alertPanelButton = new JButton("");
		alertPanelButton.setBorder(null);
		alertPanelButton.setOpaque(false);
		alertPanelButton.setBounds(133, 169, 32, 32);
		alertPanelButton.setBackground(Color.LIGHT_GRAY);
		alertPanelButton.setMargin(new Insets(0, 0, 0, 0));
		alertPanelButton.setIcon(new ImageIcon(Generic_client.class.getResource("/javax/swing/plaf/metal/icons/ocean/warning.png")));
		add(alertPanelButton);
		
		infoPanelTotemTopButton = new JButton("");
		infoPanelTotemTopButton.setBorder(null);
		infoPanelTotemTopButton.setOpaque(false);
		infoPanelTotemTopButton.setBounds(233, 36, 32, 32);
		infoPanelTotemTopButton.setBackground(Color.LIGHT_GRAY);
		infoPanelTotemTopButton.setMargin(new Insets(0, 0, 0, 0));
		infoPanelTotemTopButton.setIcon(new ImageIcon(Generic_client.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		add(infoPanelTotemTopButton);
		
		infoPanelTotemBotButton = new JButton("");
		infoPanelTotemBotButton.setBorder(null);
		infoPanelTotemBotButton.setOpaque(false);
		infoPanelTotemBotButton.setBounds(233, 106, 32, 32);
		infoPanelTotemBotButton.setBackground(Color.LIGHT_GRAY);
		infoPanelTotemBotButton.setMargin(new Insets(0, 0, 0, 0));
		infoPanelTotemBotButton.setIcon(new ImageIcon(Generic_client.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		add(infoPanelTotemBotButton);
		
		totemTopTimerLabel = new JLabel("");
		totemTopTimerLabel.setBackground(Color.BLACK);
		totemTopTimerLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		totemTopTimerLabel.setForeground(Color.BLACK);
		totemTopTimerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		totemTopTimerLabel.setBounds(74, 75, 150, 15);
		add(totemTopTimerLabel);
		
		totemBotTimerLabel = new JLabel("");
		totemBotTimerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		totemBotTimerLabel.setForeground(Color.BLACK);
		totemBotTimerLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		totemBotTimerLabel.setBackground(Color.BLACK);
		totemBotTimerLabel.setBounds(74, 145, 150, 15);
		add(totemBotTimerLabel);
		
		
		//------------------------------------Labels------------------------------------------//
		
//		JLabel versionLabel = new JLabel("Versión 2.0.8");
		JLabel versionLabel = new JLabel("Development Version");
		
		versionLabel.setFont(new Font("Yu Gothic", Font.BOLD, 11));
		versionLabel.setBounds(10, 195, 120, 14);
		add(versionLabel);
		
		JLabel background = new JLabel("New label");
		background.setBounds(0, 0, 299, 221);
		background.setIcon(new ImageIcon(Generic_client.class.getResource("/Img/Background_main.png")));
		add(background);
		
		clientIcon = new JLabel("");
		clientIcon.setBounds(10, 63, 50, 50);
		add(clientIcon);
		
		//------------------------------------Initialize Variables--------------------------------//
		initialize();
		listeners();
	}
	
	public void listeners() {
		
		//When the top button is pressed the action will be...
		totemTopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//If the totem was free, take it
				if (totemTopButton.getText().equals("Coger " + totemTopName)) {
					System.out.println("entro en el if");
					//Change the text to set free
					totemTopButton.setText("Soltar " + totemTopName);
					//Add a red border to the button
					totemTopButton.setBorder(BorderFactory.createLineBorder(new Color(254,0,0), 5));
					//Send the other clients that the totem is taken
					send("totem,takeTotemTop,"+myName);
				}
				//If the totem was blocked, send a freeTotem message
				else {
					new ConnectionCheck("freeTotemTop",Generic_client.this);
				}
			}
		});
		
		//When the bot button is pressed the action will be...
		totemBotButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//If the totem was free, take it
				if (totemBotButton.getText() == "Coger " + totemBotName) {
					//Change the text to set free
					totemBotButton.setText("Soltar " + totemBotName);
					//Add a red border to the button
					totemBotButton.setBorder(BorderFactory.createLineBorder(new Color(254,0,0), 5));
					//Send the other clients that the totem is taken
					send("totem,takeTotemBot,"+myName);
				}
				//If the totem was blocked, send a freeTotem message
				else {
	        		new ConnectionCheck("freeTotemBot",Generic_client.this);
				}
			}
		});
		
		//Shows the alertPanel with the buttons active if the totem is taken
		alertPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alertPanel.showIt(totemTopTaken, totemBotTaken);
			}
		});
		
		//Shows the infoPanel for the Top totem
		infoPanelTotemTopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				infoPanel.showIt("takeTotemTop");
			}
		});
		
		//Shows the infoPanel for the Bot totem
		infoPanelTotemBotButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				infoPanel.showIt("takeTotemBot");
			}
		});
	}
	
	public void initialize(){
		
		//Initialize the variables
		totemBotTaken=false;
		totemTopTaken=false;
		ImAlive=false;
		totemTopTimer = new Integer[3];
        totemTopTimer[0]=1;
        totemTopTimer[1]=0;
        totemTopTimer[2]=0;
        totemBotTimer = new Integer[3];
        totemBotTimer[0]=1;
        totemBotTimer[1]=0;
        totemBotTimer[2]=0;
	}
	
	public String getClientName() {
		return clientName;
	}
	
	public String getMyName() {
		return myName;
	}
	
	public void setTotemTopFree(String userName) {
		
		//If we received a message that the totem is set free and we have the totem taken the app will show an alert
		if (totemTopButton.getText().equals("Soltar " + totemTopName)) {
			showsForcedAlert(totemTopName,userName);
		}
		//Reinitialize the button
		totemTopButton.setEnabled(true);
		infoPanel.changeActiveOkButton(totemTopName, false);
		totemTopButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
		totemTopButton.setBorder(BorderFactory.createLineBorder(new Color(79,202,217), 2));
		totemTopTaken=false;
		totemTopButton.setText("Coger " + totemTopName);
		
	}
	
	public void setTotemBotFree(String userName) {
		
		//If we received a message that the totem is set free and we have the totem taken the app will show an alert
		if (totemBotButton.getText().equals("Soltar " + totemBotName)) {
			showsForcedAlert(totemBotName,userName);
		}
		//Reinitialize the button
		totemBotButton.setEnabled(true);
		infoPanel.changeActiveOkButton(totemBotName, false);
		totemBotButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
		totemBotButton.setBorder(BorderFactory.createLineBorder(new Color(232,183,169), 2));
		totemBotTaken=false;
		totemBotButton.setText("Coger " + totemBotName);
		
	}
	
	public void blockDragon(String user_name) {
		
		totemBotButton.setEnabled(false);
		infoPanel.changeActiveOkButton(totemBotName, true);
		totemBotTaken=true;
		if(totemBotTimer[0]+totemBotTimer[1]+totemBotTimer[2] == 1) { //If the time is not already running
			startTimer("dragon");
		}
		else {
			totemBotTimer[0]=1;
	        totemBotTimer[1]=0;
	        totemBotTimer[2]=0;
		}
		totemBotButton.setFont(new Font("Yu Gothic", Font.BOLD, 14));
		totemBotButton.setBorder(BorderFactory.createLineBorder(new Color(232,183,169), 2));
		if(user_name.equals("")) {
			totemBotButton.setText("En transición");
		}
		else {
			totemBotButton.setText("<html><font color = black>Dragon: " + user_name+"</html>");
		}
		
	}
	
	public void blockTotemTop(String userName) {
		
		totemTopButton.setEnabled(false);
		infoPanel.changeActiveOkButton(totemTopName, true);
		totemTopTaken=true;
		if(totemTopTimer[0]+(totemTopTimer[1]*60)+(totemTopTimer[2]*3600) == 1) { //If the time is not already running
			startTimer(totemTopName);
		}
		else {
			totemTopTimer[0]=1;
			totemTopTimer[1]=0;
			totemTopTimer[2]=0;
		}
		totemTopButton.setFont(new Font("Yu Gothic", Font.BOLD, 14));
		totemTopButton.setBorder(BorderFactory.createLineBorder(new Color(79,202,217), 2));
		if(userName.equals("")) {
			totemTopButton.setText("En transición");
		}
		else {
			totemTopButton.setText("<html><font color = white>Lobo: " + userName+"</html>");
		}
	}
	
	
	public void changeClientStatus(String msg){
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
								infoPanel.addList(parts[1],parts[2]);
								switch (parts[1]){
					        	case "takeTotemBot":
					        		if (totemBotButton.getText().equals("Soltar Dragon")) {
					        			show_alert("dragon",parts[2]);
					        		}
					        		break;
					        	case "takeTotemTop":
					        		if (totemTopButton.getText().equals("Soltar Lobo")) {
					        			show_alert("lobo",parts[2]);
					        		}
					        		break;
					        	}
							break;
							
						case "freedom":
							infoPanel.clearList(parts[1]);
							showMsg(parts[1],parts[2],parts[3]);
							break;
							
						case "CleanList":
							infoPanel.removeUserFromListByNumber(parts[1], Integer.parseInt(parts[2]));
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
									totemTopTimer[0]=Integer.valueOf(times[2]);
							        totemTopTimer[1]=Integer.valueOf(times[1]);
							        totemTopTimer[2]=Integer.valueOf(times[0]);
								}
								else if(parts[1].equals("dragon")) {
									totemBotTimer[0]=Integer.valueOf(times[2]);
									totemBotTimer[1]=Integer.valueOf(times[1]);
									totemBotTimer[2]=Integer.valueOf(times[0]);
								}
							
		    				break;
						case "freeTotem":
							chekList(parts[1],"");
		    			default:
		    				chekList(msg,"");
					}
				}
			}
		}
	}
	
	public void chekList(String action, String name) {
		
		if(infoPanel.getUser(action).equals(myName)) {
			
			String totem="";
			String okAction="";
			if (action.equals("freeTotemTop")) {
				send("CleanList,totemTop,0");
				totem="lobo";
				okAction="takeTotemTop";
				promt("free_wolf");
			}
			if (action.equals("freeTotemBot")) {
				send("CleanList,totemBot,0");
				totem="dragon";
				okAction="takeTotemBot";
				promt("free_dragon");
			}
			window.windowsMessage("Se acaba de liberar el " +totem+ " y eres el siguiente en la cola.");
			String ObjButtons[] = {"Yes","No"};
	        int PromptResult = JOptionPane.showOptionDialog(null,"Se acaba de liberar el " +totem+ " y eres el siguiente en la cola. ¿Quieres cogerlo?",
	        		"Liberación del Totem",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
	        if(PromptResult==JOptionPane.YES_OPTION)
	        {
	        	if (action.equals("freeTotemTop")) {
	        		setTotemTopFree(name);
	        		totemTopButton.setText("Soltar Lobo");
					totemTopButton.setBorder(BorderFactory.createLineBorder(new Color(254,0,0), 5));
				}
				if (action.equals("freeTotemBot")) {
					setTotemBotFree(name);
					totemBotButton.setText("Soltar Dragon");
					totemBotButton.setBorder(BorderFactory.createLineBorder(new Color(254,0,0), 5));
				}
				send("totem,"+okAction+","+myName);
	        }
	        else {
	        	send("freeTotem,"+action+","+myName);
	        }
			
		}
		else if(infoPanel.getUser(action).equals("empty")) {
			showMsg(action,name,"");
		}
	}
	
	public void showMsg(String text, String user_name, String client) {
				
		if("takeTotemBot".equals(text) && clientName.equals(client)){
			if(!totemBotButton.getText().equals("Soltar Dragon")) {
				blockDragon(user_name);
			}
		}
		else if("takeTotemTop".equals(text) && clientName.equals(client)){
			if(!totemTopButton.getText().equals("Soltar Lobo")) {
				blockTotemTop(user_name);
			}
		}
		else if(("freeTotemBot").equals(text)){
			setTotemBotFree(user_name);
		}
		else if(("freeTotemTop").equals(text)){
			setTotemTopFree(user_name);
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
		client.send(text+","+clientName);
	}

	public boolean canIExit() {
		return (!isTotemTopWarning() && !isTotemBotWarning());
	}
	
	public boolean isTotemTopWarning() {
		if (totemTopButton.getText().equals("Soltar Lobo")) {
			return true;
		}
		return false;
	}
	
	public boolean isTotemBotWarning() {
		if (totemBotButton.getText().equals("Soltar Dragon")) {
			return true;
		}
		return false;
	}
	
	public void showsForcedAlert(String totem, String userName) {
		//Shows a windows alert
		window.windowsMessage("El usuario " +userName+ " ha forzado la liberación del totem \"" + totem+"\"");
		//Reproduce the sound alert
		if(totem.equals(totemTopName)) {
			promt(userName,totemTopForcedAlert);
		}
		else if(totem.equals(totemBotName)) {
			promt(userName,totemBotForcedAlert);
		}
		//Shows a message that the totem was forced to set free
		JOptionPane.showMessageDialog(null,"El usuario " +userName+ " ha forzado la liberación del totem \"" + totem+"\"");
	}
	
	public void show_alert(String totem, String user_name) {
		window.windowsMessage("El usuario " +user_name+ " está solicitando el " + totem);
		if(totem.equals("lobo")) {
			promt(user_name,"asking_wolf");
		}
		else if(totem.equals("dragon")) {
			promt(user_name,"asking_dragon");
		}
		JOptionPane.showMessageDialog(null,"El usuario " +user_name+ " está solicitando el " + totem);
	}
	
	public InfoPanel getInfoPanel() {
		return infoPanel;
	}
	
	//Method that start the timer for a totem
	public void startTimer(String totemName) {
		Thread t1 = new Thread(new Runnable() {
	         public void run() {
	        	 try {
	        		//Wait 1 second to have a good synchronization
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
	        	//Difference between top and bot totem
	        	 if(totemName.equals(totemBotName)) {
	        		 //Run the timer while the totem is taken
	        		 while(totemBotTaken) {
	        			 
	        			 if(totemBotTimer[0]==60) {
	        				 totemBotTimer[0]=0;
	        				 totemBotTimer[1]+=1;
	        			 }
	        			 if(totemBotTimer[1]==60) {
	        				 totemBotTimer[1]=0;
	        				 totemBotTimer[2]+=1;
	        			 }
	        			 if(totemBotTimer[2] != 0) {
	        				 totemBotTimerLabel.setText("Retenido: "+ String.valueOf(totemBotTimer[2]) + " h " + String.valueOf(totemBotTimer[1]) + " min "+ String.valueOf(totemBotTimer[0])+ " seg");
	        			 }
	        			 else if(totemBotTimer[1] !=0) {
	        				 totemBotTimerLabel.setText("Retenido: "+ String.valueOf(totemBotTimer[1]) + " min "+ String.valueOf(totemBotTimer[0])+ " seg");
	        			 }
	        			 else {
	        				 totemBotTimerLabel.setText("Retenido: "+ String.valueOf(totemBotTimer[0])+ " seg");
	        			 }
	        			 totemBotTimer[0]++;
			        	 try {
			        		 //Wait 1 second
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		        	 }
	        		 //Restart the timer and remove the text of the label
	        		 totemBotTimer[0]=1;
	        		 totemBotTimer[1]=0;
	        		 totemBotTimer[2]=0;
	        		 totemBotTimerLabel.setText("");
	        	 }
	        	 else if (totemName.equals(totemTopName)) {
	        		 while(totemTopTaken) {
	        			 if(totemTopTimer[0]==60) {
	        				 totemTopTimer[0]=0;
	        				 totemTopTimer[1]+=1;
	        			 }
	        			 if(totemTopTimer[1]==60) {
	        				 totemTopTimer[1]=0;
	        				 totemTopTimer[2]+=1;
	        			 }
	        			 if(totemTopTimer[2] != 0) {
	        				 totemTopTimerLabel.setText("Retenido: "+ String.valueOf(totemTopTimer[2]) + " h " + String.valueOf(totemTopTimer[1]) + " min "+ String.valueOf(totemTopTimer[0])+ " seg");
	        			 }
	        			 else if(totemTopTimer[1] !=0) {
	        				 totemTopTimerLabel.setText("Retenido: "+ String.valueOf(totemTopTimer[1]) + " min "+ String.valueOf(totemTopTimer[0])+ " seg");
	        			 }
	        			 else {
	        				 totemTopTimerLabel.setText("Retenido: "+ String.valueOf(totemTopTimer[0])+ " seg");
	        			 }
	        			 totemTopTimer[0]++;
			        	 try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		        	 }
	        		 totemTopTimer[0]=1;
	        		 totemTopTimer[1]=0;
	        		 totemTopTimer[2]=0;
	        		 totemTopTimerLabel.setText("");
	        	 }
	         }
	    });  
	    t1.start();
	}

	protected class ConnectionCheck{
    	
    	public ConnectionCheck(String action, Generic_client panel) {
            new Thread() {
                public void run() {
                	//Get the first user in the list
                	String user = infoPanel.getUser(action);
                	boolean canGo=false;
                	
                	//Until someone respond with an "imAlive" message or the wait list is empty
        			while (!canGo &&!user.equals("empty")) {
        				
        					//Send the message to know if the user is still connected
	                		send("isAlive,NO_DATA,"+user);
	                		try {
	                			//Wait 3 seconds to know if the client is disconnected.
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
	                		//After 3 seconds, check if the client has responded to the ask
	                		if(ImAlive) {
	                			//If he is alive, then change the variable
	                			canGo=true;
	                			break;
	                		}
	                	
	                	if(!canGo) {
	                		//If he is not alive, remove the disconnected client to the wait list
	                		if (action.equals("freeTotemTop,"+clientName))	panel.send("CleanList,totemTop,0");
	        				if (action.equals("freeTotemBot,"+clientName))	panel.send("CleanList,totemBot,0");
	        				try {
	        					//Wait 2 second to let enough time to the server to read the last message and send us the CleanList back.
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
	        				//And get the next person in the queue
	                		user=infoPanel.getUser(action);
	                	}
        			}
        			//Set the variable to false again
        			ImAlive=false;
        			//Send the message to set free the totem
        			send("freeTotem,"+action+","+myName);
                }
            }.start();
        }
    }
}
