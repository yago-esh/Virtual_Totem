package Virtual_Totem;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Clients.Client_Axa;
import Clients.Client_Caser;
import Clients.Client_Lda;
import Clients.Client_Mapfre;
import Clients.Client_Multiasistencia;
import Clients.Client_Vodafone;
import Clients.Generic_client;

public class Window extends JFrame {

	private Client client;
	private ReadData IO;
	private MenuItem exitItem, showItem;
	private TrayIcon trayIcon;
	private String[] options;
	private CheckboxMenuItem optionMinimize, optionSilence;
	private ArrayList<String> clientsList;
	private ArrayList<CheckboxMenuItem> optionClients;
	private static int num_options = 3; 
	static final long serialVersionUID = 42L;

	public Window(Client client) {
		
		this.client = client;

		optionClients = new ArrayList<CheckboxMenuItem>();
		clientsList = new ArrayList<String>();
		
		//Initialize the different companies
		clientsList.add("Vodafone");
		clientsList.add("Caser");
		clientsList.add("Mapfre");
		clientsList.add("Multiasistencia");
		clientsList.add("LDA");
		clientsList.add("AXA");
		
		this.setTitle("Virtual Totem");
		this.setLocation(810, 425);
		this.setSize(300, 240);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Img/totem.png")));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//Initialize the systray feature
		systray();
		
		//Initialize the data needed from the class
		initializer();
		
		//Choose the company that is related to the client
		selectClient(Integer.valueOf(options[2]));
		
		//Initialize the listeners of the class
		listeners();
	}
	
	public void initializer() {
		
		//Create the ReadData class to read the options from the file
		IO = new ReadData(num_options);
		options = IO.read();
		//If the file is properly filled the options are set
		if(options != null && options[num_options-1] != null) {
			setOptions();
		}
		else {
			//If not, we only need to set the company option to run the program
			options = new String[num_options];
			//The predefined value for the company option is 0 which means the company "Vodafone"
			options[2]="0";
			optionClients.get(0).setState(true);
		}
	}
	
	public void selectClient(int num) {
		
		//Remove the current panel to set the new one
		this.getContentPane().removeAll();
		
		//Each company is related with 1 id, these are the couples
		switch (num) {
		case 0:
			this.getContentPane().add(new Client_Vodafone(client, this));
			break;
			
		case 1:
			this.getContentPane().add(new Client_Caser(client, this));
			break;
			
		case 2:
			this.getContentPane().add(new Client_Mapfre(client, this));
			break;
		case 3:
			this.getContentPane().add(new Client_Multiasistencia(client, this));
			break;
			
		case 4:
			this.getContentPane().add(new Client_Lda(client, this));
			break;
			
		case 5:
			this.getContentPane().add(new Client_Axa(client, this));
			break;
		default:
			break;
		}
		this.getContentPane().revalidate();
		this.getContentPane().repaint();
	}
	
	public void setOptions() {
		//Associate the value of the file to the value of the systray menu
		optionMinimize.setState(Boolean.valueOf(options[0]));
		optionSilence.setState(Boolean.valueOf(options[1]));
		optionClients.get(Integer.valueOf(options[2])).setState(true);;
	}
	
	public void getOptions() {
		//Associate the value of the systray menu to the value of file
		options[0]=String.valueOf(optionMinimize.getState());
		options[1]=String.valueOf(optionSilence.getState());
		for(int x=0;x<optionClients.size();x++) {
			if(optionClients.get(x).getState()==true) {
				options[2]=String.valueOf(x);
				break;
			}
		}
	}
	
	public Generic_client getClient() {
		//Get the current pane
		return (Generic_client) this.getContentPane().getComponent(0);
	}
	
