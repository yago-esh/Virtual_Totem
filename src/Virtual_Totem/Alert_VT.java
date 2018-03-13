package Virtual_Totem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import java.awt.List;

public class Alert_VT extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton okButton, cancelButton;
	private ArrayList<String> list_dragon, list_wolf;
	private String mode;
	private Panel_VT panel;
	private List list;
	private boolean action;

	public Alert_VT(Panel_VT panel) {
		
		this.panel = panel;
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
		info_text.setText("A continuaci\u00F3n va a proceder a solicitar el \"totem\"\r\n\r\n\u2022 Se proceder\u00E1 a mandar una alerta al usuario que tiene actualmente el totem.\r\n\u2022 Se le incluir\u00E1 en la cola de usuarios.\r\n\u2022 Cuando el usuario actual suelte el totem, se enviar\u00E1 una notificaci\u00F3n al pr\u00F3ximo usuario de la cola.");
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
		background.setIcon(new ImageIcon(Info_VT.class.getResource("/Img/background.jpg")));
		contentPanel.add(background);
		
		//------------------------------------Initialize Variables--------------------------------//
		listeners();
		initialize();
	}

	public void listeners() {
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action=true;
				addList(mode, System.getProperty("user.name"));
				if(mode.equals("coger_lobo")) {
					panel.send("list,coger_lobo,"+System.getProperty("user.name"));
				}
				else if (mode.equals("coger_dragon")) {
					panel.send("list,coger_dragon,"+System.getProperty("user.name"));
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
	}
	
	public void showIt(String totem) {
		mode=totem;
		this.setVisible(true);
		this.setTitle("Cola de usuarios para el "+totem);
		
		if(mode.equals("coger_lobo")) {
			for(String user: list_wolf) {
				list.add(user);
			}
		}
		else if (mode.equals("coger_dragon")) {
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
		if(mode.equals("coger_lobo")) {
			if(!action) {
				list_wolf.add(user);
			}
			else {
				action=false;
			}
		}
		else if (mode.equals("coger_dragon")) {
			if(!action) {
				list_dragon.add(user);
			}
			else {
				action=false;
			}
		}
	}
	
	public String getUser(String name_list) {
		String user;
		if(name_list.equals("soltar_lobo")){
			if(!list_wolf.isEmpty()) {
				user= list_wolf.get(0);
				removeUserList(name_list, user);
				return user;
			}
		}
		if (name_list.equals("soltar_dragon")) {
			if(!list_dragon.isEmpty()) {
				user= list_dragon.get(0);
				removeUserList(name_list, user);
				return user;
			}
		}
		return "empty";
	}
	
	public void removeUserList(String name_list, String user) {
		
		if(name_list.equals("soltar_lobo")){
			for(int x=0; x<list_wolf.size(); x++) {
				if (list_wolf.get(x).equals(user)) {
					list_wolf.remove(x);
					if(panel.isAction()) panel.send("CleanList,wolf,"+String.valueOf(x));
					break;
				}
			}
		}
		if (name_list.equals("soltar_dragon")) {
			for(int x=0; x<list_dragon.size(); x++) {
				if (list_dragon.get(x).equals(user)) {
					list_dragon.remove(x);
					if(panel.isAction()) panel.send("CleanList,dragon,"+String.valueOf(x));
					break;
				}
			}
		}
	}
	
}
