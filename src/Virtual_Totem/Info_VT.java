package Virtual_Totem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Info_VT extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnDesbloquearLobo;
	private JButton btnDesbloquearDragon;
	private boolean desb_dragon;
	private boolean desb_lobo;

	public Info_VT(Panel_VT panel) {	
		desb_dragon=false;
		desb_lobo=false;
		this.setTitle("Info of Virtual Totem");
		setBounds(810, 425, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		this.setVisible(false);
		JTextPane txtpnAContinuacinVa = new JTextPane();
		txtpnAContinuacinVa.setEditable(false);
		txtpnAContinuacinVa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtpnAContinuacinVa.setBackground(UIManager.getColor("CheckBox.background"));
		txtpnAContinuacinVa.setText("A continuaci\u00F3n va a proceder a desbloquear uno de los totem.\r\nEsta acci\u00F3n solo debe realizarse en los siguientes casos y solo tras haber corroborado la acci\u00F3n con el resto del equipo:\r\n\r\n\u2022 El programa ha bloqueado de forma incorrecta un totem.\r\n\u2022 Un usuario ha bloqueado de forma indefinida un totem, no est\u00E1 siendo utilizado y no puede liberarlo de forma manual.");
		txtpnAContinuacinVa.setBounds(12, 13, 408, 142);
		contentPanel.add(txtpnAContinuacinVa);
		{
			btnDesbloquearLobo = new JButton("Desbloquear Lobo");
			btnDesbloquearLobo.setForeground(Color.BLACK);
			btnDesbloquearLobo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(!desb_lobo) {
						btnDesbloquearLobo.setForeground(Color.RED);
						desb_lobo=true;
					}
					else{
						btnDesbloquearLobo.setForeground(Color.BLACK);
						desb_lobo=false;
					}
				}
			});
			btnDesbloquearLobo.setBounds(35, 168, 150, 25);
			contentPanel.add(btnDesbloquearLobo);
		}
		{
			btnDesbloquearDragon = new JButton("Desbloquear Drag\u00F3n");
			btnDesbloquearDragon.setForeground(Color.BLACK);
			btnDesbloquearDragon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!desb_dragon) {
						btnDesbloquearDragon.setForeground(Color.RED);
						desb_dragon=true;
					}
					else {
						btnDesbloquearDragon.setForeground(Color.BLACK);
						desb_dragon=false;
					}
				}
			});
			btnDesbloquearDragon.setBounds(229, 168, 150, 25);
			contentPanel.add(btnDesbloquearDragon);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						boolean send=false;
						if(desb_lobo && desb_dragon) {
							JOptionPane.showMessageDialog(null,"Lobo y Dragón desbloqueados correctamente");
							send = true;
						}
						if(desb_lobo) {
							panel.send("soltar_lobo");
							btnDesbloquearLobo.setForeground(Color.BLACK);
							desb_lobo=false;
							if(!send)JOptionPane.showMessageDialog(null,"Lobo desbloqueado correctamente");
						}
						if(desb_dragon){
							panel.send("soltar_dragon");
							btnDesbloquearDragon.setForeground(Color.BLACK);
							desb_dragon=false;
							if(!send)JOptionPane.showMessageDialog(null,"Dragón desbloqueado correctamente");
						}
						Info_VT.this.setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnDesbloquearLobo.setForeground(Color.BLACK);
						desb_lobo=false;
						btnDesbloquearDragon.setForeground(Color.BLACK);
						desb_dragon=false;
						Info_VT.this.setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void alert() {
		
	}
}
