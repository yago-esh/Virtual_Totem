package Virtual_Totem;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.UIManager;

import Clients.Generic_client;

import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class AlertPanel extends JDialog {

	private Generic_client panel;
	private boolean goingToUnlockBotTotem, goingToUnlockTopTotem;
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton unlockBotTotemButton, unlockTopTotemButton, okButton, cancelButton;
	private String totemTopName, totemBotName;
	
	public AlertPanel(Generic_client panel, String[] totemNames) {
		
		totemTopName = totemNames[0];
		totemBotName = totemNames[1];
		this.panel=panel;
		this.setVisible(false);
		this.setResizable(false);
		contentPanel.setLayout(null);
		setBounds(810, 425, 450, 275);
		this.setTitle("Info del Virtual Totem");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		//------------------------------------TextFields---------------------------------------//
		
		JTextPane infoTextPane = new JTextPane();
		infoTextPane.setOpaque(false);
		infoTextPane.setEditable(false);
		infoTextPane.setForeground(Color.BLACK);
		infoTextPane.setFont(new Font("Tahoma", Font.BOLD, 13));
		infoTextPane.setBackground(UIManager.getColor("CheckBox.background"));
		infoTextPane.setText("A continuaci\u00F3n va a proceder a desbloquear uno de los totem"+
				".\r\nEsta acci\u00F3n solo debe realizarse en los siguientes casos y solo tras haber "+
				"corroborado la acci\u00F3n con el resto del equipo:\r\n\r\n\u2022 El programa ha "+
				"bloqueado de forma incorrecta un totem.\r\n\u2022 Un usuario ha bloqueado de forma "+
				"indefinida un totem, no est\u00E1 siendo utilizado y no puede liberarlo de forma manual.");
		infoTextPane.setBounds(12, 13, 408, 142);
		contentPanel.add(infoTextPane);
		
		//------------------------------------Buttons------------------------------------------//
		
		unlockTopTotemButton = new JButton("Desbloquear "+totemTopName);
		unlockTopTotemButton.setForeground(Color.BLACK);
		unlockTopTotemButton.setBounds(35, 168, 150, 25);
		contentPanel.add(unlockTopTotemButton);
		
		unlockBotTotemButton = new JButton("Desbloquear "+totemBotName);
		unlockBotTotemButton.setForeground(Color.BLACK);
		unlockBotTotemButton.setBounds(229, 168, 150, 25);
		contentPanel.add(unlockBotTotemButton);
		
		okButton = new JButton("OK");
		okButton.setBounds(287, 206, 55, 25);
		contentPanel.add(okButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(349, 206, 75, 25);
		contentPanel.add(cancelButton);
		
		//------------------------------------Labels------------------------------------------//
		
		JLabel createdByYago = new JLabel("Created by Yago Echave-Sustaeta");
		createdByYago.setForeground(Color.BLACK);
		createdByYago.setBounds(12, 215, 258, 16);
		createdByYago.setFont(new Font("Tahoma", Font.BOLD, 13));
		contentPanel.add(createdByYago);
		
		JLabel background = new JLabel();
		background.setBounds(0, 0, 450, 253);
		background.setIcon(new ImageIcon(AlertPanel.class.getResource("/Img/background.jpg")));
		contentPanel.add(background);
		
		
		//------------------------------------Initialize Variables--------------------------------//
		listeners();
		initialize();
	}
	
	public void listeners() {
		
		//When close, just hide it
		this.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent event) {
	            hideIt();
	        }
	    });
		
		//Change the status of the "goingToUnlockTopTotem"
		unlockTopTotemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(goingToUnlockTopTotem) {
					unlockTopTotemButton.setForeground(Color.BLACK);
					goingToUnlockTopTotem=false;
				}
				else{
					unlockTopTotemButton.setForeground(Color.RED);
					goingToUnlockTopTotem=true;
				}
			}
		});
		
		//Change the status of the "goingToUnlockBotTotem"
		unlockBotTotemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(goingToUnlockBotTotem) {
					unlockBotTotemButton.setForeground(Color.BLACK);
					goingToUnlockBotTotem=false;
				}
				else {
					unlockBotTotemButton.setForeground(Color.RED);
					goingToUnlockBotTotem=true;
				}
			}
		});
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean messageOfBothTotems=false;
				//If both totems are going to be unlocked, shows a special message 
				if(goingToUnlockTopTotem && goingToUnlockBotTotem) {
					JOptionPane.showMessageDialog(null,totemTopName+" y "+ totemBotName +" desbloqueados correctamente");
					messageOfBothTotems = true;
				}
				//If top totem is going to be unlocked, send to the server the "freedom" message and shows a message to the client
				if(goingToUnlockTopTotem) {
					panel.send("freedom,freeTotemTop,"+System.getProperty("user.name"));
					unlockTopTotemButton.setForeground(Color.BLACK);
					goingToUnlockTopTotem=false;
					if(!messageOfBothTotems)JOptionPane.showMessageDialog(null,totemTopName+" desbloqueado correctamente");
				}
				//If bot totem is going to be unlocked, send to the server the "freedom" message and shows a message to the client
				if(goingToUnlockBotTotem){
					panel.send("freedom,freeTotemBot,"+System.getProperty("user.name"));
					unlockBotTotemButton.setForeground(Color.BLACK);
					goingToUnlockBotTotem=false;
					if(!messageOfBothTotems)JOptionPane.showMessageDialog(null,totemBotName+" desbloqueado correctamente");
				}
				//Then hide the panel
				hideIt();
			}
		});
		
		//Hide the panel
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideIt();
			}
		});
	}
	
	
	public void initialize() {
		//Initialize the variables
		goingToUnlockBotTotem=false;
		goingToUnlockTopTotem=false;
	}
	
	
	public void showIt(boolean totemTop, boolean totemBot) {
		this.setVisible(true);
		//Only let the client unlocks the totems which are blocked
		if(!totemTop) {
			unlockTopTotemButton.setEnabled(false);
		}
		if(!totemBot){
			unlockBotTotemButton.setEnabled(false);
		}
		
	}
	
	public void hideIt() {
		
		AlertPanel.this.setVisible(false);
		
		//Reinitialize the variables
		goingToUnlockTopTotem=false;
		goingToUnlockBotTotem=false;
		unlockTopTotemButton.setEnabled(true);
		unlockBotTotemButton.setEnabled(true);
		unlockTopTotemButton.setForeground(Color.BLACK);
		unlockBotTotemButton.setForeground(Color.BLACK);
	}
}
