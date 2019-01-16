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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Virtual_Totem.Client_VT;
import Virtual_Totem.Window_VT;

class Window_VT extends JFrame {

	private Client_VT cliente;
	private Read_Data IO;
	private Vodafone_client Vodafone_client;
	private MenuItem exitItem, showItem;
	private TrayIcon trayIcon;
	private String[] options;
	private CheckboxMenuItem OpcMinimize, OpcSilence;
	private CheckboxMenuItem opcClientVodafone;
	private CheckboxMenuItem opcClientCaser;
	private ArrayList<String> clientsList;
	private ArrayList<CheckboxMenuItem> opcClients;
	private static int num_options = 3; 
	static final long serialVersionUID = 42L;

	public Window_VT(Client_VT cliente) {
		
		this.cliente = cliente;
		
		clientsList = new ArrayList<String>();
		clientsList.add("Vodafone");
		clientsList.add("Caser");
		clientsList.add("Mapfre");
		clientsList.add("LDA");
		clientsList.add("Multiasistencia");
		
		
		opcClients = new ArrayList<CheckboxMenuItem>();
		Vodafone_client = new Vodafone_client(cliente, this);
		this.setContentPane(Vodafone_client);
		this.setTitle("Virtual Totem");
		this.setLocation(810, 425);
		this.setSize(300, 240);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Img/totem.png")));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		Systray();
		listeners();
		initializer();
	}
	
	public void initializer() {
		
		IO = new Read_Data(num_options);
		options = IO.read();
		if(options != null) {
			setoptions();
		}
		else {
			options = new String[num_options];
		}
	}
	
	public void setoptions() {
		OpcMinimize.setState(Boolean.valueOf(options[0]));
		OpcSilence.setState(Boolean.valueOf(options[1]));
	}
	
	public void getoptions() {
		options[0]=String.valueOf(OpcMinimize.getState());
		options[1]=String.valueOf(OpcSilence.getState());
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
	        opcClientVodafone = new CheckboxMenuItem("Vodafone");
	        opcClientCaser = new CheckboxMenuItem("Caser");
	       
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
	
	public void exit() {
		if(Vodafone_client.cant_exit()) {
			String ObjButtons[] = {"Yes","No"};
	        int PromptResult = JOptionPane.showOptionDialog(null,"¿Estás seguro de que quieres salir? Tienes un totem cogido.",
	        		"Advertencia de salida",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
	        if(PromptResult==JOptionPane.YES_OPTION)
	        {
	        	getoptions();
	        	IO.write(options);
	        	Vodafone_client.getAlert_VT().removeMeUserFromList();
	        	
	        	if (Vodafone_client.warning_wolf()) {
	        		Vodafone_client.blockWolf("");
	    			cliente.enviar("soltar_lobo");
	    		}
	    		if ( Vodafone_client.warning_dragon()) {
	    			Vodafone_client.blockDragon("");
	    			cliente.enviar("soltar_dragon");
	    		}
	    		
	        	try {
	        		//Need to wait 2 second to let enough time to the server to read the last message and be available to read the next one before close the flows.
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	Window_VT.this.cliente.terminar();
	            System.exit(-1);
	        }
		}
		else {
			getoptions();
        	IO.write(options);
			Vodafone_client.getAlert_VT().removeMeUserFromList();
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