	public void listeners() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//If the minimize option is active, closing window just sets the visibility of the app to false
				if(optionMinimize.getState()) {
					setVisible(false);
				}
				//Otherwise, it's close the app
				else {
					exit();
				}
			}
		});
		
		//Exit item, close the app
		exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				exit();
            }
        });
		
		//Click on the tray icon sets the visibility of the app to true
		trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	setVisible(true);
            }
        });
		
		//Click on the show item sets the visibility of the app to true
		showItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	setVisible(true);
            }
        });
		
		//For each company we create a listener for the systray menu
		for (CheckboxMenuItem menuItem : optionClients) {
			menuItem.addItemListener(new ItemListener(){
	            public void itemStateChanged(ItemEvent ie)
	            {
	            	//After click in the menu the state changes so we have to ask for the opposite state that we really want to control
            		if(menuItem.getState()) {
            			//We control if there is any problem to exit the client
            			if(canIExitClient()) {
            				//If we can exit
		            		int x=0;
		            		for (CheckboxMenuItem clients : optionClients) {
		            			//Uncheck the rest of the menu items
								if(!clients.equals(menuItem)) {
									clients.setState(false);
								}
								//For the item that was selected
								else {
									//Changes the panel to the new company
									selectClient(x);
									//Send to the server a clientChanged message to ask for the information of the new company
									client.send("clientChanged,"+client.getMyId()+","+getClient().getMyName()+","+getClient().getClientName());
								}
								x++;
							}
            			}
            			//If we can't exit just set the state of the new selected item to false
            			else {
    	            		menuItem.setState(false);
    	            	}
	            	}
	            	else {
	            		//Means we want to uncheck the unique company that is selected, so we can't let the user do this so we set the state to true again
	            		menuItem.setState(true);
	            	}
	            }
	        });
		}
	}
	
	public void systray() {
		//Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
        }
        else {
	        final PopupMenu popup = new PopupMenu();
	        URL url = System.class.getResource("/Img/icon_systray.png");
	        Image img = Toolkit.getDefaultToolkit().getImage(url);
	        trayIcon = new TrayIcon(img, "Virtual Totem", popup);
	        final SystemTray tray = SystemTray.getSystemTray();
	        // Create a pop-up menu components
	        exitItem = new MenuItem("Salir");
	        showItem = new MenuItem("Abrir");
	        Menu options = new Menu("Opciones");
	        optionMinimize = new CheckboxMenuItem("Minimizar al cerrar");
	        optionSilence = new CheckboxMenuItem("Silenciar voz");
	        Menu clients = new Menu("Clientes");
	       
	        //Add components to pop-up menu
	        popup.add(showItem);
	        popup.addSeparator();
	        popup.add(options);
	        popup.addSeparator();
	        popup.add(clients);
	        options.add(optionMinimize);
	        options.add(optionSilence);
	        for (String string : clientsList) {
	        	CheckboxMenuItem aux = new CheckboxMenuItem(string);
				optionClients.add(aux);
				clients.add(aux);
			}
	        popup.addSeparator();
	        popup.add(exitItem);
	       
	        trayIcon.setPopupMenu(popup);
	       
	        try {
	            tray.add(trayIcon);
	        } catch (AWTException e) {
	            System.out.println("TrayIcon could not be added.");
	        }
        }
	}
	
	public void showIt() {
		this.setVisible(true);
	}
	
	public boolean canIExitClient() {
		Generic_client clientExit = getClient();
		//Looks if there is any problem to exit
		if(clientExit.canIExit()) {
			//If not, remove the user from the list
			clientExit.getAlert_VT().removeMeUserFromList();
			return true;
		}else {
			//if there is any problem, let the user know that he has taken a totem
			String ObjButtons[] = {"Yes","No"};
			//Ask him if he is sure to exit
	        int PromptResult = JOptionPane.showOptionDialog(null,"¿Estás seguro de que quieres salir? Tienes un totem cogido.",
	        		"Advertencia de salida",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
	        //If he is, we start the process to let him go
	        if(PromptResult==JOptionPane.YES_OPTION)
	        {
	        	//Remove him from the list
	        	clientExit.getAlert_VT().removeMeUserFromList();
	        	
	        	//Look which is the totem he has, and send a message to the other users to set free the totem
	        	if (clientExit.isTotemTopWarning()) {
	        		clientExit.blockWolf("");
	    			client.send("freeTotem,freeTotemTop,"+clientExit.getMyName()+","+clientExit.getClientName());
	    		}
	    		if ( clientExit.isTotemBotWarning()) {
	    			clientExit.blockDragon("");
	    			client.send("freeTotem,freeTotemBot,"+clientExit.getMyName()+","+clientExit.getClientName());
	    		}
	        	try {
	        		//Need to wait 1 second to let enough time to the server to read the last message and be available to read the next one before close the flows.
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
	        	return true;
	        }
	        else {
	        	return false;
	        }
		}
	}
	
	public void exit() { 
		//Check if there is any problem to exit
		if(canIExitClient()) {
			//If not, get the options from the systray
			getOptions();
			//And write them in the file
        	IO.write(options);
        	//To finish, close the app
			Window.this.client.finish();
		}
	}
	
	public void windowsMessage(String message) {
		//Display a Windows message
		trayIcon.displayMessage(message, "Virtual Totem", MessageType.WARNING);
	}
	
	public boolean isSilence() {
		//Return if the silence option is activated or not
		return optionSilence.getState();
	}
}

