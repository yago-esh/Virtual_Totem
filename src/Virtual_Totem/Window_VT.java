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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Virtual_Totem.Client_VT;
import Virtual_Totem.Window_VT;

class Window_VT extends JFrame {

	private Client_VT cliente;
	private Read_Data IO;
	private Panel_VT Panel_VT;
	private MenuItem exitItem, showItem;
	private TrayIcon trayIcon;
	private String[] options;
	private CheckboxMenuItem OpcMinimize;
	private static int num_options = 1; 
	static final long serialVersionUID = 42L;

	public Window_VT(Client_VT cliente) {
		
		this.cliente = cliente;
		Panel_VT = new Panel_VT(cliente);
		this.setContentPane(Panel_VT);
		this.setTitle("Virtual Totem");
		this.setLocation(810, 425);
		this.setSize(300, 230);
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
		
	}
	
	public void getoptions() {
		
		options[0]=String.valueOf(OpcMinimize.getState());
		
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
	       
	        //Add components to pop-up menu
	        popup.add(showItem);
	        popup.addSeparator();
	        popup.add(options);
	        options.add(OpcMinimize);
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
		if(Panel_VT.cant_exit()) {
			String ObjButtons[] = {"Yes","No"};
	        int PromptResult = JOptionPane.showOptionDialog(null,"¿Estás seguro de que quieres salir? Tienes un totem cogido.",
	        		"Advertencia de salida",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
	        if(PromptResult==JOptionPane.YES_OPTION)
	        {
	        	getoptions();
	        	IO.write(options);
	        	Panel_VT.getAlert_VT().removeMeUserFromList();
	        	
	        	if (Panel_VT.warning_wolf()) {
	        		Panel_VT.blockWolf("");
	    			cliente.enviar("soltar_lobo");
	    		}
	    		if ( Panel_VT.warning_dragon()) {
	    			Panel_VT.blockDragon("");
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
			Panel_VT.getAlert_VT().removeMeUserFromList();
			Window_VT.this.cliente.terminar();
            System.exit(-1);
		}
	}
}

