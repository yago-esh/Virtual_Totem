package Virtual_Totem;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Info_VT extends JDialog {

	private Panel_VT panel;
	private boolean unlock_dragon, unlock_wolf;
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton BT_unlock_dragon, BT_unlock_wolf, okButton, cancelButton;
	
	public Info_VT(Panel_VT panel) {
		
		this.panel=panel;
		this.setVisible(false);
		this.setResizable(false);
		contentPanel.setLayout(null);
		setBounds(810, 425, 450, 275);
		this.setTitle("Info of Virtual Totem");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		//------------------------------------TextFields---------------------------------------//
		
		JTextPane info_text = new JTextPane();
		info_text.setOpaque(false);
		info_text.setEditable(false);
		info_text.setForeground(Color.BLACK);
		info_text.setFont(new Font("Tahoma", Font.BOLD, 13));
		info_text.setBackground(UIManager.getColor("CheckBox.background"));
		info_text.setText("A continuaci\u00F3n va a proceder a desbloquear uno de los totem"+
				".\r\nEsta acci\u00F3n solo debe realizarse en los siguientes casos y solo tras haber"+
				"corroborado la acci\u00F3n con el resto del equipo:\r\n\r\n\u2022 El programa ha"+
				"bloqueado de forma incorrecta un totem.\r\n\u2022 Un usuario ha bloqueado de forma"+
				"indefinida un totem, no est\u00E1 siendo utilizado y no puede liberarlo de forma manual.");
		info_text.setBounds(12, 13, 408, 142);
		contentPanel.add(info_text);
		
		//------------------------------------Buttons------------------------------------------//
		
		BT_unlock_wolf = new JButton("Desbloquear Lobo");
		BT_unlock_wolf.setForeground(Color.BLACK);
		BT_unlock_wolf.setBounds(35, 168, 150, 25);
		contentPanel.add(BT_unlock_wolf);
		
		BT_unlock_dragon = new JButton("Desbloquear Drag\u00F3n");
		BT_unlock_dragon.setForeground(Color.BLACK);
		BT_unlock_dragon.setBounds(229, 168, 150, 25);
		contentPanel.add(BT_unlock_dragon);
		
		okButton = new JButton("OK");
		okButton.setBounds(287, 206, 55, 25);
		contentPanel.add(okButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(349, 206, 75, 25);
		contentPanel.add(cancelButton);
		
		//------------------------------------Labels------------------------------------------//
		
		JLabel CreatedByYago = new JLabel("Created by Yago Echave-Sustaeta");
		CreatedByYago.setForeground(Color.BLACK);
		CreatedByYago.setBounds(12, 210, 258, 16);
		CreatedByYago.setFont(new Font("Tahoma", Font.BOLD, 13));
		contentPanel.add(CreatedByYago);
		
		JLabel Version_lb = new JLabel("Versión 1.3.5");
		Version_lb.setBounds(12, 225, 120, 14);
		contentPanel.add(Version_lb);

		JLabel background = new JLabel();
		background.setBounds(0, 0, 450, 253);
		background.setIcon(new ImageIcon(Info_VT.class.getResource("/Img/background.jpg")));
		contentPanel.add(background);
		
		
		//------------------------------------Initialize Variables--------------------------------//
		listeners();
		initialize();
	}
	
	public void listeners() {
		
		this.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent event) {
	            hideIt();
	        }
	    });
		
		BT_unlock_wolf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!unlock_wolf) {
					BT_unlock_wolf.setForeground(Color.RED);
					unlock_wolf=true;
				}
				else{
					BT_unlock_wolf.setForeground(Color.BLACK);
					unlock_wolf=false;
				}
			}
		});
		
		BT_unlock_dragon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!unlock_dragon) {
					BT_unlock_dragon.setForeground(Color.RED);
					unlock_dragon=true;
				}
				else {
					BT_unlock_dragon.setForeground(Color.BLACK);
					unlock_dragon=false;
				}
			}
		});
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean send=false;
				if(unlock_wolf && unlock_dragon) {
					JOptionPane.showMessageDialog(null,"Lobo y Dragón desbloqueados correctamente");
					send = true;
				}
				if(unlock_wolf) {
					panel.send("totem,soltar_lobo,"+System.getProperty("user.name"));
					BT_unlock_wolf.setForeground(Color.BLACK);
					unlock_wolf=false;
					if(!send)JOptionPane.showMessageDialog(null,"Lobo desbloqueado correctamente");
				}
				if(unlock_dragon){
					panel.send("totem,soltar_dragon,"+System.getProperty("user.name"));
					BT_unlock_dragon.setForeground(Color.BLACK);
					unlock_dragon=false;
					if(!send)JOptionPane.showMessageDialog(null,"Dragón desbloqueado correctamente");
				}
				hideIt();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideIt();
			}
		});
	}
	
	
	public void initialize() {
		unlock_dragon=false;
		unlock_wolf=false;
	}
	
	
	public void showIt(boolean wolf, boolean dragon) {
		this.setVisible(true);
		if(!wolf) {
			BT_unlock_wolf.setEnabled(false);
		}
		if(!dragon){
			BT_unlock_dragon.setEnabled(false);
		}
		
	}
	
	public void hideIt() {
		
		Info_VT.this.setVisible(false);
		
		unlock_wolf=false;
		unlock_dragon=false;
		BT_unlock_wolf.setEnabled(true);
		BT_unlock_dragon.setEnabled(true);
		BT_unlock_wolf.setForeground(Color.BLACK);
		BT_unlock_dragon.setForeground(Color.BLACK);
	}
}
