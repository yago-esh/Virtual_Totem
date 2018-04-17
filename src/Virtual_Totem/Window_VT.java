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
	private Panel_VT Panel_VT;
	private MenuItem exitItem, showItem;
	private TrayIcon trayIcon;
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
	}
	
	public void listeners() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
		
		exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println(Panel_VT.cant_exit());
				if(Panel_VT.cant_exit()) {
					String ObjButtons[] = {"Yes","No"};
			        int PromptResult = JOptionPane.showOptionDialog(null,"¿Estás seguro de que quieres salir? Tienes un totem cogido.",
			        		"Advertencia de salida",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
			        if(PromptResult==JOptionPane.YES_OPTION)
			        {
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
					Panel_VT.getAlert_VT().removeMeUserFromList();
					Window_VT.this.cliente.terminar();
		            System.exit(-1);
				}
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
	       
	        //Add components to pop-up menu
	        popup.add(showItem);
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
}

