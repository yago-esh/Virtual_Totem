package Virtual_Totem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import Clients.Generic_client;

import java.awt.List;

public class InfoPanel extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton okButton, cancelButton;
	private ArrayList<String> totemBotList, totemTopList;
	private String totemSelected, myName, totemTopName, totemBotName;
	private Generic_client panel;
	private List list;
	private boolean isTotemTopTaken, isTotemBotTaken;
	private JTextPane infoTextPane;

	public InfoPanel(Generic_client panel, String name, String[] totemNames) {
		
		totemTopName = totemNames[0];
		totemBotName = totemNames[1];
		this.panel = panel;
		this.myName = name;
		this.setVisible(false);
		setBounds(810, 425, 450, 245);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		//------------------------------------TextFields------------------------------------------//
		
		infoTextPane = new JTextPane();
		infoTextPane.setOpaque(false);
		infoTextPane.setEditable(false);
		infoTextPane.setForeground(Color.BLACK);
		infoTextPane.setFont(new Font("Tahoma", Font.BOLD, 13));
		infoTextPane.setBackground(UIManager.getColor("CheckBox.background"));
		infoTextPane.setBounds(149, 13, 275, 150);
		contentPanel.add(infoTextPane);
		
		//------------------------------------Buttons------------------------------------------//
		okButton = new JButton("OK");
		okButton.setBounds(287, 164, 55, 25);
		contentPanel.add(okButton);
		
		list = new List();
		list.setBounds(19, 44, 120, 142);
		contentPanel.add(list);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(349, 164, 75, 25);
		contentPanel.add(cancelButton);
		
		JLabel queueUsersLabel = new JLabel("Usuarios en cola:");
		queueUsersLabel.setForeground(Color.BLACK);
		queueUsersLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		queueUsersLabel.setBounds(19, 13, 120, 25);
		contentPanel.add(queueUsersLabel);
		
		//------------------------------------Labels------------------------------------------//
		
		JLabel background = new JLabel();
		background.setBounds(0, 0, 450, 254);
		background.setIcon(new ImageIcon(AlertPanel.class.getResource("/Img/background.jpg")));
		contentPanel.add(background);
		
		//------------------------------------Initialize Variables--------------------------------//
		listeners();
		initialize();
	}

	public void listeners() {
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//If the user is not in the list already
				if(!isInTheList(totemSelected)) {
					//Send the message to add him to the list
					//The message is going to come back to the sender, so it will process it later
					if(totemSelected.equals("takeTotemTop")) {
						panel.send("list,takeTotemTop,"+myName);
					}
					else if (totemSelected.equals("takeTotemBot")) {
						panel.send("list,takeTotemBot,"+myName);
					}
				}
				hideIt();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideIt();
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent event) {
	            hideIt();
	        }
	    });
	}
	
	public void initialize() {
		
		//Initialize the variables
		totemBotList = new ArrayList<String>();
		totemTopList = new ArrayList<String>();
		totemSelected="";
		isTotemTopTaken=false;
		isTotemBotTaken=false;
	}
	
	//Change the status of the OK button, It will be able only if the totem is taken
	public void changeActiveOkButton(String totem, boolean state) {
		if(totem.equals(totemTopName)){
			isTotemTopTaken=state;
		}
		else if(totem.equals(totemBotName)) {
			isTotemBotTaken=state;
		}
	}
	
	public void showIt(String mode) {
		//Set the totemSelected
		this.totemSelected=mode;
		String totemName="";
		this.setVisible(true);
		
		//Difference between top and bot totem
		if(totemSelected.equals("takeTotemTop")) {
			//Set the totemName of the infoTextPane
			totemName=totemTopName;
			okButton.setEnabled(isTotemTopTaken);
			this.setTitle("Cola de usuarios para el totem \""+totemTopName+"\"");
			//Add all the queue user to the list
			for(String user: totemTopList) {
				list.add(user);
			}
		}
		else if (totemSelected.equals("takeTotemBot")) {
			totemName=totemBotName;
			okButton.setEnabled(isTotemBotTaken);
			this.setTitle("Cola de usuarios para el totem \""+totemBotName+"\"");
			for(String user: totemBotList) {
				list.add(user);
			}
		}
		
		//Set the text of the panel, need to be here to write te totemName properly
		infoTextPane.setText("A continuación va a proceder a solicitar el totem \""+totemName+"\"\r\r "
				+ "• Se procederá a mandar una alerta al usuario que tiene actualmente el totem.\n"
				+ "• Se le incluirá en la cola de usuarios.\r\n\u2022 Cuando el usuario actual suelte el"
				+ " totem, se enviará una notificación al próximo usuario de la cola.");
	}
	
	public void hideIt() {
		this.setVisible(false);
		//Clean the list
		list.removeAll();
	}
	
	public void addList(String mode, String user){
		
		//Add the user to the list
		if(mode.equals("takeTotemTop")) {
			totemTopList.add(user);
		}
		else if (mode.equals("takeTotemBot")) {
			totemBotList.add(user);
		}
	}
	
	public boolean isInTheList(String mode) {
		
		//Difference between top and bot totem
		if(mode.equals("takeTotemTop")) {
			//Look into the list if the user is already on it
			for(String user: totemTopList) {
				if(user.equals(myName)) return true;
			}
		}
		else if (mode.equals("takeTotemBot")) {
			for(String user: totemBotList) {
				if(user.equals(myName)) return true;
			}
		}
		return false;
	}
	
	public String getUser(String mode) {
		
		//Difference between top and bot totem
		if(mode.equals("freeTotemTop")){
			//If there is someone in the queue list, return the first one
			if(!totemTopList.isEmpty()) {
				return totemTopList.get(0);
			}
		}
		if (mode.equals("freeTotemBot")) {
			if(!totemBotList.isEmpty()) {
				return totemBotList.get(0);
			}
		}
		//If there is no one, return empty
		return "empty";
	}
	
	public void removeUserFromList(String mode, String user) {
		
		//Difference between top and bot totem
		if(mode.equals("freeTotemTop")){
			//We go through the list
			for(int x=0; x<totemTopList.size(); x++) {
				//If it is found in the list, remove him
				if (totemTopList.get(x).equals(user)) {
					totemTopList.remove(x);
					break;
				}
			}
		}
		if (mode.equals("freeTotemBot")) {
			for(int x=0; x<totemBotList.size(); x++) {
				if (totemBotList.get(x).equals(user)) {
					totemBotList.remove(x);
					break;
				}
			}
		}
	}
	
	public void removeUserFromListByNumber(String totem, int number) {
		
		//Difference between top and bot totem
		if(totem.equals("totemTop")){
			totemTopList.remove(number);
		}
		if (totem.equals("totemBot")) {
			totemBotList.remove(number);		
		}
	}
	
	public void clearList(String mode) {
		
		//Difference between top and bot totem
		if(mode.equals("freeTotemTop")){
			totemTopList.clear();
		}
		if (mode.equals("freeTotemBot")) {
			totemBotList.clear();
		}
	}
	
	public void removeMeFromLists() {

		//Looks if the user is in any list and send a remove message to server
		for(int x=0; x<totemTopList.size(); x++) {
			if (totemTopList.get(x).equals(myName)) {
					panel.send("CleanList,totemTop,"+String.valueOf(x));
				break;
			}
		}
		for(int x=0; x<totemBotList.size(); x++) {
			if (totemBotList.get(x).equals(myName)) {
				totemBotList.remove(x);
					panel.send("CleanList,totemBot,"+String.valueOf(x));
				break;
			}
		}
	}
	
}
