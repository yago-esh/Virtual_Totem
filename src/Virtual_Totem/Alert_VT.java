package Virtual_Totem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	private final JPanel contentPanel = new JPanel();
	private JButton okButton, cancelButton;

	public Alert_VT() {
		
		this.setVisible(false);
		setBounds(810, 425, 450, 235);
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
		
		List list = new List();
		list.setBounds(19, 44, 120, 142);
		list.add("La Corpo");
		list.add("El Edu");
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
	}

	public void listeners() {
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideIt();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideIt();
			}
		});
	}
	
	public void showIt(String totem) {
		this.setVisible(true);
		this.setTitle("Cola de usuarios para el "+totem);
	}
	
	public void hideIt() {
		this.setVisible(false);
	}
}
