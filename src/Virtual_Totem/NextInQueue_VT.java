package Virtual_Totem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;


public class NextInQueue_VT extends JDialog {
	

	private static final long serialVersionUID = 1L;
	private JButton okButton, cancelButton;
	private Panel_VT panel;
	private String totem, action, okAction;

	public NextInQueue_VT(Panel_VT panel, String action) {
		
		this.panel=panel;
		this.action=action;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		this.setTitle("Eres el siguiente de la cola");
		setBounds(100, 100, 321, 158);
		getRootPane().setDefaultButton(okButton);
		getContentPane().setLayout(null);
		
		

		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 86, 305, 33);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane);
		
		//------------------------------------Buttons------------------------------------------//
		
		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
				
		//------------------------------------TextFields---------------------------------------//
			
		JTextPane txtpnSeAcabaDe = new JTextPane();
		txtpnSeAcabaDe.setEditable(false);
		txtpnSeAcabaDe.setBounds(10, 0, 285, 87);
		getContentPane().add(txtpnSeAcabaDe);
		txtpnSeAcabaDe.setFont(new Font("Yu Gothic", Font.BOLD, 14));
		txtpnSeAcabaDe.setText("Se acaba de liberar el " +totem+ " y eres el siguiente en la cola. \u00BFQuieres cogerlo?");
		
		//------------------------------------Initialize Variables--------------------------------//
		
		initialize();
		listeners();
	}
	
	public void initialize() {
		if(action.equals("soltar_lobo")) {
			totem="lobo";
			okAction="coger_lobo";
		}
		if(action.equals("soltar_dragon")) {
			totem="dragon";
			okAction="coger_dragon";
		}
	}
	
	public void listeners() {
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.setAction(totem);
				panel.send("totem,"+okAction+","+System.getProperty("user.name"));
				setVisible(false);
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.send(action);
				setVisible(false);
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent event) {
	        	panel.send(action);
				setVisible(false);
	        }
	    });
	}
}
