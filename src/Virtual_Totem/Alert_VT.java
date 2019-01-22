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

public class Alert_VT extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton okButton, cancelButton;
	private ArrayList<String> list_dragon, list_wolf;
	private String mode, myName;
	private Generic_client panel;
	private List list;
	private boolean active_wolf, active_dragon;
	private boolean action;

	public Alert_VT(Generic_client panel, String name) {
		
		this.panel = panel;
		this.myName = name;
		this.setVisible(false);
		setBounds(810, 425, 450, 245);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		//------------------------------------TextFields------------------------------------------//
		
		JTextPane info_text = new JTextPane();
		info_text.setOpaque(false);
		info_text.setEditable(false);
		info_text.setForeground(Color.BLACK);
		info_text.setFont(new Font("Tahoma", Font.BOLD, 13));
		info_text.setBackground(UIManager.getColor("CheckBox.background"));
		info_text.setText("A continuaci\u00F3n va a proceder a solicitar el \"totem\"\r\n\r\n\u2022 Se"
				+ " proceder\u00E1 a mandar una alerta al usuario que tiene actualmente el totem.\r\n\u2022"
				+ " Se le incluir\u00E1 en la cola de usuarios.\r\n\u2022 Cuando el usuario actual suelte el"
				+ " totem, se enviar\u00E1 una notificaci\u00F3n al pr\u00F3ximo usuario de la cola.");
		info_text.setBounds(149, 13, 275, 150);
		contentPanel.add(info_text);
		
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
		
		JLabel lblNewLabel = new JLabel("Usuarios en cola:");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(19, 13, 120, 25);
		contentPanel.add(lblNewLabel);
		
		//------------------------------------Labels------------------------------------------//
		
		JLabel background = new JLabel();
		background.setBounds(0, 0, 450, 254);
		background.setIcon(new ImageIcon(InfoPanel.class.getResource("/Img/background.jpg")));
		contentPanel.add(background);
		
		//------------------------------------Initialize Variables--------------------------------//
		listeners();
		initialize();
	}

	public void listeners() {
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!isInList(mode)) {
					action=true;
					addList(mode, myName);
					if(mode.equals("takeTotemTop")) {
						panel.send("list,takeTotemTop,"+myName);
					}
					else if (mode.equals("takeTotemBot")) {
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
		list_dragon = new ArrayList<String>();
		list_wolf = new ArrayList<String>();
		mode="";
		action=false;
		active_wolf=false;
		active_dragon=false;
	}
	
	public void changeActiveOkButton(String totem, boolean state) {
		if(totem.equals("wolf")){
			active_wolf=state;
		}
		else if(totem.equals("dragon")) {
			active_dragon=state;
		}
	}
	
	public void showIt(String totem) {
		mode=totem;
		this.setVisible(true);
			if(mode.equals("takeTotemTop")) {
				okButton.setEnabled(active_wolf);
				this.setTitle("Cola de usuarios para el lobo");
				for(String user: list_wolf) {
					list.add(user);
				}
			}
			else if (mode.equals("takeTotemBot")) {
				okButton.setEnabled(active_dragon);
				this.setTitle("Cola de usuarios para el dragon");
				for(String user: list_dragon) {
					list.add(user);
				}
			}
	}
	
	public void hideIt() {
		this.setVisible(false);
		list.removeAll();
	}
	
	public void addList(String mode, String user){
		if(mode.equals("takeTotemTop")) {
			if(!action) {
				
				list_wolf.add(user);
			}
			else {
				action=false;
			}
		}
		else if (mode.equals("takeTotemBot")) {
			if(!action) {
				list_dragon.add(user);
			}
			else {
				action=false;
			}
		}
	}
	
	public boolean isInList(String mode) {
		
		if(mode.equals("takeTotemTop")) {
			for(String user: list_wolf) {
				if(user.equals(myName)) return true;
			}
		}
		else if (mode.equals("takeTotemBot")) {
			for(String user: list_dragon) {
				if(user.equals(myName)) return true;
			}
		}
		
		return false;
	}
	
	public String getUser(String name_list) {
		String user;
		if(name_list.equals("freeTotemTop")){
			if(!list_wolf.isEmpty()) {
				user= list_wolf.get(0);
				return user;
			}
		}
		if (name_list.equals("freeTotemBot")) {
			if(!list_dragon.isEmpty()) {
				user= list_dragon.get(0);
				return user;
			}
		}
		return "empty";
	}
	
	public void removeUserList(String name_list, String user) {
		
		if(name_list.equals("freeTotemTop")){
			for(int x=0; x<list_wolf.size(); x++) {
				if (list_wolf.get(x).equals(user)) {
					list_wolf.remove(x);
					break;
				}
			}
		}
		if (name_list.equals("freeTotemBot")) {
			for(int x=0; x<list_dragon.size(); x++) {
				if (list_dragon.get(x).equals(user)) {
					list_dragon.remove(x);
					break;
				}
			}
		}
	}
	
	public void removeNumList(String name_list, int num) {
		if(name_list.equals("wolf")){
			list_wolf.remove(num);
		}
		if (name_list.equals("dragon")) {
			list_dragon.remove(num);		
		}
	}
	
	public void clearList(String name_list) {
		if(name_list.equals("freeTotemTop")){
			list_wolf.clear();
		}
		if (name_list.equals("freeTotemBot")) {
			list_dragon.clear();
		}
	}
	
	public void removeMeUserFromList() {

		for(int x=0; x<list_wolf.size(); x++) {
			if (list_wolf.get(x).equals(myName)) {
					panel.send("CleanList,totemTop,"+String.valueOf(x));
				break;
			}
		}

		for(int x=0; x<list_dragon.size(); x++) {
			if (list_dragon.get(x).equals(myName)) {
				list_dragon.remove(x);
					panel.send("CleanList,totemBot,"+String.valueOf(x));
				break;
			}
		}
	}
	
}
