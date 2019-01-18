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

class Window_VT extends JFrame {

	private Client_VT cliente;
	private Read_Data IO;
	private MenuItem exitItem, showItem;
	private TrayIcon trayIcon;
	private String[] options;
	private CheckboxMenuItem OpcMinimize, OpcSilence;
	private ArrayList<String> clientsList;
	private ArrayList<CheckboxMenuItem> opcClients;
	private static int num_options = 3; 
	static final long serialVersionUID = 42L;

	public Window_VT(Client_VT cliente) {
		
		this.cliente = cliente;

		opcClients = new ArrayList<CheckboxMenuItem>();
		clientsList = new ArrayList<String>();
		
		clientsList.add("Vodafone");
		clientsList.add("Caser");
		clientsList.add("Mapfre");
//		clientsList.add("LDA");
//		clientsList.add("Multiasistencia");
		
		this.setTitle("Virtual Totem");
		this.setLocation(810, 425);
		this.setSize(300, 240);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Img/totem.png")));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		Systray();
		initializer();
		selectClient(Integer.valueOf(options[2]));
		listeners();
	}
	
	public void initializer() {
		
		IO = new Read_Data(num_options);
		options = IO.read();
		if(options != null && options[num_options-1] != null) {
			setoptions();
		}
		else {
			options = new String[num_options];
			options[2]="0";
			opcClients.get(0).setState(true);
		}
	}
	
	public void selectClient(int num) {
		
		this.getContentPane().removeAll();
		switch (num) {
		case 0:
			this.getContentPane().add(new Client_Vodafone(cliente, this));
			break;
			
		case 1:
			this.getContentPane().add(new Client_Caser(cliente, this));
			break;
			
		case 2:
			this.getContentPane().add(new Client_Mapfre(cliente, this));
			break;
		default:
			break;
		}
		this.getContentPane().revalidate();
		this.getContentPane().repaint();

	}
	
	public void setoptions() {
//		System.out.println(Boolean.valueOf(options[0]));
		OpcMinimize.setState(Boolean.valueOf(options[0]));
		OpcSilence.setState(Boolean.valueOf(options[1]));
		opcClients.get(Integer.valueOf(options[2])).setState(true);;
	}
	
	public void getoptions() {
		options[0]=String.valueOf(OpcMinimize.getState());
		options[1]=String.valueOf(OpcSilence.getState());
		for(int x=0;x<opcClients.size();x++) {
			if(opcClients.get(x).getState()==true) {
				options[2]=String.valueOf(x);
				break;
			}
		}
	}
	
	public Generic_client getClient() {
		return (Generic_client) this.getContentPane().getComponent(0);
	}
	
	public void listeners() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(OpcMinimize.getState()) {
					setVisible(false);
				}
				else {
					exit();
				}
			}
		});
		
		exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				exit();
            }
        });
		
		trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	setVisible(true);
            }
        });
		
		showItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	setVisible(true);
            }
        });
		
		for (CheckboxMenuItem menuItem : opcClients) {
			menuItem.addItemListener(new ItemListener(){
	            public void itemStateChanged(ItemEvent ie)
	            {
	            		if(menuItem.getState()) {
	            			if(canIExitClient()) {
			            		int x=0;
			            		for (CheckboxMenuItem clients : opcClients) {
									if(!clients.equals(menuItem)) {
										clients.setState(false);
									}
									else {
										selectClient(x);
										cliente.enviar("clientChanged,x,"+getClient().getMyName()+","+getClient().getClientName());
									}
									x++;
								}
	            			}
	            			else {
	    	            		menuItem.setState(false);
	    	            	}
		            	}
		            	else {
		            		menuItem.setState(true);
		            		JOptionPane.showMessageDialog(null,"Es necesario que un cliente esté seleccionado");
		            	}
	            }
	        });
		}
		
	}
	
	public void Systray() {
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
	        OpcMinimize = new CheckboxMenuItem("Minimizar al cerrar");
	        OpcSilence = new CheckboxMenuItem("Silenciar voz");
	        Menu clients = new Menu("Clientes");
	       
	        //Add components to pop-up menu
	        popup.add(showItem);
	        popup.addSeparator();
	        popup.add(options);
	        popup.addSeparator();
	        popup.add(clients);
	        options.add(OpcMinimize);
	        options.add(OpcSilence);
	        for (String string : clientsList) {
	        	CheckboxMenuItem aux = new CheckboxMenuItem(string);
				opcClients.add(aux);
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
		if(clientExit.cant_exit()) {
			String ObjButtons[] = {"Yes","No"};
	        int PromptResult = JOptionPane.showOptionDialog(null,"¿Estás seguro de que quieres salir? Tienes un totem cogido.",
	        		"Advertencia de salida",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
	        if(PromptResult==JOptionPane.YES_OPTION)
	        {
	        	clientExit.getAlert_VT().removeMeUserFromList();
	        	
	        	if (clientExit.warning_wolf()) {
	        		clientExit.blockWolf("");
	    			cliente.enviar("freeTotem,soltar_lobo,"+clientExit.getMyName()+","+clientExit.getClientName());
	    		}
	    		if ( clientExit.warning_dragon()) {
	    			clientExit.blockDragon("");
	    			cliente.enviar("freeTotem,soltar_dragon,"+clientExit.getMyName()+","+clientExit.getClientName());
	    		}
	        	try {
	        		//Need to wait 2 second to let enough time to the server to read the last message and be available to read the next one before close the flows.
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
	        	return true;
	        }
	        else {
	        	return false;
	        }
		}else {
			return true;
		}
	}
	
	public void exit() {
		if(canIExitClient()) {
			getoptions();
        	IO.write(options);
			Window_VT.this.cliente.terminar();
            System.exit(-1);
		}
	}
	
	public void msgTry(String msg) {
		trayIcon.displayMessage(msg, "Virtual Totem", MessageType.WARNING);
	}
	
	public boolean isSilence() {
		return OpcSilence.getState();
	}
}

